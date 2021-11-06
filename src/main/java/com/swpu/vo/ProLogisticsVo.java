package com.swpu.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="物流回显Vo", description="")
public class ProLogisticsVo implements Serializable {
    private static final long serialVersionUID = -8049989811778002533L;

    @ApiModelProperty(value = "物流主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "物流公司名")
    private String name;

    @ApiModelProperty(value = "物流公司电话号码")
    private String telephone;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间")
    private Date creteTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "更改时间")
    private Date updateTime;

    @ApiModelProperty(value = "物流价格")
    private BigDecimal price;

    @ApiModelProperty(value = "验证是否删除")
    private Boolean isDelete;

    @ApiModelProperty(value = "图片地址")
    private String imgLocation;
}
