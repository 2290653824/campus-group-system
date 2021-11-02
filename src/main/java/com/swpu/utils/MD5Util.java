package com.swpu.utils;

import org.springframework.util.StringUtils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密
 * @Author Liyuxi
 * @create 2021-10-19 15:25
 */
public class MD5Util {

    /**
     * 盐，用于混交md5
     */
    private static final String slat = "&%5123***&&%%$$#@";

    /**
     * 加密 无法解密
     * @param password
     * @return
     */
    public static String md5(String password) {
        if (StringUtils.hasText(password)){
            byte[] bytes = null;
            try {
                bytes = MessageDigest.getInstance("md5").digest(password.getBytes());
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            //MD5加密得到16进制
            StringBuilder code = new StringBuilder(new BigInteger(1, bytes).toString(16));
            //保证md5加密后是32位
            for (int i = 0; i < 32 - code.length(); i++) {
                code.insert(0,"0");
            }
            return code.toString();
        }else {
            return null;
        }

    }

    public static void main(String[] args) {
        System.out.println(md5("123456"));
    }
}
