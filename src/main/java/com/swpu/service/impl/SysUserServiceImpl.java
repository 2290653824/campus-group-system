package com.swpu.service.impl;

import com.swpu.common.Result;
import com.swpu.dto.LoginDTO;
import com.swpu.entity.SysUser;
import com.swpu.mapper.SysRoleMapper;
import com.swpu.mapper.SysUserMapper;
import com.swpu.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swpu.service.UserRolesService;
import com.swpu.utils.MD5Util;
import com.swpu.utils.RedisUtil;
import com.swpu.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Liyuxi
 * @since 2021-11-02
 */
@Service
@Slf4j
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private UserRolesService userRolesService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    public Result login(LoginDTO loginDTO) {
        log.info("1.开始登陆");
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginDTO.getUsername());
        log.info("判断用户是否存在，密码是否正确");
        if (null == userDetails ||
            !passwordEncoder.matches(MD5Util.md5(loginDTO.getPassword()),userDetails.getPassword())){
            return Result.fail("账号或密码错误，请重新输入");
        }
        if (!userDetails.isEnabled()){
            return Result.fail("该账号已禁用，请联系管理员");
        }
        log.info("登陆成功，在security对象中存入登陆者信息");
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        log.info("根据登陆信息获取token");
        //需要借助jwt生成token
        String token = tokenUtil.generateToken(userDetails);
        HashMap<String, String> map = new HashMap<>(2);
        map.put("tokenHead",tokenHead);
        map.put("token",token);
        return Result.success("登陆成功",map);
    }
}
