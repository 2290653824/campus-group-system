package com.swpu.controller;

import com.swpu.common.Result;
import com.swpu.dto.LoginDTO;
import com.swpu.service.SysUserService;
import com.swpu.utils.RedisUtil;
import com.swpu.utils.SecurityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "登陆接口")
@RequestMapping("/user")
public class LoginController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private RedisUtil redisUtil;

    @PostMapping("login")
    @ApiOperation("登陆")
    public Result login(@RequestBody LoginDTO loginDTO){
        return sysUserService.login(loginDTO);
    }


    @PostMapping("/logout")
    @ApiOperation("退出登陆")
    public Result logout(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = SecurityUtil.getUsername();
        redisUtil.delKey("userInfo_" + username);
        if (authentication != null){
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        return Result.success("退出登陆成功!");
    }



}
