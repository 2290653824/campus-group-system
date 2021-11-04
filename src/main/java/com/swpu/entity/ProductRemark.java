package com.swpu.entity;

import java.time.LocalDate;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author Liyuxi
 * @since 2021-11-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="ProductRemark对象", description="")
public class ProductRemark implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
   @ApiModelProperty(value = "用户主键")
    private Integer userId;
    @ApiModelProperty("产品主键")
    private Integer productId;
    @ApiModelProperty("评论内容")
    private String context;

    @TableField("createTime")
    private LocalDate createTime;
    @ApiModelProperty("回复人的主键")
    private Integer replyId;
    @ApiModelProperty("父评论的主键")
    private Integer parentId;
    @ApiModelProperty("是否删除标记")
    private Boolean isDelete;


}
