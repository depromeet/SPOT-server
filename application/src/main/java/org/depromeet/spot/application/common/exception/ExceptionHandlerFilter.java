package org.depromeet.spot.application.common.exception;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Component
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    private final String UTF_8 = "UTF-8";

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (CustomJwtException e) {
            // logging 안 하면 콘솔에 로그 출력 안 됨.
            logger.error("CustomJwtException : {}", e);
            setErrorResponse(response, e.getJwtErrorCode());
        }
    }

    private void setErrorResponse(HttpServletResponse response, JwtErrorCode jwtErrorCode)
            throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(jwtErrorCode.getStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(UTF_8);
        ErrorResponse errorResponse =
                new ErrorResponse(jwtErrorCode.getCode(), jwtErrorCode.getMessage());
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }

    @Getter
    @AllArgsConstructor
    public static class ErrorResponse {
        private final String code;
        private final String message;
    }
}
