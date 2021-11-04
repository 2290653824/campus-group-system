package com.swpu.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.swpu.entity.ProductRemark;
import com.swpu.entity.SysMenu;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "评论回显Vo", description = "")
public class ProRemarkVo implements Serializable {

    private static final long serialVersionUID = -8288679313742177813L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "用户主键")
    private Integer userId;

    @ApiModelProperty("产品主键")
    private Integer productId;

    @ApiModelProperty("评论内容")
    private String context;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
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
