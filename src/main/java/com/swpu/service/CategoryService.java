package com.swpu.service;

import com.swpu.common.QueryInfo;
import com.swpu.common.Result;
import com.swpu.dto.CategoryAddDTO;
import com.swpu.dto.CommodityAddDTO;
import com.swpu.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Liyuxi
 * @since 2021-11-06
 */
public interface CategoryService extends IService<Category> {

    /**
     * 根据id查找分类
     * @param id
     * @return
     */
    Result selectById(@Param("id") Integer id);


    /**
     * 分页条件查询
     * @param queryInfo
     * @return
     */
    Result findPage(QueryInfo queryInfo);

    /**
     * 添加商品
     * @param dto
     * @return 影响行数
     */

    Result insertCategory(CategoryAddDTO dto);

    /**
     * 根据id删除商品
     * @param id
     * @return 影响行数
     */
    Result deleteById(@Param("id") Integer id);


    /**
     * 更新分类数据
     * @param dto
     * @return
     */
    Result updateCategory(CategoryAddDTO dto);



    /**
     * 根据分类id查询出商品列表
     * @param catId
     * @return
     */
    Result findCommodities(@Param("catId") Long catId);

}
