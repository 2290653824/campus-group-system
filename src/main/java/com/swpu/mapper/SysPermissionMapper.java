package com.swpu.mapper;

import com.swpu.entity.SysPermission;
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
public interface SysPermissionMapper extends BaseMapper<SysPermission> {
    /**
     * 根据角色id查询权限
     * @param id
     * @return
     */
    List<SysPermission> findPerByRoleId(@Param("id") Long id);
}
