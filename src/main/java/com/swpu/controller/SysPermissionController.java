package com.swpu.controller;


import com.swpu.common.QueryInfo;
import com.swpu.common.Result;
import com.swpu.dto.PermissionAddDTO;
import com.swpu.dto.PermissionUpdateDTO;
import com.swpu.service.SysPermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Liyuxi
 * @since 2021-11-02
 */
@RestController
@RequestMapping("/sys-permission")
@Api(tags = "权限模块")
public class SysPermissionController {

    @Autowired
    private SysPermissionService sysPermissionService;

    @PostMapping("add")
    @ApiOperation("新增权限")
    public Result add(@RequestBody PermissionAddDTO dto){
        return sysPermissionService.addPermission(dto);
    }

    @PostMapping("update")
    @ApiOperation("修改权限")
    public Result update(@RequestBody PermissionUpdateDTO dto){
        return sysPermissionService.updatePermission(dto);
    }


    @PostMapping("findPage")
    @ApiOperation("分页条件查询")
    public Result findPage(@RequestBody QueryInfo queryInfo){
        return sysPermissionService.findPage(queryInfo);
    }

    @Transactional(rollbackFor = Exception.class)
    @GetMapping("delete/{id}")
    @ApiOperation("删除权限")
    public Result delete(@PathVariable("id") Long id){
        if (null == id){
            return Result.fail("参数异常");
        }
        boolean b = sysPermissionService.removeById(id);
        if (b){
            return Result.success("删除成功!");
        }
        return Result.success("删除失败!");
    }


    @GetMapping("findAll")
    @ApiOperation("查询所有")
    public Result findAll(){
        return sysPermissionService.findAll();
    }
}
