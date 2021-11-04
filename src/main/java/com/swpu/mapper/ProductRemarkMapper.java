package com.swpu.mapper;

import com.swpu.entity.ProductRemark;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author Liyuxi
 * @since 2021-11-04
 */
@Mapper
public interface ProductRemarkMapper extends BaseMapper<ProductRemark> {
    /**
     * 物理删除评论
     *
     * @param id
     * @return
     */
    int delRemark(@Param("id") Integer id);

    /**
     * 根据id查询父评论
     *
     * @param id
     * @return
     */
    List<ProductRemark> findChildrenProRemark(@Param("id") Integer id);
}
