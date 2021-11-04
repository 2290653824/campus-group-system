package com.swpu.mapper;

import com.swpu.entity.RolesMenus;
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
public interface RolesMenusMapper extends BaseMapper<RolesMenus> {
    /**
     * 删除旧的角色-菜单
     * @param roleId
     */
    void deleteOleMenu(@Param("roleId") Long roleId);
}
