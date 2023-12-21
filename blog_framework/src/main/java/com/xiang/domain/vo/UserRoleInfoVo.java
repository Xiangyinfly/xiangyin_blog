package com.xiang.domain.vo;

import com.xiang.domain.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleInfoVo {
    private List<Long> roleIds;
    private List<Role> roles;
    private AdminUserInfoVo user;
}
