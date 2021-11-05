package com.swpu.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="新增评论DTO", description="")
public class ProductRemarkDTO implements Serializable {

    private static final long serialVersionUID = -8222569175249995132L;

    @ApiModelProperty(value = "用户主键")
    private Long userId;

    @ApiModelProperty("产品主键")
    private Long productId;

    @ApiModelProperty("评论内容")
    private String context;

    @ApiModelProperty("回复人的主键")
    private Long replyId;

    @ApiModelProperty("父评论的主键")
    private Long parentId;
}
