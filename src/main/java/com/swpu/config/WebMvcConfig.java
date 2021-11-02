package com.swpu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
//        registry
//                //允许访问的路径
//                .addMapping("/**")
//                //配置请求来源
//                .allowedOrigins("http://localhost:8080")
//                //允许跨域访问的方法
//                .allowedMethods("GET","POST","DELETE","PUT","OPTION")
//                //是否允许携带参数
//                .allowCredentials(true)
////                .allowedHeaders()
//                //最大相应时间
//                .maxAge(3600);
        registry
                //过滤所有请求
                .addMapping("/**")
                //配置跨域来源
                .allowedOrigins("http://localhost:8080", "null")
                //是否支持跨域Cookie
                .allowCredentials(true)
                //最大响应时间
                .maxAge(3600)
                //设置允许访问的方法
                .allowedMethods("GET", "POST", "PUT", "DELETE");
    }


    private CorsConfiguration corsConfiguration() {
        CorsConfiguration cors = new CorsConfiguration();
        cors.addAllowedOrigin("*");
        cors.addAllowedHeader("*");
        cors.addAllowedMethod("*");
        //将请求头里保存的token暴露出来给前端获取
        cors.addExposedHeader("Authorization");
        return cors;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", this.corsConfiguration());
        return new CorsFilter(source);
    }
}
