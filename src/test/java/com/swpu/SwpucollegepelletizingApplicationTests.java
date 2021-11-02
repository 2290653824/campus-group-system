package com.swpu;

import com.swpu.utils.MD5Util;
import com.swpu.utils.MailUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class SwpucollegepelletizingApplicationTests {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MailUtils mailUtils;

    @Test
    void contextLoads() {
        System.out.println(passwordEncoder.encode(MD5Util.md5("123456")));
        boolean result = passwordEncoder.matches(MD5Util.md5("123456"),
                passwordEncoder.encode(MD5Util.md5("123456")));
        System.out.println(result);
    }

}
