package com.xiang.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.jeffreyning.mybatisplus.service.MppServiceImpl;
import com.xiang.domain.entity.UserRole;
import com.xiang.service.UserRoleService;
import com.xiang.mapper.UserRoleMapper;
import org.springframework.stereotype.Service;

/**
* @author chenwentao
* @description 针对表【sys_user_role(用户和角色关联表)】的数据库操作Service实现
* @createDate 2023-12-21 14:17:52
*/
@Service
public class UserRoleServiceImpl extends MppServiceImpl<UserRoleMapper, UserRole>
    implements UserRoleService{

}




