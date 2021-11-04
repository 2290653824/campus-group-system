package com.swpu.mapper;

import com.swpu.entity.RolesPermissions;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Liyuxi
 * @since 2021-11-02
 */
@Mapper
public interface RolesPermissionsMapper extends BaseMapper<RolesPermissions> {
    /**
     * 删除旧角色-权限
     * @param roleId
     */
    void deleteOldPer(@Param("roleId") Long roleId);
}
