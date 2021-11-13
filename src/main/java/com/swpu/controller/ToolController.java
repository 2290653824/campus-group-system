package com.swpu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.swpu.common.Result;
import com.swpu.entity.SysUser;
import com.swpu.service.AliOssService;
import com.swpu.service.SysUserService;
import com.swpu.utils.MD5Util;
import com.swpu.utils.MailUtils;
import com.swpu.utils.RedisUtil;
import com.swpu.vo.MailVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Random;

@RestController
@Slf4j
@Api(tags = "通用工具")
@RequestMapping("tool")
public class ToolController {

    @Autowired
    private AliOssService aliOssService;

    @Autowired
    private MailUtils mailUtils;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("upload")
    @ApiOperation("oos上传文件")
    public Result uoload(MultipartFile file){
        String upload = aliOssService.upload(file);
        return Result.success("url",upload);
    }

    @PostMapping("deleteFile")
    @ApiOperation("删除替换之后的头像")
    public Result deleteFile(String file){
        try {
            String[] split = file.split(".com/");
            System.out.println(split[1]);
            aliOssService.deleteFile(split[1]);
            return Result.success("删除成功");
        }catch (Exception e){
            log.error("删除错误");
            return Result.fail("删除失败");
        }
    }

    @ApiOperation("邮件找回密码")
    @PostMapping("forgetPassword")
    public Result forGet(@RequestBody MailVo mail){
        mail.setSubject("评分系统平台");
        Random random = new Random();
        int password = random.nextInt(999999);
        mail.setContent("您的新密码: "+password+",请妥善保管!");
        QueryWrapper<SysUser> qw = new QueryWrapper<>();
        qw.eq("user_name",mail.getUsername());
        qw.eq("email",mail.getRecivers());
        qw.last("limit 1");
        SysUser one = sysUserService.getOne(qw);
        one.setPassword(passwordEncoder.encode(MD5Util.md5(String.valueOf(password))));
        sysUserService.updateById(one);
        return Result.success(mailUtils.sendSimpleMail(mail));
    }

}
