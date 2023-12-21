package com.xiang.mapper;

import com.github.jeffreyning.mybatisplus.base.MppBaseMapper;
import com.xiang.domain.entity.UserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author chenwentao
* @description 针对表【sys_user_role(用户和角色关联表)】的数据库操作Mapper
* @createDate 2023-12-21 14:17:52
* @Entity com.xiang.domain.entity.UserRole
*/
@Mapper
public interface UserRoleMapper extends MppBaseMapper<UserRole> {

}




