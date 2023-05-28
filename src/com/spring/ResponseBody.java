package com.spring;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 어노테이션 정보를 런타임 시 까지 유지하도록 설정
@Retention(RetentionPolicy.RUNTIME)

// 어노테이션을 메소드에만 적용 가능하게 지정
@Target(ElementType.METHOD)

// 'ResponseBody' 어노테이션을 정의
// 메소드에 붙여 사용하며, 해당 메소드의 반환값이 HTTP 응답 본문(Response Body)이 됨
// 즉, 메소드의 반환값을 View를 통해 처리하는 것이 아니라, 직접 HTTP 응답으로 사용한다는 의미
public @interface ResponseBody {
}
