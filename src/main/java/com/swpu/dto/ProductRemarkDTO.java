package com.swpu.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDate;
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="新增评论DTO", description="")
public class ProductRemarkDTO {
    @ApiModelProperty(value = "用户主键")
    private Integer userId;
    @ApiModelProperty("产品主键")
    private Integer productId;
    @ApiModelProperty("评论内容")
    private String context;
    @ApiModelProperty("回复人的主键")
    private Integer replyId;
    @ApiModelProperty("父评论的主键")
    private Integer parentId;
}
