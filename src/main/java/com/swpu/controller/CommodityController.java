package com.swpu.controller;


import com.swpu.common.QueryInfo;
import com.swpu.common.Result;
import com.swpu.dto.CommodityAddDTO;
import com.swpu.entity.Commodity;
import com.swpu.service.CommodityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Liyuxi
 * @since 2021-11-03
 */
@RestController
@Api(tags = "商品接口")
@RequestMapping("/commodity")
public class CommodityController {

    @Autowired
    private CommodityService commodityService;

    @ApiOperation("根据id删除商品")
    @PostMapping("/del/{id}")
    public Result deleteById(@PathVariable Integer id){
        return commodityService.deleteById(id);
    }


    @ApiOperation("根据id查询商品")
    @PostMapping("/findById/{id}")
    public Result findById(@PathVariable Integer id){
        return commodityService.SelectById(id);
    }

    @ApiOperation("分页条件查询")
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryInfo queryInfo){
        return commodityService.findPage(queryInfo);
    }


    @ApiOperation("添加商品")
    @PostMapping("/insertCommodity")
    public Result insertCommodity(@RequestBody CommodityAddDTO dto){
        return commodityService.insertCommodity(dto);
    }

    @ApiOperation(("修改商品"))
    @PostMapping("/updateCommodity")
    public Result updateCommodity(CommodityAddDTO dto){
        return commodityService.updateCommodity(dto);
    }


}
