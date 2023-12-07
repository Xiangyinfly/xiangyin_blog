package com.xiang.utils;

import com.xiang.domain.entity.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
    public static LoginUser getLoginUser() {
        return ((LoginUser) getAuthentication().getPrincipal());
    }

    public static boolean isAdmin() {
        Long id = getUserId();
        return id != null && 1L == id;
    }

    public static Long getUserId() {
        return getLoginUser().getUser().getId();
    }
}
