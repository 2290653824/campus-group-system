package com.swpu.service;

import com.swpu.common.QueryInfo;
import com.swpu.common.Result;
import com.swpu.dto.PermissionAddDTO;
import com.swpu.dto.PermissionUpdateDTO;
import com.swpu.entity.SysPermission;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Liyuxi
 * @since 2021-11-02
 */
public interface SysPermissionService extends IService<SysPermission> {
    /**
     * 新增权限
     * @param dto
     * @return
     */
    Result addPermission(PermissionAddDTO dto);

    /**
     * 修改权限
     * @param dto
     * @return
     */
    Result updatePermission(PermissionUpdateDTO dto);

    /**
     * 分页条件查询
     * @param queryInfo
     * @return
     */
    Result findPage(QueryInfo queryInfo);
}
