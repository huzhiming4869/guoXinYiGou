package com.xcy.gxyg.common.util;


import com.xcy.gxyg.common.entity.ApplyUser;
import com.xcy.gxyg.common.entity.BackstageUser;
import com.xcy.gxyg.common.exception.BusinessException;
import com.xcy.gxyg.common.res.ResponseEnum;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

/**
 * @author Ming
 */
@Component
public class JwtUtils {


    @Resource
    private RedisUtil redisUtil;

    private static long tokenExpiration = 3 * 24 * 60 * 60 * 1000;

    private Key getBackstageInstance() {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        String backstageTokenKey = "G1U2O3X4I5N6Y7I8J9I0E1B2A3C4K5S6T7A8G9E";
        byte[] bytes = DatatypeConverter.parseBase64Binary(backstageTokenKey);
        return new SecretKeySpec(bytes, signatureAlgorithm.getJcaName());
    }

    private Key getApplyInstance() {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        String applyKeyTokenKey = "G1U2O3X4I5N6Y7I8J9I0E1A2P3P4L5Y";
        byte[] bytes = DatatypeConverter.parseBase64Binary(applyKeyTokenKey);
        return new SecretKeySpec(bytes, signatureAlgorithm.getJcaName());
    }


    public String createBackstageToken(BackstageUser backstageUser) {
        return Jwts.builder()
                .setSubject("GXYG-BACKSTAGE-USER")
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
                .claim("backstageUserId", backstageUser.getId())
                .claim("password", backstageUser.getPassword())
                .claim("roleId", backstageUser.getRoleId())
                .claim("account", backstageUser.getAccount())
                .signWith(SignatureAlgorithm.HS512, getBackstageInstance())
                .compressWith(CompressionCodecs.GZIP)
                .compact();
    }

    public String createApplyToken(ApplyUser applyUser) {
        return Jwts.builder()
                .setSubject("GXYG-APPLY-USER")
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
                .claim("applyUserId", applyUser.getId())
                .claim("account", applyUser.getAccount())
                .claim("aliAccount", applyUser.getAliAccount())
                .signWith(SignatureAlgorithm.HS512, getApplyInstance())
                .compressWith(CompressionCodecs.GZIP)
                .compact();
    }


    /**
     * Apply用户 判断token是否有效
     *
     * @param token
     * @return
     */
    public boolean checkApplyToken(String token) {
        if (StringUtils.isEmpty(token)) {
            return false;
        }
        try {
            Jwts.parser().setSigningKey(getApplyInstance()).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Backstage用户 判断token是否有效
     *
     * @param token
     * @return
     */
    public boolean checkBackstageToken(String token) {
        if (StringUtils.isEmpty(token)) {
            return false;
        }
        try {
            Jwts.parser().setSigningKey(getBackstageInstance()).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    //单点登录
//    public boolean checkRepeatLogin(String token) {
//        try {
//            String redisToken = redisUtil.get(getUserId(token).toString());
//            if (StrUtil.isBlank(redisToken)) {
//                return true;
//            }
//            return token.equals(redisToken);
//        } catch (Exception e) {
//            return false;
//        }
//    }


    public Long getBackstageUserId(String token) {
        Claims claims = getBackstageClaims(token);
        return Long.parseLong(claims.get("backstageUserId").toString());
    }

    public String getBackstagePassWord(String token) {
        Claims claims = getBackstageClaims(token);
        return claims.get("password") + "";
    }

    public String getBackstageAccount(String token) {
        Claims claims = getBackstageClaims(token);
        return claims.get("account") + "";
    }

    public Long getBackstageRoleId(String token) {
        Claims claims = getBackstageClaims(token);
        return Long.parseLong(claims.get("roleId").toString());
    }


    public void removeToken(String token) {
        //jwttoken无需删除，客户端扔掉即可。
    }


    /**
     * 校验token并返回Claims
     *
     * @param token
     * @return
     */
    private Claims getBackstageClaims(String token) {
        if (StringUtils.isEmpty(token)) {
            // LOGIN_AUTH_ERROR(-211, "未登录"),
            throw new BusinessException(ResponseEnum.LOGIN_AUTH_ERROR);
        }
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(getBackstageInstance()).parseClaimsJws(token);
            Claims claims = claimsJws.getBody();
            return claims;
        } catch (Exception e) {
            throw new BusinessException(ResponseEnum.LOGIN_AUTH_ERROR);
        }
    }

    /**
     * 校验token并返回Claims
     *
     * @param token
     * @return
     */
    private Claims getApplyClaims(String token) {
        if (StringUtils.isEmpty(token)) {
            // LOGIN_AUTH_ERROR(-211, "未登录"),
            throw new BusinessException(ResponseEnum.LOGIN_AUTH_ERROR);
        }
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(getApplyInstance()).parseClaimsJws(token);
            Claims claims = claimsJws.getBody();
            return claims;
        } catch (Exception e) {
            throw new BusinessException(ResponseEnum.LOGIN_AUTH_ERROR);
        }
    }
}