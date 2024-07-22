package org.depromeet.spot.application.common.aop;

import jakarta.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.depromeet.spot.application.common.jwt.JwtTokenUtil;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Aspect
@RequiredArgsConstructor
@Slf4j
public class JwtTokenAspect {

    private final JwtTokenUtil jwtTokenUtil;

    private final HttpServletRequest request;

    @Around("@annotation(org.depromeet.spot.application.common.annotation.MemberId)")
    public Object getMemberIdFromTokenAspect(ProceedingJoinPoint joinPoint) throws Throwable {
        String jwtToken = request.getHeader("Authorization");
        String access_token = jwtToken.split(" ")[1];

        Long memberId = jwtTokenUtil.getIdFromJWT(access_token);
        if (memberId != null) {
            // 동작하는 메소드의 시그니처를 가져옴.
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Object[] args = joinPoint.getArgs();
            Class<?>[] parameterTypes = signature.getParameterTypes();
            String[] parameterNames = signature.getParameterNames();

            for (int i = 0; i < args.length; i++) {
                if ("memberId".equals(parameterNames[i]) && parameterTypes[i] == Long.class) {
                    args[i] = memberId; // memberId로 변경
                }
            }

            // 변경된 인자로 메서드 실행
            return joinPoint.proceed(args);
        }

        throw new RuntimeException("토큰 내에 memberId가 없습니다.");
    }
}
