package com.swpu.service;

import com.swpu.common.QueryInfo;
import com.swpu.common.Result;
import com.swpu.dto.LoginDTO;
import com.swpu.dto.UserAddDTO;
import com.swpu.dto.UserUpdateDTO;
import com.swpu.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Liyuxi
 * @since 2021-11-02
 */
public interface SysUserService extends IService<SysUser> {
    /**
     * 登陆
     * @param loginDTO
     * @return
     */
    Result login(LoginDTO loginDTO);

    /**
     * 新增用户
     * @param dto
     * @return
     */
    Result addUser(UserAddDTO dto);

    /**
     * 修改用户
     * @param dto
     * @return
     */
    Result updateUser(UserUpdateDTO dto);

    /**
     * 分页查询
     * @param queryInfo
     * @return
     */
    Result findPage(QueryInfo queryInfo);

    /**
     * 删除用户
     * @param id
     * @return
     */
    Result deleteById(Long id);
}
