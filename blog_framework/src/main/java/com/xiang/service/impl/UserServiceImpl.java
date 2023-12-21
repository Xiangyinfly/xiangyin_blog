package com.xiang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiang.constants.SystemConstants;
import com.xiang.domain.ResponseResult;
import com.xiang.domain.dto.AddUserDto;
import com.xiang.domain.dto.UpdateUserDto;
import com.xiang.domain.entity.Role;
import com.xiang.domain.entity.User;
import com.xiang.domain.entity.UserRole;
import com.xiang.domain.vo.*;
import com.xiang.enums.AppHttpCodeEnum;
import com.xiang.exception.SystemException;
import com.xiang.service.RoleService;
import com.xiang.service.UserRoleService;
import com.xiang.service.UserService;
import com.xiang.mapper.UserMapper;
import com.xiang.utils.BeanCopyUtils;
import com.xiang.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
* @author chenwentao
* @description 针对表【sys_user(用户表)】的数据库操作Service实现
* @createDate 2023-12-06 14:29:42
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RoleService roleService;
    @Override
    public ResponseResult getUserInfo() {
        Long userId = SecurityUtils.getUserId();
        User user = getById(userId);
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        return ResponseResult.okResult(userInfoVo);
    }

    @Override
    public ResponseResult updateUserInfo(User user) {
        updateById(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult register(User user) {
        if (!StringUtils.hasText(user.getUserName())) {
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if(!StringUtils.hasText(user.getPassword())){
            throw new SystemException(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_NOT_NULL);
        }

        if(userNameExist(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if(nickNameExist(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIST);
        }
        if(emailExist(user.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }


        String encodePasswd = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePasswd);
        save(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getUserList(Integer pageNum, Integer pageSize, String userName, String phonenumber, String status) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(userName),User::getUserName,userName);
        queryWrapper.like(StringUtils.hasText(phonenumber),User::getPhonenumber,phonenumber);
        queryWrapper.like(SystemConstants.STATUS_NORMAL.equals(status) || SystemConstants.STATUS_ABNORMAL.equals(status),User::getStatus,status);
        Page<User> page = new Page<>(pageNum, pageSize);
        page(page,queryWrapper);
        List<UserListVo> userListVos = BeanCopyUtils.copyBeanList(page.getRecords(), UserListVo.class);
        PageVo pageVo = new PageVo(userListVos, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    @Transactional
    public ResponseResult addUser(AddUserDto addUserDto) {
        User user = BeanCopyUtils.copyBean(addUserDto, User.class);
        if (!StringUtils.hasText(user.getUserName())) {
            throw new SystemException(AppHttpCodeEnum.USERNAME_NOT_NULL);
        }
        if(userNameExist(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if(phonenumberExist(user.getPhonenumber())){
            throw new SystemException(AppHttpCodeEnum.PHONENUMBER_EXIST);
        }
        if(emailExist(user.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        save(user);

        List<Long> roleIds = addUserDto.getRoleIds();
        List<UserRole> userRoleList = roleIds.stream().map(ri -> new UserRole(user.getId(), ri)).toList();
        userRoleService.saveOrUpdateBatchByMultiId(userRoleList);
        return ResponseResult.okResult();
    }

    @Override
    @Transactional
    public ResponseResult deleteUser(Long id) {
        removeById(id);

        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRole::getUserId,id);
        userRoleService.remove(queryWrapper);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getUserInfo(Long id) {
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRole::getUserId,id);
        List<UserRole> userRoleList = userRoleService.list(queryWrapper);
        List<Long> roleIds = userRoleList.stream().map(UserRole::getRoleId).toList();

        List<Role> roles = roleService.listByIds(roleIds);

        User user = getById(id);
        AdminUserInfoVo adminUserInfoVo = BeanCopyUtils.copyBean(user, AdminUserInfoVo.class);

        UserRoleInfoVo userRoleInfoVo = new UserRoleInfoVo(roleIds, roles, adminUserInfoVo);
        return ResponseResult.okResult(userRoleInfoVo);
    }

    @Override
    @Transactional
    public ResponseResult updateUser(UpdateUserDto updateUserDto) {
        User user = BeanCopyUtils.copyBean(updateUserDto, User.class);
        if(userNameExist(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if(nickNameExist(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIST);
        }
        if(emailExist(user.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }
        updateById(user);

        //由于是复合主键，更新的时候先删除旧的userrole关系再添加新的userrole关系！！
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRole::getUserId,user.getId());
        userRoleService.remove(queryWrapper);

        List<Long> roleIds = updateUserDto.getRoleIds();
        List<UserRole> userRoleList = roleIds.stream().map(ri -> new UserRole(user.getId(), ri)).toList();
        userRoleService.saveOrUpdateBatchByMultiId(userRoleList);
        return ResponseResult.okResult();
    }


    private boolean phonenumberExist(String phonenumber) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPhonenumber,phonenumber);
        return count(queryWrapper) > 0;
    }

    private boolean emailExist(String email) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmail,email);
        return count(queryWrapper) > 0;
    }

    private boolean nickNameExist(String nickName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getNickName,nickName);
        return count(queryWrapper) > 0;
    }

    private boolean userNameExist(String userName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,userName);
        return count(queryWrapper) > 0;
    }
}




