package org.depromeet.spot.application.common.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.WeakKeyException;
import jakarta.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.spec.SecretKeySpec;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.depromeet.spot.domain.member.MemberRole;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenUtil {
    // JWT를 생성하고 관리하는 클래스

    // 토큰에 사용되는 시크릿 키
    @Value("${spring.jwt.secret}")
    private String secretKey;

    // 객체 초기화, secretKey를 Base64로 인코딩
    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String generateToken(Long memberId, MemberRole memberRole){
        return Jwts.builder()
                .setHeader(createHeader())
                .setClaims(createClaims(memberRole))
                .setSubject(memberId.toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60)) // 토큰 만료 시간
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String getIdFromJWT(String token) {
        Claims claims = Jwts.parser()
            .setSigningKey(secretKey)
            .parseClaimsJws(token)
            .getBody();

        return claims.get("id", String.class);
    }

    public String getRoleFromJWT(String token) {
        Claims claims = Jwts.parser()
            .setSigningKey(secretKey)
            .parseClaimsJws(token)
            .getBody();

        return claims.get("role", String.class);
    }

    public Jws<Claims> getClaims(String token){
        return Jwts.parserBuilder()
            .setSigningKey(createSignature())
            .build()
            .parseClaimsJws(token);
    }

    public boolean isValidToken(String token) {
        try {
            Jws<Claims> claims = getClaims(token);
            return true;
        } catch (ExpiredJwtException exception) {
            log.error("Token Expired");
            throw new ExpiredJwtException(exception.getHeader(), exception.getClaims(),token);
        } catch (UnsupportedJwtException | WeakKeyException exception) {
            log.error("Unsupported Token");
            throw new UnsupportedJwtException("지원되지 않는 토큰입니다.");
        } catch (MalformedJwtException | IllegalArgumentException exception){
            throw new MalformedJwtException("잘못된 형식의 토큰입니다.");
        }
    }



    private Map<String,Object> createHeader(){
        // 헤더 생성
        Map<String,Object> headers = new HashMap<>();

        headers.put("typ", "JWT");
        headers.put("alg", "HS256"); // 서명? 생성에 사용될 알고리즘

        return headers;
    }

    // Claim -> 정보를 key-value 형태로 저장함.
    private Map<String,Object> createClaims(MemberRole role){
        Map<String,Object> claims = new HashMap<>();

        claims.put("role", role);
        return claims;
    }

    private Key createSignature() {
        byte[] apiKeySecretBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());
    }

}
