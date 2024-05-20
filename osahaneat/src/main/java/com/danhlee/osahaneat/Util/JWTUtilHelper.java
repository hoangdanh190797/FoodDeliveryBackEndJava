package com.danhlee.osahaneat.Util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class JWTUtilHelper {
    @Value("${jwt.privateKey}")
    private String privateKey;

    public String generateToken(String data) {
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(privateKey));
        String jws = Jwts.builder().subject(data).signWith(key).compact();
        return jws;
    }

    public boolean verifyToken(String token) {
        Jws<Claims> jws;
        try {
            SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(privateKey));
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            //Code cũ: Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJwt(token);
            //Đọc .doc github để làm!
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
