package com.swpu.controller;


import com.swpu.common.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "测试")
public class TestController {

    @ApiOperation("测试接口")
    @GetMapping("test")
    public Result test(){
        return Result.success("测试成功");
    }

}
