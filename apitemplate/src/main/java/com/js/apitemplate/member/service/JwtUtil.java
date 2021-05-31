package com.js.apitemplate.member.service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.js.apitemplate.member.vo.MemberVo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;


@Component
public class JwtUtil {
	public final static long TOKEN_VALIDATION_SECOND = 1000L * 10;
    public final static long REFRESH_TOKEN_VALIDATION_SECOND = 1000L * 60 * 24 * 2;

    final static public String ACCESS_TOKEN_NAME = "accessToken";
    final static public String REFRESH_TOKEN_NAME = "refreshToken";

    
    
    
    @Value("${spring.jwt.secret}")
    private String SECRET_KEY ;

    
//    @PostConstruct
//    protected void init() {
//    	System.out.println("라라랄라");
//    	SECRET_KEY = Base64.getEncoder().encodeToString(SECRET_KEY.getBytes());
//    }
    
    private Key getSigningKey(String secretKey) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * 토큰이 있는지 없는지 검사, 토큰에 담김 페이로드 가져온다
     * @param token
     * @return
     * @throws ExpiredJwtException
     */
    public Claims extractAllClaims(String token) throws ExpiredJwtException {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey(SECRET_KEY))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 페이로드로부터 userId 가져온다
     * @param token
     * @return
     */
    public String getUserId(String token) {
        return extractAllClaims(token).get("userId", String.class);
    }

    /**
     * 토큰 만료되었는지 확인
     * @param token
     * @return
     */
    public Boolean isTokenExpired(String token) {
        final Date expiration = extractAllClaims(token).getExpiration();
        return expiration.before(new Date());
    }

    public String generateToken(MemberVo memberVo) {
        return doGenerateToken(memberVo.getMberId(), TOKEN_VALIDATION_SECOND);
    }

    public String generateRefreshToken(MemberVo memberVo) {
        return doGenerateToken(memberVo.getMberId(), REFRESH_TOKEN_VALIDATION_SECOND);
    }

    /**
     * 토큰 생성, 페이로드에 담을 값은 username? userid? userId!!
     * @param userId
     * @param expireTime
     * @return
     */
    public String doGenerateToken(String userId, long expireTime) {

        Claims claims = Jwts.claims();
        claims.put("userId", userId);

        String jwt = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(getSigningKey(SECRET_KEY), SignatureAlgorithm.HS256)
                .compact();

        return jwt;
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUserId(token);

        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}
