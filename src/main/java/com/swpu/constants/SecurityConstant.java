package com.swpu.constants;

/**
 * 白名单设置
 */
public class SecurityConstant {

    public static final String[] WHITE_LIST = {
            //后端的
            "/user/login",
            "/user/logout",
            "/oauth/**",
            "/test",

            //swagger相关的
            "/doc.html",
            "/swagger-ui.html",
            "/webjars/**",
            "/swagger-resources/**",
            "/v2/api-docs",
            "/v2/api-docs-ext",
            "/configuration/ui",
            "/configuration/security",
            "/favicon.ico",

            //微信小程序资源
            "/user/wxlogin",
            //发送手机验证码
            "/user/sendSms",
            //druid的
            "/druid/**",

            //获取验证码
            "/captcha/**",
            "/getCaptchaOpen",

            //前端的


            //找回密码
            "/tool/forgetPassword"
    };

}
