package com.swpu.mapper;

import com.swpu.entity.ProductRemark;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Liyuxi
 * @since 2021-11-04
 */
@Mapper
public interface ProductRemarkMapper extends BaseMapper<ProductRemark> {

    int delRemark(Integer id);
}
