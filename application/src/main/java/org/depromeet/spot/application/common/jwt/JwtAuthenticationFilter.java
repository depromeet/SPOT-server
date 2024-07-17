package org.depromeet.spot.application.common.jwt;

import java.io.IOException;
import java.util.List;

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

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        List<String> list =
                List.of(
                        // 로그인, 회원가입은 제외
                        "/api/v1/members", "/kakao/");
        boolean flag = list.stream().anyMatch(url -> request.getRequestURI().startsWith(url));
        log.info("flag : {}", flag);
        // 현재 URL 이 LIST 안에 포함되있는걸로 시작하는가?
        if (flag) {
            filterChain.doFilter(request, response);
            return;
        }

        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        log.info("JwtAuthenticationFilter header : {}", header);

        // header가 null이거나 빈 문자열이면 안됨.
        if (header != null && !header.equalsIgnoreCase("")) {
            if (header.startsWith("Bearer")) {
                String access_token = header.split(" ")[1];
                if (jwtTokenUtil.isValidateToken(access_token)) {
                    String memberId = jwtTokenUtil.getIdFromJWT(access_token);
                    MemberRole role = MemberRole.valueOf(jwtTokenUtil.getRoleFromJWT(access_token));
                    JwtToken jwtToken = new JwtToken(memberId, role);
                    SecurityContextHolder.getContext().setAuthentication(jwtToken);
                    filterChain.doFilter(request, response);
                }
            }
            // 토큰 검증 실패 -> Exception
        } else throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }
}
