package com.swpu.controller;


import com.swpu.common.QueryInfo;
import com.swpu.common.Result;
import com.swpu.dto.CategoryAddDTO;
import com.swpu.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Liyuxi
 * @since 2021-11-06
 */
@RestController
@Api(tags = "分类接口")
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @ApiOperation("根据id进行删除")
    @PostMapping("/del/{id}")
    public Result deleteById(@PathVariable Integer id){
        return categoryService.deleteById(id);
    }

    @ApiOperation("根据id查找分类")
    @PostMapping("/findById/{id}")
    public Result findById(@PathVariable Integer id){
        return categoryService.selectById(id);
    }

    @ApiOperation("新增分类")
    @PostMapping("/insertCategory")
    public Result insertCategory(@RequestBody CategoryAddDTO dto){
        return categoryService.insertCategory(dto);
    }

    @ApiOperation("修改分类")
    @PostMapping("/updateCategory")
    public Result updateCategory(@RequestBody CategoryAddDTO dto){
        return categoryService.updateCategory(dto);
    }

    @ApiOperation("分页条件查询")
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryInfo queryInfo){
        return categoryService.findPage(queryInfo);
    }

    @ApiOperation("查询该分类下的列表")
    @PostMapping("findCommodities")
    public Result findCommodities(Long catId){
        return categoryService.findCommodities(catId);
    }
}
