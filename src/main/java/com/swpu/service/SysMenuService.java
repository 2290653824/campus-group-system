package com.swpu.service;

import com.swpu.common.QueryInfo;
import com.swpu.common.Result;
import com.swpu.dto.MenuAddDTO;
import com.swpu.dto.MenuUpdateDTO;
import com.swpu.entity.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Liyuxi
 * @since 2021-11-02
 */
public interface SysMenuService extends IService<SysMenu> {
    /**
     * 新增菜单
     * @param dto
     * @return
     */
    Result addMenu(MenuAddDTO dto);

    /**
     * 修改菜单
     * @param dto
     * @return
     */
    Result updateMenu(MenuUpdateDTO dto);

    /**
     * 分页查询菜单
     * @param queryInfo
     * @return
     */
    Result findPage(QueryInfo queryInfo);

    /**
     * 查询父级菜单
     * @return
     */
    Result findParent();
}
