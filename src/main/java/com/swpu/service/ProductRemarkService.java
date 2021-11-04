package com.swpu.service;

import com.swpu.common.Result;
import com.swpu.dto.ProductRemarkDTO;
import com.swpu.entity.ProductRemark;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Liyuxi
 * @since 2021-11-04
 */
public interface ProductRemarkService extends IService<ProductRemark> {
    /**
     * 逻辑删除评论
     * @param id
     * @return
     */
    Result delRemark(Integer id);

    /**
     * 新增评论
     * @param productRemarkDTO
     * @return
     */
    Result addReamrk(ProductRemarkDTO productRemarkDTO);

    /**
     * 物理删除评论
     * @param id
     * @return
     */
    Result pdelRemark(Integer id);
}
