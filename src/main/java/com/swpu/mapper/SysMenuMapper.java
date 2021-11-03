package com.swpu.mapper;

import com.swpu.entity.SysMenu;
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
public interface SysMenuMapper extends BaseMapper<SysMenu> {
    /**
     * 根据父级id查询子级菜单
     * @param id
     * @return
     */
    List<SysMenu> findChildrenMenu(@Param("id") Long id);
}
