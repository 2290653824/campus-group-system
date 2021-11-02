package com.swpu.service.impl;

import com.swpu.entity.SysMenu;
import com.swpu.entity.SysUser;
import com.swpu.mapper.SysUserMapper;
import com.swpu.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private RedisUtil redisUtil;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //1.在mapper中自定义登陆，根据用户名获取用户信息
        SysUser user;
        if (redisUtil.hasKey("userInfo_" + username)){
            //从缓存中存在用户信息直接从redis取值
            user = (SysUser) redisUtil.getValue("userInfo_" + username);
        }else {
            user = sysUserMapper.findByUserName(username);
            if (null == user){
                throw new RuntimeException("用户名或密码错误");
            }
            if (user.getAdmin()){
                user.setRoles(sysUserMapper.findRoles(null));
                user.setPermissions(sysUserMapper.findPermissions(null));
                List<SysMenu> parentMenus = sysUserMapper.findMenus(null);
                parentMenus.forEach(item -> {
                    List<SysMenu> childrenMenu = sysUserMapper.findChildrenMenu(item.getId(), null);
                    item.setChildren(childrenMenu);
                });
                user.setMenus(parentMenus);
            }else {
                user.setRoles(sysUserMapper.findRoles(user.getId()));
                user.setPermissions(sysUserMapper.findPermissions(user.getId()));
                List<SysMenu> parentMenus = sysUserMapper.findMenus(user.getId());
                parentMenus.forEach(item -> {
                    List<SysMenu> childrenMenu = sysUserMapper.findChildrenMenu(item.getId(), user.getId());
                    item.setChildren(childrenMenu);
                });
                user.setMenus(parentMenus);
            }
            redisUtil.setValueTime("userInfo_" + username,user,10);
        }
        return user;

    }
}
