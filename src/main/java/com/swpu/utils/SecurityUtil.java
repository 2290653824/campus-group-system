package com.swpu.utils;

import com.swpu.entity.SysUser;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author ajie
 * @createTime 2021年05月27日 19:51:00
 */
public class SecurityUtil {

    /**
     * 获取用户名
     * @return
     */
    public static String getUsername() {
        try {
            return getUserInfo().getUsername();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取用户ID
     * @return
     */
    public static Long getUserId() {
        try {
            return getUserInfo().getId();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取用户信息
     * @return
     */
    public static SysUser getUserInfo() {
        try {
            SysUser user = (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            user.setPassword(null);
//            user.setName(user.getUsername());
            return user;
        } catch (Exception e) {
            return null;
        }
    }


}
