package org.depromeet.spot.application.common.jwt;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;

import jakarta.servlet.http.HttpServletRequest;

import org.depromeet.spot.application.common.exception.CustomJwtException;
import org.depromeet.spot.application.common.exception.JwtErrorCode;
import org.depromeet.spot.domain.member.Member;
import org.depromeet.spot.domain.member.enums.MemberRole;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.WeakKeyException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenUtil {
    // JWT를 생성하고 관리하는 클래스

    // 토큰에 사용되는 시크릿 키
    @Value("${spring.jwt.secret}")
    private String SECRETKEY;

    public String getJWTToken(Member member) {
        // TODO 토큰 구현하기.

        // jwt 토큰 생성
        return generateToken(member.getId(), member.getRole());
    }

    public String generateToken(Long memberId, MemberRole memberRole) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime expiredAtDateTime = currentDateTime.plusYears(1);

        ZonedDateTime zonedCurrent = expiredAtDateTime.atZone(ZoneId.systemDefault());
        ZonedDateTime zonedExpire = expiredAtDateTime.atZone(ZoneId.systemDefault());
        Date current = Date.from(zonedCurrent.toInstant());
        Date expiredAt = Date.from(zonedExpire.toInstant());

        return Jwts.builder()
                .setHeader(createHeader())
                .setClaims(createClaims(memberId, memberRole))
                .setIssuedAt(current)
                .setExpiration(expiredAt)
                .signWith(SignatureAlgorithm.HS256, SECRETKEY.getBytes())
                .compact();
    }

    public Long getIdFromJWT(String token) {
        return Jwts.parser()
                .setSigningKey(SECRETKEY.getBytes())
                .parseClaimsJws(token)
                .getBody()
                .get("memberId", Long.class);
    }

    public String getRoleFromJWT(String token) {
        return Jwts.parser()
                .setSigningKey(SECRETKEY.getBytes())
                .parseClaimsJws(token)
                .getBody()
                .get("role", String.class);
    }

    public Jws<Claims> getClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(createSignature()).build().parseClaimsJws(token);
    }

    public boolean isValidateToken(String token) {
        if (token == null) {
            throw new CustomJwtException(JwtErrorCode.NONEXISTENT_TOKEN);
        }
        try {
            Jws<Claims> claims = getClaims(token);
            return true;
        } catch (ExpiredJwtException exception) {
            log.error("Token Expired");
            throw new CustomJwtException(JwtErrorCode.EXPIRED_TOKEN);
        } catch (UnsupportedJwtException
                | WeakKeyException
                | MalformedJwtException
                | IllegalArgumentException exception) {
            log.error("Unsupported Token");
            throw new CustomJwtException(JwtErrorCode.INVALID_TOKEN);
        }
    }

    private Map<String, Object> createHeader() {
        // 헤더 생성
        Map<String, Object> headers = new HashMap<>();

        headers.put("typ", "JWT");
        headers.put("alg", "HS256"); // 서명? 생성에 사용될 알고리즘

        return headers;
    }

    // Claim -> 정보를 key-value 형태로 저장함.
    private Map<String, Object> createClaims(Long memberId, MemberRole role) {
        Map<String, Object> claims = new HashMap<>();

        claims.put("memberId", memberId);
        claims.put("role", role);
        return claims;
    }

    private Key createSignature() {
        byte[] apiKeySecretBytes = SECRETKEY.getBytes();
        return new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());
    }

    public String getAccessToken(HttpServletRequest request) {
        String jwtToken = request.getHeader("Authorization");
        isValidateToken(jwtToken);
        return jwtToken.split(" ")[1];
    }
}
