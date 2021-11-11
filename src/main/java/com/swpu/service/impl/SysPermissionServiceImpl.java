package com.swpu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.swpu.common.QueryInfo;
import com.swpu.common.Result;
import com.swpu.dto.PermissionAddDTO;
import com.swpu.dto.PermissionUpdateDTO;
import com.swpu.entity.SysPermission;
import com.swpu.mapper.SysPermissionMapper;
import com.swpu.service.SysPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swpu.utils.BeanCopyUtil;
import com.swpu.utils.RedisUtil;
import com.swpu.utils.SecurityUtil;
import com.swpu.vo.PermissionVo;
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
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements SysPermissionService {

    @Autowired
    private RedisUtil redisUtil;

    //事务注解，如果过程出现任何问题，则回滚到执行前的状态，防止脏数据
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result addPermission(PermissionAddDTO dto) {
        if (dto == null){
            return Result.fail("传入参数不能为空");
        }
        //将权限DTO的数据复制进实体类
        SysPermission sysPermission = BeanCopyUtil.copyObject(dto, SysPermission.class);
        //直接mybatis-plus新增一条数据
        this.save(sysPermission);
        redisUtil.delKey("userInfo_" + SecurityUtil.getUsername());
        return Result.success("新增成功");
    }

    //事务注解，如果过程出现任何问题，则回滚到执行前的状态，防止脏数据
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result updatePermission(PermissionUpdateDTO dto) {
        if (dto.getId() == null){
            return Result.fail("参数异常");
        }
        SysPermission sysPermission = BeanCopyUtil.copyObject(dto, SysPermission.class);
        this.updateById(sysPermission);
        redisUtil.delKey("userInfo_" + SecurityUtil.getUsername());
        return Result.success("修改成功");
    }

    @Override
    public Result findPage(QueryInfo queryInfo) {
        //如果没有传分页参数，则默认第一页，每页10条数据
        queryInfo.setPageNumber(0 == queryInfo.getPageNumber() ? 1 : queryInfo.getPageNumber());
        queryInfo.setPageSize(0 == queryInfo.getPageSize() ? 10 : queryInfo.getPageSize());
        //新建分页对象
        Page<SysPermission> page = new Page<>(queryInfo.getPageNumber(),queryInfo.getPageSize());
        //新建查询对象
        QueryWrapper<SysPermission> qw = new QueryWrapper<>();
        //判断是否传入条件查询参数
        if (StringUtils.hasText(queryInfo.getQueryString())){
            //模糊查询  plus写法
            qw.like("label",queryInfo.getQueryString());
            //qw.like类似于SELECT * FROM sys_permission WHERE label LIKE %queryInfo.getQueryString()%
        }
        //传入page和查询条件对象，进行查询
        IPage<SysPermission> resultPage = super.page(page, qw);
        //记录分页的查询条数
        long total = resultPage.getTotal();
        //将page转化为List
        List<SysPermission> records = resultPage.getRecords();
        //复制对象集合
        List<PermissionVo> voList = BeanCopyUtil.copyList(records, PermissionVo.class);
        //新建voPage对象
        Page<PermissionVo> voPage = new Page<>();
        //传入total
        voPage.setTotal(total);
        //传入数据
        voPage.setRecords(voList);
        return Result.success("查询成功",voPage);
    }

    @Override
    public Result findAll() {
        List<SysPermission> list = this.list();
        return Result.success("所有权限数据",list);
    }


}
