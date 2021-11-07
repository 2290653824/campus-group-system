package com.swpu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.swpu.common.QueryInfo;
import com.swpu.common.Result;
import com.swpu.dto.CategoryAddDTO;
import com.swpu.entity.Category;
import com.swpu.entity.Commodity;
import com.swpu.mapper.CategoryMapper;
import com.swpu.service.CategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swpu.utils.BeanCopyUtil;
import com.swpu.vo.CategoryVo;
import com.swpu.vo.CommodityVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Liyuxi
 * @since 2021-11-06
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public Result selectById(Integer id) {
        if(id==null||id<=0) return Result.fail("id为空");
        Category category = categoryMapper.selectById(id);
        return Result.success("查询成功",category);


    }

    @Override
    public Result findPage(QueryInfo queryInfo) {
        //如果没有传分页参数，则默认第一页，每页10条数据
        queryInfo.setPageNumber(0 == queryInfo.getPageNumber() ? 1 : queryInfo.getPageNumber());
        queryInfo.setPageSize(0 == queryInfo.getPageSize() ? 10 : queryInfo.getPageSize());
        //新建分页对象
        Page<Category> page = new Page<>(queryInfo.getPageNumber(),queryInfo.getPageSize());
        //新建查询对象
        QueryWrapper<Category> qw = new QueryWrapper<>();
        //判断是否传入条件查询参数
        if (StringUtils.hasText(queryInfo.getQueryString())){
            //模糊查询  plus写法
            qw.like("name",queryInfo.getQueryString());
        }
        IPage<Category> iPage = super.page(page, qw);
        List<Category> records = iPage.getRecords();
        long total = iPage.getTotal();
        List<CategoryVo> categoryVos = BeanCopyUtil.copyList(records, CategoryVo.class);
        Page<CategoryVo> voPage = new Page<>();
        voPage.setRecords(categoryVos);
        voPage.setTotal(total);
        return Result.success("分页参数",voPage);
    }

    @Override
    public Result insertCategory(CategoryAddDTO dto) {
        if(dto==null) return Result.fail("传入的参数异常");
        Category category = BeanCopyUtil.copyObject(dto, Category.class);
        category.setCreateTime(new Date());
        category.setUpdateTime(new Date());
        boolean save = this.save(category);
        if(save) return Result.success("添加分类成功");
        return Result.fail("删除异常");
    }

    @Override
    public Result deleteById(Integer id) {
        if(id == null|| id<=0) return Result.fail("参数异常");
        boolean b = this.removeById(id);
        if(b) return Result.success("删除成功");
        return Result.fail("删除异常");
    }

    @Override
    public Result updateCategory(CategoryAddDTO dto) {
        if(dto==null) return Result.fail("参数异常");
        Category category = BeanCopyUtil.copyObject(dto, Category.class);
        category.setUpdateTime(new Date());
        boolean b = this.updateById(category);
        if(b) return Result.success("更新成功");
        return Result.fail("更新异常");

    }

    @Override
    public Result findCommodities(Long catId) {
        if(catId==null) return Result.fail("参数异常");
        List<Category> commodities = categoryMapper.findCommodities(catId);
        System.out.println(commodities);
        return Result.success("查询商品列表成功",commodities);
    }
}
