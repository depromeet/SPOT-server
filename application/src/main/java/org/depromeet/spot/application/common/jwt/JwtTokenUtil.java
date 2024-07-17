package org.depromeet.spot.application.common.jwt;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;

import org.depromeet.spot.domain.member.Member;
import org.depromeet.spot.domain.member.enums.MemberRole;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
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

    public HttpHeaders getJWTToken(Member member) {
        // TODO 토큰 구현하기.

        // jwt 토큰 생성
        String token = generateToken(member.getId(), member.getRole());

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        return headers;
    }

    public String generateToken(Long memberId, MemberRole memberRole) {
        return Jwts.builder()
                .setHeader(createHeader())
                .setClaims(createClaims(memberRole))
                .setSubject(memberId.toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(
                        new Date(
                                System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 30L)) // 토큰 만료 시간
                .signWith(SignatureAlgorithm.HS256, SECRETKEY.getBytes())
                .compact();
    }

    public String getIdFromJWT(String token) {
        return Jwts.parser()
                .setSigningKey(SECRETKEY.getBytes())
                .parseClaimsJws(token)
                .getBody()
                .get("id", String.class);
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
        try {
            Jws<Claims> claims = getClaims(token);
            return true;
        } catch (ExpiredJwtException exception) {
            log.error("Token Expired");
            throw new ExpiredJwtException(exception.getHeader(), exception.getClaims(), token);
        } catch (UnsupportedJwtException | WeakKeyException exception) {
            log.error("Unsupported Token");
            throw new UnsupportedJwtException("지원되지 않는 토큰입니다.");
        } catch (MalformedJwtException | IllegalArgumentException exception) {
            throw new MalformedJwtException("잘못된 형식의 토큰입니다.");
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
    private Map<String, Object> createClaims(MemberRole role) {
        Map<String, Object> claims = new HashMap<>();

        claims.put("role", role);
        return claims;
    }

    private Key createSignature() {
        byte[] apiKeySecretBytes = SECRETKEY.getBytes();
        return new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());
    }
}
