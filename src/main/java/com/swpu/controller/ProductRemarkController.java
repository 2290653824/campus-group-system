package com.swpu.controller;


import com.swpu.common.QueryInfo;
import com.swpu.common.Result;
import com.swpu.dto.ProductRemarkDTO;
import com.swpu.service.ProductRemarkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Liyuxi
 * @since 2021-11-04
 */
@Api(tags = "评论接口")
@RestController
@RequestMapping("/product-remark")
public class ProductRemarkController {
    @Autowired
    private ProductRemarkService productRemarkService;

    @PostMapping(value = "/del/{id}")
    @ApiOperation("删除评论(普通删除)")
    public Result delRemark(@PathVariable("id") Integer id) {
        return productRemarkService.delRemark(id);
    }

    @ApiOperation("增加评论")
    @PostMapping("/add")
    public Result addReamrk(@RequestBody ProductRemarkDTO productRemarkDTO) {
        if (productRemarkDTO == null) {
            return Result.fail("参数有问题");
        } else {
            Result result = productRemarkService.addReamrk(productRemarkDTO);
            return result;
        }
    }

    @ApiOperation("删除评论(永久删除)")
    @PostMapping("/pdel/{id}")
    public Result pdelRemark(@PathVariable("id") Integer id) {
        if (id < 0 || id == null) {
            return Result.fail("传入参数有问题");
        } else {
            Result result = productRemarkService.pdelRemark(id);
            return result;
        }
    }

    @ApiOperation(value = "获取评论")
    @PostMapping(value = "/findPages")
    public Result pdelFindPages(@RequestBody QueryInfo queryInfo) {
        return productRemarkService.pdelFindPages(queryInfo);
    }
}
