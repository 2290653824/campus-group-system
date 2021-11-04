package com.swpu.service.impl;

import com.swpu.common.Result;
import com.swpu.dto.ProductRemarkDTO;
import com.swpu.entity.ProductRemark;
import com.swpu.mapper.ProductRemarkMapper;
import com.swpu.service.ProductRemarkService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swpu.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        if(id==null||id<0) {
            return Result.fail("传入参数异常");
        }
        int count=productRemarkMapper.delRemark(id);
        if(count==0) {
            return Result.fail("未查到删除资源");
        }else if (count!=1) {
          return  Result.fail("删除异常。");
        } else {
            return Result.success("成功删除");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result addReamrk(ProductRemarkDTO productRemarkDTO) {
        if (productRemarkDTO==null) {
            return Result.fail("传入参数异常");
        }
        ProductRemark productRemark=new ProductRemark();
        if ("".equals(productRemarkDTO.getContext().trim())) {
            return Result.fail("评论的内容不能为空");
        } else if (productRemarkDTO.getUserId()<0||productRemarkDTO.getUserId()==null) {
            return Result.fail("未识别用户,用户可能发生异常");
        } else if (productRemarkDTO.getProductId()<0||productRemarkDTO.getProductId()==null) {
            return Result.fail("未识别到商品");
        } else {
            productRemark.setContext(productRemarkDTO.getContext().trim());
            productRemark.setUserId(productRemark.getUserId());
            productRemark.setIsDelete(false);
            productRemark.setReplyId(productRemarkDTO.getReplyId());
            productRemark.setProductId(productRemarkDTO.getProductId());
            productRemark.setParentId(productRemarkDTO.getParentId());
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            productRemark.setCreateTime(new Date());
            int count=productRemarkMapper.insert(productRemark);
            if(count==0) {
              return  Result.fail("添加失败");
            }else if(count!=1) {
                return Result.fail("添加发生异常");
            }else {
                return Result.success("添加成功");
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result pdelRemark(Integer id) {
        if(id<0||id==null) {
            return Result.fail("传入参数有问题");
        } else {
            int count=productRemarkMapper.deleteById(id);
            if (count==0) {
                return Result.fail("该商品可能已经删除");
            } else if (count!=1) {
              return Result.fail("删除发生异常");
            } else {
                return Result.success("删除成功");
            }
        }
    }
}
