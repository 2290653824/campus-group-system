package com.swpu.service;

import com.swpu.common.Result;
import com.swpu.dto.LoginDTO;
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
}
