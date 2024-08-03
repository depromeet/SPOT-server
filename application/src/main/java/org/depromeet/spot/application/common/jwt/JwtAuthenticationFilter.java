package org.depromeet.spot.application.common.jwt;

import java.io.IOException;
import java.util.Arrays;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.depromeet.spot.domain.member.enums.MemberRole;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

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
        "/api/v1/members",
        "/api/v1/levelUpConditions",
    };

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String requestURI = request.getRequestURI();
        if (Arrays.stream(AUTH_WHITELIST).anyMatch(requestURI::startsWith)) {
            filterChain.doFilter(request, response);
            return;
        }

        // header가 null이거나 빈 문자열이면 안됨.
        if (header != null && !header.equalsIgnoreCase("")) {
            if (header.startsWith(JwtTokenEnums.BEARER.getValue())) {
                String accessToken = header.split(" ")[1];
                if (jwtTokenUtil.isValidateToken(accessToken)) {
                    Long memberId = jwtTokenUtil.getIdFromJWT(accessToken);
                    MemberRole role = MemberRole.valueOf(jwtTokenUtil.getRoleFromJWT(accessToken));
                    JwtToken jwtToken = new JwtToken(memberId, role);
                    SecurityContextHolder.getContext().setAuthentication(jwtToken);
                    filterChain.doFilter(request, response);
                    return;
                }
            }
            // 토큰 검증 실패 -> Exception
        } else throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }
}
