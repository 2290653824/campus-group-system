package com.swpu.service;

import com.swpu.common.QueryInfo;
import com.swpu.common.Result;
import com.swpu.dto.RoleAddDTO;
import com.swpu.dto.RoleUpdateDTO;
import com.swpu.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Liyuxi
 * @since 2021-11-02
 */
public interface SysRoleService extends IService<SysRole> {
    /**
     * 新增角色
     * @param dto
     * @return
     */
    Result addRole(RoleAddDTO dto);

    /**
     * 修改角色
     * @param dto
     * @return
     */
    Result updateRole(RoleUpdateDTO dto);

    /**
     * 分页查询
     * @param queryInfo
     * @return
     */
    Result findPage(QueryInfo queryInfo);

    /**
     * 删除角色
     * @param id
     * @return
     */
    Result deleteRole(Long id);
}
