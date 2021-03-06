package com.swpu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.swpu.common.QueryInfo;
import com.swpu.common.Result;
import com.swpu.dto.CommodityAddDTO;
import com.swpu.entity.Category;
import com.swpu.entity.Commodity;
import com.swpu.entity.ProCategory;
import com.swpu.entity.SysPermission;
import com.swpu.mapper.CategoryMapper;
import com.swpu.mapper.CommodityMapper;
import com.swpu.service.CommodityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swpu.service.ProCategoryService;
import com.swpu.utils.BeanCopyUtil;
import com.swpu.utils.SecurityUtil;
import com.swpu.vo.CommodityVo;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Liyuxi
 * @since 2021-11-03
 */
@Service
public class CommodityServiceImpl extends ServiceImpl<CommodityMapper, Commodity> implements CommodityService {

    @Autowired
    private CommodityMapper commodityMapper;

    @Autowired
    private ProCategoryService proCategoryService;

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public Result SelectById(Integer id) {
        if (id == null || id <= 0) {
            return Result.fail("传入的id异常");
        }
        Commodity byId = this.getById(id);
        return new Result(true,"根据id查询商品成功",byId);
    }

    @Override
    public Result insertCommodity(CommodityAddDTO dto) {
        if (dto == null){
            return Result.fail("参数异常");
        }
        Commodity commodity = BeanCopyUtil.copyObject(dto, Commodity.class);
        commodity.setCreateTime(new Date());
        commodity.setUpdateTime(new Date());
        this.save(commodity);
        if (dto.getCategoryList() != null && dto.getCategoryList().size() > 0){
            //新增商品-分类关联表的关联关系
            List<Category> categoryList = dto.getCategoryList();
            categoryList.forEach(data->{
                ProCategory proCategory = new ProCategory();
                proCategory.setProId(commodity.getId());
                proCategory.setCatId(data.getId());
                proCategoryService.save(proCategory);
            });
        }
        return Result.success("添加商品成功");
    }

    @Override
    public Result deleteById(Integer id) {
        if (id == null) {
            return Result.fail("传入的id异常");
        }
        boolean b = this.removeById(id);
        if (b) {
            return Result.success("删除成功");
        }
        return Result.fail("删除异常");
    }

    @Override
    public Result findPage(QueryInfo queryInfo) {
        //如果没有传分页参数，则默认第一页，每页10条数据
        queryInfo.setPageNumber(0 == queryInfo.getPageNumber() ? 1 : queryInfo.getPageNumber());
        queryInfo.setPageSize(0 == queryInfo.getPageSize() ? 10 : queryInfo.getPageSize());
        //新建分页对象
        Page<Commodity> page = new Page<>(queryInfo.getPageNumber(),queryInfo.getPageSize());
        //新建查询对象
        QueryWrapper<Commodity> qw = new QueryWrapper<>();
        //判断是否传入条件查询参数
        if (StringUtils.hasText(queryInfo.getQueryString())){
            //模糊查询  plus写法
            qw.like("name",queryInfo.getQueryString());
        }
        IPage<Commodity> iPage = super.page(page, qw);
        List<Commodity> records = iPage.getRecords();
        long total = iPage.getTotal();
        List<CommodityVo> commodityVos = BeanCopyUtil.copyList(records, CommodityVo.class);
        commodityVos.forEach(data->{
            data.setCategoryList(categoryMapper.findCategoryByCommodityId(data.getId()));
        });
        Page<CommodityVo> voPage = new Page<>();
        voPage.setRecords(commodityVos);
        voPage.setTotal(total);
        return Result.success("分页参数",voPage);
    }

    @Override
    public Result updateCommodity(CommodityAddDTO dto) {
        if(dto.getId() == null){
            return Result.fail("参数异常");
        }
        Commodity commodity=BeanCopyUtil.copyObject(dto,Commodity.class);
        commodity.setUpdateTime(new Date());
        if (dto.getCategoryList() != null && dto.getCategoryList().size() > 0) {
            //先删除原有的商品-分类关联关系
            proCategoryService.remove(
                    new QueryWrapper<ProCategory>().eq("pro_id",dto.getId()));
            //新增商品-分类关联表的关联关系
            List<Category> categoryList = dto.getCategoryList();
            categoryList.forEach(data -> {
                ProCategory proCategory = new ProCategory();
                proCategory.setProId(commodity.getId());
                proCategory.setCatId(data.getId());
                proCategoryService.save(proCategory);
            });
        }
        this.updateById(commodity);
        return Result.success("修改成功");

    }

    @Override
    public Result findCategories(Long proId) {
        if(proId==null) return Result.fail("参数异常");
        List<Category> categories = commodityMapper.findCategories(proId);
        return Result.success("查询分类列表成功",categories);
    }


}
