package com.swpu.controller;


import com.swpu.common.QueryInfo;
import com.swpu.common.Result;
import com.swpu.dto.MenuAddDTO;
import com.swpu.dto.MenuUpdateDTO;
import com.swpu.service.SysMenuService;
import com.swpu.utils.RedisUtil;
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
@RequestMapping("/sys-menu")
@Api(tags = "菜单模块")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;

    @Autowired
    private RedisUtil redisUtil;

    @PostMapping("add")
    @ApiOperation("新增菜单")
    public Result add(@RequestBody MenuAddDTO dto){
        return sysMenuService.addMenu(dto);
    }

    @PostMapping("update")
    @ApiOperation("修改菜单")
    public Result update(@RequestBody MenuUpdateDTO dto){
        return sysMenuService.updateMenu(dto);
    }

    @PostMapping("findPage")
    @ApiOperation("条件分页查询")
    public Result findPage(@RequestBody QueryInfo queryInfo){
        return sysMenuService.findPage(queryInfo);
    }

    @Transactional(rollbackFor = Exception.class)
    @GetMapping("delete/{id}")
    @ApiOperation("删除菜单")
    public Result delete(@PathVariable("id") Long id){
        if (null == id){
            return Result.fail("参数异常");
        }
        boolean b = sysMenuService.removeById(id);
        if (b){
            return Result.success("删除成功!");
        }
        return Result.fail("删除失败!");
    }

    @GetMapping("findParent")
    @ApiOperation("查询所有菜单")
    public Result findParent(){
        return sysMenuService.findParent();
    }


}
