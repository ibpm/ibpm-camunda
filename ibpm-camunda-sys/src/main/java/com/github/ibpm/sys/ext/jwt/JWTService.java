package com.github.ibpm.sys.ext.jwt;

import com.github.ibpm.common.exception.RTException;
import com.github.ibpm.common.result.sys.user.User;
import com.github.ibpm.config.util.BeanUtil;
import com.github.ibpm.sys.exception.JWTDecodedException;
import com.github.ibpm.sys.exception.JWTExpiredException;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Component
public class JWTService {

    private static final String KEY = "user";

    @Value("${jwt.expire-duration:86400000}")
    private long expireDuration;

    @Value("${jwt.secret:1!2@3#4$5%6^7&8*}")
    private String secret;

    public String createToken(User user) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long current = System.currentTimeMillis();
        Date now = new Date(current);

        Map<String, Object> claims = new HashMap<>();
        String jsonStr = BeanUtil.bean2JsonStr(user);
        claims.put(KEY, jsonStr);

        return Jwts.builder()
                .signWith(signatureAlgorithm, secret)
                .setClaims(claims)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(now)
                .setSubject(user.getUserName())
                .setExpiration(new Date(current + expireDuration))
                .compact();
    }

    public User parseToken(String token) throws JWTExpiredException, JWTDecodedException {
        try{
            Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
            String jsonStr = claims.get(KEY, String.class);
            return BeanUtil.jsonStr2Bean(jsonStr, User.class);
        }catch (MalformedJwtException e){
            throw new JWTDecodedException(e);
        }catch (SignatureException e){
            throw new JWTDecodedException(e);
        }catch (ExpiredJwtException e){
            throw new JWTExpiredException(e);
        }catch(Exception e){
            throw new RTException(1001, e);
        }
    }
}
