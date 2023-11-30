package com.xiang.service.impl;

import com.xiang.domain.ResponseResult;
import com.xiang.domain.entity.LoginUser;
import com.xiang.domain.entity.User;
import com.xiang.domain.vo.BlogUserLoginVo;
import com.xiang.domain.vo.UserInfoVo;
import com.xiang.mapper.UserMapper;
import com.xiang.service.BlogLoginService;
import com.xiang.utils.BeanCopyUtils;
import com.xiang.utils.JwtUtils;
import com.xiang.utils.RedisCache;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class BlogLoginServiceImpl implements BlogLoginService {
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
        redisCache.setCacheObject("bloglogin:" + userId,loginUser);

        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        BlogUserLoginVo blogUserLoginVo = new BlogUserLoginVo(jwt, userInfoVo);

        return ResponseResult.okResult(blogUserLoginVo);
    }

}
