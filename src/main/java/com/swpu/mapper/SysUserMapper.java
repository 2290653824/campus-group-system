package com.swpu.mapper;

import com.swpu.entity.SysMenu;
import com.swpu.entity.SysPermission;
import com.swpu.entity.SysRole;
import com.swpu.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Liyuxi
 * @since 2021-11-02
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    SysUser findByUserName(@Param("username") String username);

    /**
     * 获取role信息
     * @param userId
     * @return
     */
    List<SysRole> findRoles(@Param("userId") Long userId);

    /**
     * 获取菜单信息
     * @param userId
     * @return
     */
    List<SysMenu> findMenus(@Param("userId")Long userId);

    /**
     * 获取权限信息
     * @param userId
     * @return
     */
    List<SysPermission> findPermissions(@Param("userId")Long userId);

    /**
     * 查询子菜单信息
     * @param id
     * @param userId
     * @return
     */
    List<SysMenu> findChildrenMenu(@Param("id")Long id,@Param("userId") Long userId);
}
