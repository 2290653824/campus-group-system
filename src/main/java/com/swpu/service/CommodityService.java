package com.swpu.service;

import com.swpu.common.QueryInfo;
import com.swpu.common.Result;
import com.swpu.dto.CommodityAddDTO;
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
     * 根据id查找商品
     * @param id
     * @return Commodity
     */
    Result SelectById(@Param("id") Integer id);


    /**
     * 添加商品
     * @param dto
     * @return 影响行数
     */

    Result insertCommodity(CommodityAddDTO dto);

    /**
     * 根据id删除商品
     * @param id
     * @return 影响行数
     */
    Result deleteById(@Param("id") Integer id);

    /**
     * 分页条件查询
     * @param queryInfo
     * @return
     */
    Result findPage(QueryInfo queryInfo);
}
