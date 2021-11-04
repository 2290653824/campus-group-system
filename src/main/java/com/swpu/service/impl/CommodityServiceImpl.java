package com.swpu.service.impl;

import com.swpu.common.Result;
import com.swpu.entity.Commodity;
import com.swpu.mapper.CommodityMapper;
import com.swpu.service.CommodityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Liyuxi
 * @since 2021-11-03
 */
@Service
public class CommodityServiceImpl extends ServiceImpl<CommodityMapper, Commodity> implements CommodityService {


    @Autowired
    private CommodityMapper commodityMapper;


    @Override
    public Result findByName(String name) {
        if(name==null) return Result.fail("传入的name为空");
        List<Commodity> commodities= commodityMapper.findByName(name);
        return new Result(true,"查询商品成功",commodities);

    }

    @Override
    public Result SelectById(Integer id) {
        if (id == null || id <= 0) {
            return Result.fail("传入的id异常");
        }
        Commodity commodity=commodityMapper.selectById(id);

        return new Result(true,"根据id查询商品成功",commodity);
    }

    @Override
    public Result insertCommodity(Commodity commodity) {
        int i = commodityMapper.insert(commodity);
        if(i!=1) return Result.fail("添加商品失败");
        return Result.success("添加商品成功");
    }

    @Override
    public Result deleteById(Integer id) {
        if (id == null || id <= 0) {
            return Result.fail("传入的id异常");
        }
        int i = commodityMapper.deleteById(id);
        if (i == 1) {
            return Result.success("删除成功");
        }
        return Result.fail("删除异常");
    }


}
