package com.swpu.mapper;

import com.swpu.entity.SysRole;
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
public interface SysRoleMapper extends BaseMapper<SysRole> {
    /**
     * 根据用户id查询角色信息
     * @param id
     * @return
     */
    List<SysRole> findRolesByUserId(@Param("id") Long id);

    /**
     * 通过用户id查询角色
     * @param id
     * @return
     */
    List<SysRole> getRoleByUserId(@Param("id") Long id);
}
