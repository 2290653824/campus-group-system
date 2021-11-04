package com.swpu.service.impl;

import com.swpu.common.Result;
import com.swpu.dto.ProductRemarkDTO;
import com.swpu.entity.ProductRemark;
import com.swpu.mapper.ProductRemarkMapper;
import com.swpu.service.ProductRemarkService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swpu.utils.BeanCopyUtil;
import com.swpu.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 *  服务实现类
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
        if(id==null) {
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
        remark.setCreateTime(new Date());
        this.save(remark);
        return Result.success("新增成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result pdelRemark(Integer id) {
        if (id == null){
            return Result.fail("参数异常");
        }
        boolean b = this.removeById(id);
        if (b){
            return Result.success("删除成功");
        }
        return Result.fail("删除失败");
    }
}
