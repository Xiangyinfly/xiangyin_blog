package com.xiang.service;

import com.xiang.domain.ResponseResult;
import com.xiang.domain.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author chenwentao
* @description 针对表【sys_user(用户表)】的数据库操作Service
* @createDate 2023-12-06 14:29:42
*/
public interface UserService extends IService<User> {

    ResponseResult getUserInfo();

    ResponseResult updateUserInfo(User user);

    ResponseResult register(User user);

    ResponseResult getUserList(Integer pageNum, Integer pageSize, String userName, String phonenumber, String status);
}
