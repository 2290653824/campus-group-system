package com.swpu.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.swpu.entity.SysMenu;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="新增菜单DTO", description="")
public class MenuAddDTO implements Serializable {

    private static final long serialVersionUID = -8205552243507064569L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "菜单路径")
    private String path;

    @ApiModelProperty(value = "菜单图标")
    private String icon;

    @ApiModelProperty(value = "菜单名称")
    private String title;

    @ApiModelProperty(value = "菜单组件")
    private String component;

    @ApiModelProperty(value = "父级菜单Id(若本身为父级菜单，则为空)")
    private Long parentId;

    @ApiModelProperty(value = "显示状态(0不显示、1显示)")
    private Boolean status;


}
