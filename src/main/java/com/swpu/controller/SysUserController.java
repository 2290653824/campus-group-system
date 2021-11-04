package com.swpu.controller;


import com.swpu.common.QueryInfo;
import com.swpu.common.Result;
import com.swpu.dto.UserAddDTO;
import com.swpu.dto.UserUpdateDTO;
import com.swpu.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
@RequestMapping("/sys-user")
@Api(tags = "用户模块")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @PostMapping("add")
    @ApiOperation("新增用户")
    public Result add(@RequestBody UserAddDTO dto){
        return sysUserService.addUser(dto);
    }

    @PostMapping("update")
    @ApiOperation("修改用户")
    public Result update(@RequestBody UserUpdateDTO dto){
        return sysUserService.updateUser(dto);
    }

    @PostMapping("findPage")
    @ApiOperation("分页条件查询")
    public Result findPage(@RequestBody QueryInfo queryInfo){
        return sysUserService.findPage(queryInfo);
    }


    @GetMapping("delete/{id}")
    @ApiOperation("删除用户")
    public Result delete(@PathVariable("id") Long id){
        return sysUserService.deleteById(id);
    }
}
