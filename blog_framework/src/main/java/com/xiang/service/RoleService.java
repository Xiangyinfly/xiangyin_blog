package com.xiang.service;

import com.xiang.domain.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author chenwentao
* @description 针对表【sys_role(角色信息表)】的数据库操作Service
* @createDate 2023-12-11 20:25:19
*/
public interface RoleService extends IService<Role> {

    List<String> selectRoleKeyByUserId(Long id);
}
