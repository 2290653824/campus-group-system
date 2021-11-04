package com.swpu.service;

import com.swpu.common.Result;
import com.swpu.entity.Commodity;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Liyuxi
 * @since 2021-11-03
 */
public interface CommodityService extends IService<Commodity> {


    /**
     * 根据名称查找商品
     * @return Commodity
     */
    Result findByName(@Param("name") String name);


    /**
     * 根据id查找商品
     * @param id
     * @return Commodity
     */
    Result SelectById(@Param("id") Integer id);


    /**
     * 添加商品
     * @param commodity
     * @return 影响行数
     */

    Result insertCommodity(@Param("commodity") Commodity commodity);

    /**
     * 根据id删除商品
     * @param id
     * @return 影响行数
     */
    Result deleteById(@Param("id") Integer id);

}
