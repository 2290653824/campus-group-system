package com.swpu.vo;

import com.swpu.entity.SysRole;
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
@ApiModel(value="用户回显Vo", description="")
public class UserVo implements Serializable {

    private static final long serialVersionUID = -7915023335860421390L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "登录名")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "性别(0男，1女，2未知)")
    private Integer sex;

    @ApiModelProperty(value = "用户头像")
    private String avatar;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "微信小程序openid，每个用户对应一个，且唯一")
    private String openId;

    @ApiModelProperty(value = "状态，是否禁用")
    private Boolean status;

    @ApiModelProperty(value = "是否是管理员")
    private Boolean admin;

    @ApiModelProperty(value = "电话号码")
    private String phoneNumber;

    @ApiModelProperty(value = "用户邮箱")
    private String email;

    @ApiModelProperty(value = "用户拥有的多个角色")
    private List<SysRole> roles;
}
