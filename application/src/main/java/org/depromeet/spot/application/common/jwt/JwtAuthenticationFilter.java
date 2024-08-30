package org.depromeet.spot.application.common.jwt;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.depromeet.spot.application.common.exception.CustomJwtException;
import org.depromeet.spot.application.common.exception.JwtErrorCode;
import org.depromeet.spot.domain.member.enums.MemberRole;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;

    private static final String[] AUTH_WHITELIST = {
        "/swagger-ui",
        "/api-docs",
        "swagger-ui-custom.html",
        "/v3/api-docs",
        "/api-docs",
        "/swagger-ui.html",
        "/favicon.ico",
        "/actuator",
        "/api/v1/levels/info",
        "/kakao",
        "/api/v1/jwts",
        "/google/callback",
        "/trackEvent",
    };

    private static final Map<String, Set<String>> AUTH_METHOD_WHITELIST =
            Map.of(
                    "/api/v1/members",
                    Set.of("GET", "POST"),
                    "/api/v2/members",
                    Set.of("GET", "POST"),
                    "/login/oauth2/code/google",
                    Set.of("GET"),
                    "/api/v1/members/delete",
                    Set.of("DELETE"),
                    "/api/v1/baseball-teams",
                    Set.of("GET"),
                    "/api/v2/GOOGLE",
                    Set.of("GET"),
                    "/api/v2/KAKAO",
                    Set.of("GET"));

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String requestURI = request.getRequestURI();
        final String requestMethod = request.getMethod();

        if (checkMethodWhitelist(requestURI, requestMethod)) {
            filterChain.doFilter(request, response);
            return;
        }
        // header가 null이거나 빈 문자열이면 안됨.
        if (header == null || header.isEmpty()) {
            throw new CustomJwtException(JwtErrorCode.NONEXISTENT_TOKEN);
        }

        if (header.startsWith(JwtTokenEnums.BEARER.getValue())) {
            String accessToken = header.split(" ")[1];
            if (jwtTokenUtil.isValidateToken(accessToken)) {
                Long memberId = jwtTokenUtil.getIdFromJWT(accessToken);
                MemberRole role = MemberRole.valueOf(jwtTokenUtil.getRoleFromJWT(accessToken));
                JwtToken jwtToken = new JwtToken(memberId, role);
                SecurityContextHolder.getContext().setAuthentication(jwtToken);
                filterChain.doFilter(request, response);
            }
        }
        // 토큰 검증 실패 -> Exception
        else throw new CustomJwtException(JwtErrorCode.INVALID_TOKEN);
    }

    private boolean checkMethodWhitelist(String requestURI, String requestMethod) {
        if (Arrays.stream(AUTH_WHITELIST).anyMatch(requestURI::startsWith)) {
            return true;
        }

        Optional<String> matchUrl =
                AUTH_METHOD_WHITELIST.keySet().stream().filter(requestURI::startsWith).findFirst();
        if (matchUrl.isPresent()
                && AUTH_METHOD_WHITELIST
                        .getOrDefault(matchUrl.get(), Set.of())
                        .contains(requestMethod)) {
            return true;
        }
        return false;
    }
}
