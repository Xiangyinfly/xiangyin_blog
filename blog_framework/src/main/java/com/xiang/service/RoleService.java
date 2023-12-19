package com.xiang.service;

import com.xiang.domain.ResponseResult;
import com.xiang.domain.dto.AddRoleDto;
import com.xiang.domain.dto.UpdateRoleDto;
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

    ResponseResult roleList(Integer pageNum, Integer pageSize, String roleName, String status);

    ResponseResult changeStatus(Role role);

    ResponseResult addRole(AddRoleDto addRoleDto);

    ResponseResult getRole(Long id);

    ResponseResult updateRole(UpdateRoleDto updateRoleDto);

    ResponseResult deleteRole(Long id);
}
