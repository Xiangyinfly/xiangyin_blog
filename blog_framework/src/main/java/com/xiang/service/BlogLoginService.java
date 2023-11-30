package com.xiang.service;

import com.xiang.domain.ResponseResult;
import com.xiang.domain.entity.User;

public interface BlogLoginService {
    ResponseResult login(User user);
}
