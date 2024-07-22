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

    // Before 사용 시
    // [ProjectingArgumentResolverRegistrar.class]: BeanPostProcessor before instantiation of bean
    // failed
    // 에러 발생함. 이유 아직 모르겠음 ㅠㅠ
    //
    // Q) Around는  ProceedingJoinPoint를 사용하여 메소드를 호출할 수 있고,
    // 메서드의 인자를 변경하거나 직접적으로 메소드의 실행을 제어가능함.
    // Before는 실행 전에만 동작하면서 메소드의 인자를 바꾸거나 직접 제어가 불가능함.
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
