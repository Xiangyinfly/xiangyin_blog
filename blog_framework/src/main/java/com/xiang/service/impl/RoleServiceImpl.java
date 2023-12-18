package com.xiang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiang.constants.SystemConstants;
import com.xiang.domain.ResponseResult;
import com.xiang.domain.dto.AddRoleDto;
import com.xiang.domain.entity.Comment;
import com.xiang.domain.entity.Role;
import com.xiang.domain.entity.RoleMenu;
import com.xiang.domain.vo.PageVo;
import com.xiang.domain.vo.RoleListVo;
import com.xiang.domain.vo.RoleVo;
import com.xiang.mapper.RoleMenuMapper;
import com.xiang.service.RoleMenuService;
import com.xiang.service.RoleService;
import com.xiang.mapper.RoleMapper;
import com.xiang.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
* @author chenwentao
* @description 针对表【sys_role(角色信息表)】的数据库操作Service实现
* @createDate 2023-12-11 20:25:19
*/
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
    implements RoleService{

    @Autowired
    private RoleMenuService roleMenuService;


    @Override
    public List<String> selectRoleKeyByUserId(Long id) {
        //判断是否是管理员 如果是返回集合中只需要有admin
        if(id == 1L){
            List<String> roleKeys = new ArrayList<>();
            roleKeys.add("admin");
            return roleKeys;
        }
        //否则查询用户所具有的角色信息
        return getBaseMapper().selectRoleKeyByUserId(id);
    }

    @Override
    public ResponseResult roleList(Integer pageNum, Integer pageSize, String roleName, String status) {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(roleName),Role::getRoleName,roleName);
        queryWrapper.eq(SystemConstants.STATUS_NORMAL.equals(status) || SystemConstants.STATUS_ABNORMAL.equals(status),Role::getStatus,status);
        Page<Role> page = new Page<>(pageNum, pageSize);
        page(page,queryWrapper);

        List<RoleListVo> roleListVos = BeanCopyUtils.copyBeanList(page.getRecords(), RoleListVo.class);
        PageVo pageVo = new PageVo(roleListVos,page.getTotal());
        return ResponseResult.okResult(pageVo);

    }

    @Override
    public ResponseResult changeStatus(Role role) {
        updateById(role);
        return ResponseResult.okResult();
    }

    @Override
    @Transactional
    public ResponseResult addRole(AddRoleDto addRoleDto) {
        Role role = BeanCopyUtils.copyBean(addRoleDto, Role.class);
        save(role);

        List<Long> menuIds = addRoleDto.getMenuIds();
        List<RoleMenu> roleMenus = menuIds.stream().map(m -> new RoleMenu(role.getId(), m)).toList();
        roleMenuService.saveBatch(roleMenus);

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getRole(Long id) {
        Role role = getById(id);
        RoleVo roleVo = BeanCopyUtils.copyBean(role, RoleVo.class);
        return ResponseResult.okResult(roleVo);
    }
}




