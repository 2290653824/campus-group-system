package com.swpu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

/**
 * <p>
 *
 * </p>
 *
 * @author Liyuxi
 * @since 2021-11-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="SysUser对象", description="")
public class SysUser implements UserDetails {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "登录名")
    private String userName;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "性别(0男，1女，2未知)")
    private Boolean sex;

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
    @TableField(exist = false)
    private List<SysRole> roles;

    @ApiModelProperty(value = "角色对应的子菜单")
    @TableField(exist = false)
    private List<SysMenu> menus;

    @ApiModelProperty(value = "角色对应的权限")
    @TableField(exist = false)
    private List<SysPermission> permissions;

    /**
     * 权限数据
     * @return
     */
    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<GrantedAuthority> list = new ArrayList<>();
        //角色信息
        if (roles != null && roles.size() > 0){
            roles.forEach(item->{
                if (StringUtils.hasText(item.getCode())){
                    list.add(new SimpleGrantedAuthority("ROLE_" + item.getCode()));
                }
            });
        }
        //权限信息
        if (permissions != null && permissions.size() > 0){
            permissions.forEach(item->{
                if (StringUtils.hasText(item.getCode())){
                    list.add(new SimpleGrantedAuthority(item.getCode()));
                }
            });
        }
        return list;
    }

    /**
     * 用户名
     * @return
     */
    @Override
    public String getUsername() {
        return userName;
    }

    /**
     * 账号是否过期
     * @return
     */
    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return false;
    }
    /**
     * 账号是否锁定
     * @return
     */
    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return false;
    }

    /**
     *
     * @return
     */
    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return false;
    }
    /**
     * 是否被禁用
     * @return
     */
    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return status;
    }
}
