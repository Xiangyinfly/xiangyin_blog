package com.xiang.mapper;

import com.xiang.domain.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author chenwentao
* @description 针对表【sys_role(角色信息表)】的数据库操作Mapper
* @createDate 2023-12-11 20:25:19
* @Entity com.xiang.domain/entity.Role
*/
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    List<String> selectRoleKeyByUserId(Long userId);
}




