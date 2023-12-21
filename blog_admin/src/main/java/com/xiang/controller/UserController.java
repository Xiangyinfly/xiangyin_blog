package com.xiang.controller;

import com.xiang.annotation.SystemLog;
import com.xiang.domain.ResponseResult;
import com.xiang.domain.dto.AddUserDto;
import com.xiang.domain.dto.UpdateUserDto;
import com.xiang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/system/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public ResponseResult getUserList(Integer pageNum, Integer pageSize,String userName,String phonenumber,String status) {
        return userService.getUserList(pageNum,pageSize,userName,phonenumber,status);
    }

    @SystemLog(businessName = "新增用户")
    @PostMapping
    public ResponseResult addUser(@RequestBody AddUserDto addUserDto) {
        return userService.addUser(addUserDto);
    }


    @SystemLog(businessName = "删除用户")
    @DeleteMapping("/{id}")
    public ResponseResult deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }

    @SystemLog(businessName = "查询用户信息及用户角色信息")
    @GetMapping("/{id}")
    public ResponseResult getUserInfo(@PathVariable Long id) {
        return userService.getUserInfo(id);
    }

    @SystemLog(businessName = "修改用户信息")
    @PutMapping
    public ResponseResult updateUser(@RequestBody UpdateUserDto updateUserDto) {
        return userService.updateUser(updateUserDto);
    }


}
