package com.swpu.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "邮件发送内容")
public class MailVo implements Serializable {

    private static final long serialVersionUID = 8904112977866514278L;

    @ApiModelProperty(value = "是否为HTML格式")
    private boolean html;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "接收人")
    private String recivers;

    @ApiModelProperty(value = "邮件主题")
    private String subject;

    @ApiModelProperty(value = "邮件内容")
    private String content;
}
