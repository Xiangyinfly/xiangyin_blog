package com.xiang.controller;

import com.xiang.domain.ResponseResult;
import com.xiang.domain.entity.User;
import com.xiang.service.BlogLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class BlogLoginController {
    @Autowired
    private BlogLoginService blogLoginService;

    @PostMapping("/login")
    public ResponseResult login(@RequestBody User user) {
        return blogLoginService.login(user);
    }

    @PostMapping("/logout")
    public ResponseResult login() {
        return blogLoginService.logout();
    }
}
