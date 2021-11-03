package com.swpu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.swpu.common.QueryInfo;
import com.swpu.common.Result;
import com.swpu.dto.MenuAddDTO;
import com.swpu.dto.MenuUpdateDTO;
import com.swpu.entity.SysMenu;
import com.swpu.mapper.SysMenuMapper;
import com.swpu.service.SysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swpu.utils.BeanCopyUtil;
import com.swpu.utils.RedisUtil;
import com.swpu.utils.SecurityUtil;
import com.swpu.vo.MenuVo;
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
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result addMenu(MenuAddDTO dto) {
        if (null == dto){
            return Result.fail("参数不能为空");
        }
        SysMenu sysMenu = BeanCopyUtil.copyObject(dto, SysMenu.class);
        this.save(sysMenu);
        redisUtil.delKey("userInfo_" + SecurityUtil.getUsername());
        return Result.success("新增成功");
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result updateMenu(MenuUpdateDTO dto) {
        if (dto.getId() == null){
            return Result.fail("参数异常");
        }
        SysMenu sysMenu = BeanCopyUtil.copyObject(dto, SysMenu.class);
        this.updateById(sysMenu);
        redisUtil.delKey("userInfo_" + SecurityUtil.getUsername());
        return Result.success("修改成功");
    }

    @Override
    public Result findPage(QueryInfo queryInfo) {
        //如果没有传分页参数，则默认第一页，每页10条数据
        queryInfo.setPageNumber(0 == queryInfo.getPageNumber() ? 1 : queryInfo.getPageNumber());
        queryInfo.setPageSize(0 == queryInfo.getPageSize() ? 10 : queryInfo.getPageSize());
        Page<SysMenu> page = new Page<>(queryInfo.getPageNumber(),queryInfo.getPageSize());
        QueryWrapper<SysMenu> qw = new QueryWrapper<>();
        if (StringUtils.hasText(queryInfo.getQueryString())){
            qw.like("title",queryInfo.getQueryString());
        }
        qw.isNull("parent_id");
        IPage<SysMenu> iPage = super.page(page, qw);
        long total = iPage.getTotal();
        List<SysMenu> records = iPage.getRecords();
        List<MenuVo> menuVos = BeanCopyUtil.copyList(records, MenuVo.class);
        menuVos.forEach(data->{
            List<SysMenu> childrenMenu = sysMenuMapper.findChildrenMenu(data.getId());
            data.setChildren(childrenMenu);
        });
        Page<MenuVo> voPage = new Page<>();
        voPage.setTotal(total);
        voPage.setRecords(menuVos);
        return Result.success("分页数据",voPage);
    }
}
