package com.xiang.controller;

import com.xiang.annotation.SystemLog;
import com.xiang.domain.ResponseResult;
import com.xiang.domain.dto.AddRoleDto;
import com.xiang.domain.dto.ChangeRoleStatusDto;
import com.xiang.domain.dto.UpdateRoleDto;
import com.xiang.domain.entity.Role;
import com.xiang.service.RoleService;
import com.xiang.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/system/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @SystemLog(businessName = "查询角色列表")
    @GetMapping("/list")
    public ResponseResult roleList(Integer pageNum, Integer pageSize,String roleName,String status) {
        return roleService.roleList(pageNum,pageSize,roleName,status);
    }

    @SystemLog(businessName = "改变角色状态")
    @GetMapping("/changeStatus")
    public ResponseResult changeStatus(@RequestBody ChangeRoleStatusDto changeRoleStatusDto) {
        Role role = BeanCopyUtils.copyBean(changeRoleStatusDto, Role.class);
        role.setId(changeRoleStatusDto.getRoleId());
        return roleService.changeStatus(role);
    }

    @SystemLog(businessName = "新增角色")
    @PostMapping
    public ResponseResult addRole(@RequestBody AddRoleDto addRoleDto) {
        return roleService.addRole(addRoleDto);
    }

    @SystemLog(businessName = "角色信息回显")
    @GetMapping("/{id}")
    public ResponseResult getRole(@PathVariable Long id) {
        return roleService.getRole(id);
    }

    @SystemLog(businessName = "更新角色")
    @PutMapping
    public ResponseResult updateRole(@RequestBody UpdateRoleDto updateRoleDto) {
        return roleService.updateRole(updateRoleDto);
    }

    @SystemLog(businessName = "删除角色")
    @DeleteMapping("/{id}")
    public ResponseResult deleteRole(@PathVariable Long id) {
        return roleService.deleteRole(id);
    }


}