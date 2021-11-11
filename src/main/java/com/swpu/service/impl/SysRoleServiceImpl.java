package com.swpu.service.impl;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.swpu.common.QueryInfo;
import com.swpu.common.Result;
import com.swpu.dto.RoleAddDTO;
import com.swpu.dto.RoleUpdateDTO;
import com.swpu.entity.*;
import com.swpu.mapper.*;
import com.swpu.service.RolesMenusService;
import com.swpu.service.RolesPermissionsService;
import com.swpu.service.SysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swpu.utils.BeanCopyUtil;
import com.swpu.utils.RedisUtil;
import com.swpu.utils.SecurityUtil;
import com.swpu.vo.RoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Liyuxi
 * @since 2021-11-02
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Autowired
    private RolesMenusService rolesMenusService;

    @Autowired
    private RolesPermissionsService rolesPermissionsService;

    @Autowired
    private RolesMenusMapper rolesMenusMapper;

    @Autowired
    private RolesPermissionsMapper rolesPermissionsMapper;

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result addRole(RoleAddDTO dto) {
        if (dto == null){
            return Result.fail("参数异常");
        }
        QueryWrapper<SysRole> qw = new QueryWrapper<>();
        qw.eq("label",dto.getLabel());
        qw.eq("code",dto.getCode());
        SysRole one = this.getOne(qw);
        if (one != null){
            return Result.fail("已存在同名角色，请修改后再添加");
        }
        SysRole sysRole = BeanCopyUtil.copyObject(dto, SysRole.class);
        this.save(sysRole);
        //直接新增
        if (dto.getMenus() !=null && dto.getMenus().size() >0){
            List<SysMenu> menus = dto.getMenus();
            menus.forEach(data->{
                //新增角色-权限关系
                RolesMenus rolesMenus = new RolesMenus();
                rolesMenus.setRoleId(sysRole.getId());
                rolesMenus.setMenuId(data.getId());
                rolesMenusService.save(rolesMenus);
            });
        }
        //直接新增
        if (dto.getPermissions() != null && dto.getPermissions().size() >0){
            List<SysPermission> permissions = dto.getPermissions();
            permissions.forEach(data->{
                RolesPermissions rolesPermissions = new RolesPermissions();
                rolesPermissions.setRoleId(sysRole.getId());
                rolesPermissions.setPermissionId(data.getId());
                rolesPermissionsService.save(rolesPermissions);
            });
        }
        redisUtil.delKey("userInfo_" + SecurityUtil.getUsername());
        return Result.success("新增成功");
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result updateRole(RoleUpdateDTO dto) {
        if (dto.getId() == null){
            return Result.fail("参数异常");
        }
        Long roleId = dto.getId();
        //如果修改了菜单，则先删除原关联，再新增关联
        if (dto.getMenus() !=null &&dto.getMenus().size() >0){
            //先删除原有
            rolesMenusMapper.deleteOleMenu(roleId);
            //插入新的关联
            List<SysMenu> menus = dto.getMenus();
            menus.forEach(data->{
                //新增角色-权限关系
                RolesMenus rolesMenus = new RolesMenus();
                rolesMenus.setRoleId(roleId);
                rolesMenus.setMenuId(data.getId());
                rolesMenusService.save(rolesMenus);
            });
        }
        //如果修改了权限，则先删除原关联，再新增关联
        if (dto.getPermissions() != null && dto.getPermissions().size() >0){
            rolesPermissionsMapper.deleteOldPer(roleId);
            List<SysPermission> permissions = dto.getPermissions();
            permissions.forEach(data->{
                RolesPermissions rolesPermissions = new RolesPermissions();
                rolesPermissions.setRoleId(roleId);
                rolesPermissions.setPermissionId(data.getId());
                rolesPermissionsService.save(rolesPermissions);
            });
        }
        SysRole sysRole = BeanCopyUtil.copyObject(dto, SysRole.class);
        this.updateById(sysRole);
        redisUtil.delKey("userInfo_" + SecurityUtil.getUsername());
        return Result.success("修改成功");
    }

    @Override
    public Result findPage(QueryInfo queryInfo) {
        queryInfo.setPageNumber(0 == queryInfo.getPageNumber() ? 1 : queryInfo.getPageNumber());
        queryInfo.setPageSize(0 == queryInfo.getPageSize() ? 10 : queryInfo.getPageSize());
        //新建分页对象
        Page<SysRole> page = new Page<>(queryInfo.getPageNumber(),queryInfo.getPageSize());
        QueryWrapper<SysRole> qw = new QueryWrapper<>();
        if (StringUtils.hasText(queryInfo.getQueryString())){
            qw.like("label",queryInfo.getQueryString());
        }
        IPage<SysRole> iPage = super.page(page, qw);
        long total = iPage.getTotal();
        List<SysRole> records = iPage.getRecords();
        List<RoleVo> voList = BeanCopyUtil.copyList(records, RoleVo.class);
        voList.forEach(data->{
            Long id = data.getId();
            //查询菜单
            List<SysMenu> menuList = sysMenuMapper.findMenuByRoleId(id);
            menuList.forEach(item->{
                //插入子菜单
                List<SysMenu> childrenMenu = sysMenuMapper.findChildrenMenu(item.getId());
                item.setChildren(childrenMenu);
            });
            //查询权限
            List<SysPermission> permissionList = sysPermissionMapper.findPerByRoleId(id);
            data.setMenus(menuList);
            data.setPermissions(permissionList);
        });
        Page<RoleVo> resultPage = new Page<>();
        resultPage.setTotal(total);
        resultPage.setRecords(voList);
        return Result.success("分页数据",resultPage);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result deleteRole(Long id) {
        if (id == null){
            return Result.fail("参数异常");
        }
        //先删除关联表的关联关系
        rolesMenusMapper.deleteOleMenu(id);
        rolesPermissionsMapper.deleteOldPer(id);
        boolean b = this.removeById(id);
        if (b){
            return Result.success("删除成功");
        }
        return Result.fail("删除失败");
    }

    @Override
    public Result findAll() {
        List<SysRole> list = this.list();
        return Result.success("所有角色数据",list);
    }
}
