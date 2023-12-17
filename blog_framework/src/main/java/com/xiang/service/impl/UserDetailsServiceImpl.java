package com.xiang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiang.constants.SystemConstants;
import com.xiang.domain.entity.LoginUser;
import com.xiang.domain.entity.User;
import com.xiang.mapper.MenuMapper;
import com.xiang.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MenuMapper menuMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,username);
        User user = userMapper.selectOne(queryWrapper);
        if (Objects.isNull(user)) {
            throw new RuntimeException("用户不存在！");
        }
        //权限信息封装
        if (SystemConstants.ADMIN.equals(user.getType())) {        //只有后台用户才加入权限封装
            List<String> perms = menuMapper.selectPermsByUserId(user.getId());
            return new LoginUser(user,perms);
        }
        return new LoginUser(user);
    }
}
