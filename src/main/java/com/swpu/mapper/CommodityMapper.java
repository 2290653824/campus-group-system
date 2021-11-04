package com.swpu.mapper;

import com.swpu.entity.Commodity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Liyuxi
 * @since 2021-11-03
 */
public interface CommodityMapper extends BaseMapper<Commodity> {

    /**
     * 根据名称查找商品(同名商品可能有多个)
     * @param name
     * @return List<Commodity>
     */
    List<Commodity> findByName(@Param("name") String name);


    /**
     * 根据id查找商品
     * @param id
     * @return Commodity
     */
    @Override
    Commodity selectById(@Param("id") Serializable id);

    /**
     * 根据id删除商品
     * @param id
     * @return 影响行数
     */
    int deleteById(@Param("id") Integer id);


    /**
     * 添加商品
     * @param commodity
     * @return 影响行数
     */
    @Override
    int insert(@Param("commodity") Commodity commodity);
}
