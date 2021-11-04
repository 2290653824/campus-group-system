package com.swpu.service.impl;

import com.swpu.common.Result;
import com.swpu.entity.ProductRemark;
import com.swpu.mapper.ProductRemarkMapper;
import com.swpu.service.ProductRemarkService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Result delRemark(Integer id) {
        if(id==null||id<0)
        {
            return Result.fail("参数不合法");
        }
        int count=productRemarkMapper.delRemark(id);
        if(count==0)
        {
            return Result.fail("未查到删除资源");
        }else if (count!=1)
        {
          return   Result.fail("删除异常。");
        }
        else {
            return Result.success("成功删除");
        }
    }
}
