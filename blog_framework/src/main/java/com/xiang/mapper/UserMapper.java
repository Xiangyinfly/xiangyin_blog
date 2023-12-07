package com.xiang.mapper;

import com.xiang.domain.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author chenwentao
* @description 针对表【sys_user(用户表)】的数据库操作Mapper
* @createDate 2023-12-06 14:29:42
* @Entity com.xiang.domain/entity.User
*/

@Mapper
public interface UserMapper extends BaseMapper<User> {

}




