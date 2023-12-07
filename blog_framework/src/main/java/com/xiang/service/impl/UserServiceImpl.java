package com.xiang.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiang.domain.entity.User;
import com.xiang.service.UserService;
import com.xiang.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author chenwentao
* @description 针对表【sys_user(用户表)】的数据库操作Service实现
* @createDate 2023-12-06 14:29:42
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




