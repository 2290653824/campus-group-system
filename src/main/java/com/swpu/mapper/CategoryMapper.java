package com.swpu.mapper;

import com.swpu.entity.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Liyuxi
 * @since 2021-11-06
 */
public interface CategoryMapper extends BaseMapper<Category> {


    /**
     * 根据分类id查询出商品列表
     * @param catId
     * @return
     */
    List<Category> findCommodities(@Param("catId") Long catId);

    /**
     *
     * @param id
     * @return
     */
    List<Category> findCategoryByCommodityId(@Param("id")Long id);
}
