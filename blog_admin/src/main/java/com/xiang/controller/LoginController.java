package com.xiang.controller;

import com.xiang.annotation.SystemLog;
import com.xiang.domain.ResponseResult;
import com.xiang.domain.entity.LoginUser;
import com.xiang.domain.entity.Menu;
import com.xiang.domain.entity.User;
import com.xiang.domain.vo.AdminUserInfoVo;
import com.xiang.domain.vo.RouterVo;
import com.xiang.domain.vo.UserInfoVo;
import com.xiang.enums.AppHttpCodeEnum;
import com.xiang.exception.SystemException;
import com.xiang.service.BlogLoginService;
import com.xiang.service.LoginService;
import com.xiang.service.MenuService;
import com.xiang.service.RoleService;
import com.xiang.utils.BeanCopyUtils;
import com.xiang.utils.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class LoginController {
    @Autowired
    private LoginService loginService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private RoleService roleService;

    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody User user){
        if(!StringUtils.hasText(user.getUserName())){
            //提示 必须要传用户名
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return loginService.login(user);
    }



    @GetMapping("/getInfo")
    @Operation(summary = "登录用户信息",description = "获取登录用户的权限、角色、基本信息并封装")
    public ResponseResult getInfo() {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        User user = loginUser.getUser();
        //获取权限信息
        List<String> perms = menuService.selectPermsByUserId(user.getId());
        //获取角色信息
        List<String> roleKeyList = roleService.selectRoleKeyByUserId(user.getId());

        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        AdminUserInfoVo adminUserInfoVo = new AdminUserInfoVo(perms, roleKeyList, userInfoVo);
        return ResponseResult.okResult(adminUserInfoVo);
    }

    @GetMapping("/getRouters")
    public ResponseResult<RouterVo> getRouters(){
        Long userId = SecurityUtils.getUserId();
        //查询menu 结果是tree的形式
        List<Menu> menus = menuService.selectRouterMenuTreeByUserId(userId);
        //封装数据返回
        return ResponseResult.okResult(new RouterVo(menus));
    }

    @PostMapping("/user/logout")
    public ResponseResult logout() {
        return loginService.logout();
    }

}
