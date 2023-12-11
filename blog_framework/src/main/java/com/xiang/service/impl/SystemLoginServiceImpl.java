package com.xiang.service.impl;

import com.xiang.domain.ResponseResult;
import com.xiang.domain.entity.LoginUser;
import com.xiang.domain.entity.User;
import com.xiang.domain.vo.BlogUserLoginVo;
import com.xiang.domain.vo.UserInfoVo;
import com.xiang.service.LoginService;
import com.xiang.utils.BeanCopyUtils;
import com.xiang.utils.JwtUtils;
import com.xiang.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;

@Service
public class SystemLoginServiceImpl implements LoginService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(token);
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("用户名或密码错误！");
        }

        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtils.createJWT(userId);

        //redis缓存
        redisCache.setCacheObject("login:" + userId,loginUser);

        HashMap<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token",jwt);
        return ResponseResult.okResult(tokenMap);
    }

}
