package com.swpu.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class TokenUtil {


    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    /**
     * 传入用户信息，生成token
     * @param userDetails
     * @return
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", userDetails.getUsername());
        claims.put("created", new Date());
        return this.generateToken(claims);
    }

    /**
     * 根据荷载生成token
     * @param claims
     * @return
     */
    public String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret)
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .compact();
    }

    /**
     * 获取token
     * @param token
     * @return
     */
    public Claims getTokenBody(String token){
        try {
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 根据token得到用户名
     * @param token
     * @return
     */
    public String getUsernameByToken(String token){
        return (String) this.getTokenBody(token).get("username");
    }

    /**
     * token是否过期
     * @param token
     * @return
     */
    public boolean isExpiration(String token) {
        return this.getTokenBody(token).getExpiration().before(new Date());
    }

    /**
     * 刷新token
     * @param token
     * @return
     */
    public String refreshToken(String token) {
        Claims claims = this.getTokenBody(token);
        claims.setExpiration(new Date());
        return this.generateToken(claims);
    }
}
