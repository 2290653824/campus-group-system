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

    Result delRemark(Integer id);

    Result addReamrk(ProductRemarkDTO productRemarkDTO);
}
