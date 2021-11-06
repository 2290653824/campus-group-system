package com.swpu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.swpu.common.QueryInfo;
import com.swpu.common.Result;
import com.swpu.dto.ProductRemarkDTO;
import com.swpu.entity.ProductRemark;
import com.swpu.entity.SysMenu;
import com.swpu.mapper.ProductRemarkMapper;
import com.swpu.service.ProductRemarkService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swpu.utils.BeanCopyUtil;
import com.swpu.utils.DateUtil;
import com.swpu.utils.SecurityUtil;
import com.swpu.vo.MenuVo;
import com.swpu.vo.ProRemarkVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Liyuxi
 * @since 2021-11-04
 */
@Service
public class ProductRemarkServiceImpl extends ServiceImpl<ProductRemarkMapper, ProductRemark> implements ProductRemarkService {
    @Autowired
    private ProductRemarkMapper productRemarkMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result delRemark(Integer id) {
        if (id == null) {
            return Result.fail("传入参数异常");
        }
        //根据id获取评论
        ProductRemark remark = this.getById(id);
        //修改实体类状态
        remark.setIsDelete(Boolean.FALSE);
        //使用mybatis-plus修改评论
        this.updateById(remark);
        return Result.success("修改成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result addReamrk(ProductRemarkDTO productRemarkDTO) {
        if (productRemarkDTO.getUserId() == null && productRemarkDTO.getProductId() == null) {
            return Result.fail("参数异常");
        }
        if (!StringUtils.hasText(productRemarkDTO.getContext())) {
            return Result.fail("评论的内容不能为空");
        }
        ProductRemark remark = BeanCopyUtil.copyObject(productRemarkDTO, ProductRemark.class);
        remark.setIsDelete(Boolean.TRUE);
        remark.setUserId(SecurityUtil.getUserId());
        remark.setCreateTime(new Date());
        this.save(remark);
        return Result.success("新增成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result pdelRemark(Integer id) {
        if (id == null) {
            return Result.fail("参数异常");
        }
        boolean b = this.removeById(id);
        if (b) {
            return Result.success("删除成功");
        }
        return Result.fail("删除失败");
    }

    @Override
    public Result pdelFindPages(QueryInfo queryInfo) {
        //如果没有传分页参数，则默认第一页，每页10条数据
        queryInfo.setPageNumber(0 == queryInfo.getPageNumber() ? 1 : queryInfo.getPageNumber());
        queryInfo.setPageSize(0 == queryInfo.getPageSize() ? 10 : queryInfo.getPageSize());
        Page<ProductRemark> page = new Page<>(queryInfo.getPageNumber(), queryInfo.getPageSize());
        QueryWrapper<ProductRemark> qw = new QueryWrapper<>();
        if (StringUtils.hasText(queryInfo.getQueryString())) {
            qw.like("context", queryInfo.getQueryString());
        }
        qw.isNull("parent_id");
        qw.eq("is_delete",0);
        IPage<ProductRemark> iPage = super.page(page, qw);
        long total = iPage.getTotal();
        List<ProductRemark> records = iPage.getRecords();
        List<ProRemarkVo> proRemarkVos = BeanCopyUtil.copyList(records, ProRemarkVo.class);
        proRemarkVos.forEach(data -> {
            List<ProductRemark> childrenProRemark = productRemarkMapper.findChildrenProRemark(data.getId());
            data.setChildren(childrenProRemark);
        });
        Page<ProRemarkVo> voPage = new Page<>();
        voPage.setTotal(total);
        voPage.setRecords(proRemarkVos);
        return Result.success("分页数据", voPage);
    }
}
