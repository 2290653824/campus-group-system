package com.swpu.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.swpu.entity.SysMenu;
import com.swpu.entity.SysPermission;
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
@ApiModel(value="角色回显Vo", description="")
public class RoleVo implements Serializable {

    private static final long serialVersionUID = -7311032400315118086L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "角色描述")
    private String label;

    @ApiModelProperty(value = "角色对应的标签值")
    private String code;

    @ApiModelProperty(value = "显示状态(0不显示、1显示)")
    private Boolean status;

    @ApiModelProperty(value = "菜单列表")
    private List<SysMenu> menus;

    @ApiModelProperty(value = "权限列表")
    private List<SysPermission> permissions;
}
