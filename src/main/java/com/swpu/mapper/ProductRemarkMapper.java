package com.swpu.mapper;

import com.swpu.entity.ProductRemark;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Liyuxi
 * @since 2021-11-04
 */
public interface ProductRemarkMapper extends BaseMapper<ProductRemark> {

    int delRemark(Integer id);
}
