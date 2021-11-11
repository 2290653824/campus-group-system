package com.swpu.controller;


import com.swpu.common.QueryInfo;
import com.swpu.common.Result;
import com.swpu.dto.RoleAddDTO;
import com.swpu.dto.RoleUpdateDTO;
import com.swpu.service.SysRoleService;
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
@RequestMapping("/sys-role")
@Api(tags = "角色模块")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    @PostMapping("add")
    @ApiOperation("新增角色")
    public Result add(@RequestBody RoleAddDTO dto){
        return sysRoleService.addRole(dto);
    }

    @PostMapping("update")
    @ApiOperation("修改角色")
    public Result update(@RequestBody RoleUpdateDTO dto){
        return sysRoleService.updateRole(dto);
    }

    @PostMapping("findPage")
    @ApiOperation("分页条件查询")
    public Result add(@RequestBody QueryInfo queryInfo){
        return sysRoleService.findPage(queryInfo);
    }


    @GetMapping("delete/{id}")
    @ApiOperation("删除")
    public Result delete(@PathVariable("id") Long id){
        return sysRoleService.deleteRole(id);
    }

    @GetMapping("findAll")
    @ApiOperation("查询所有")
    public Result findAll(){
        return sysRoleService.findAll();
    }

}
