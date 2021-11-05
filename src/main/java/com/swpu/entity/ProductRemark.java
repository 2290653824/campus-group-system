package com.swpu.entity;

import java.time.LocalDate;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
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
    private Long id;

    @ApiModelProperty(value = "用户主键")
    private Long userId;

    @ApiModelProperty("产品主键")
    private Integer productId;

    @ApiModelProperty("评论内容")
    private String context;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @TableField("createTime")
    private Date createTime;

    @ApiModelProperty("回复人的主键")
    private Integer replyId;

    @ApiModelProperty("父评论的主键")
    private Integer parentId;

    @ApiModelProperty("是否删除标记")
    private Boolean isDelete;

    @ApiModelProperty(value = "子评论")
    @TableField(exist = false)
    private List<ProductRemark> children;
}
