package org.depromeet.spot.application.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 해당 어노테이션은 헤더에 담긴 Jwt 토큰에서 <b>memberId</b> 값을 가져오는 어노테이션입니다. <br>
 * 컨트롤러에서 사용하려는 <b>메소드</b>에 사용하면됩니다. <br>
 * <br>
 * <b>파라미터</b>에 <b>Long memberId</b>를 추가해주어야 합니다. <br>
 * <b>@Parameter(hidden = true) Long memberId</b>로 하시면 <br>
 * 해당 파라미터는 Swagger에서 제외됩니다. :)
 */
@Target(ElementType.METHOD) // 적용 대상 : 메소드
@Retention(RetentionPolicy.RUNTIME) // 런타임에 사용할 수 있도록 설정
public @interface MemberId {}
