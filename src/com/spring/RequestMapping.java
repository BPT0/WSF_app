package com.spring;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 어노테이션 정보를 런타임 시 까지 유지하도록 설정
@Retention(RetentionPolicy.RUNTIME)

// 어노테이션을 메소드에만 적용 가능하게 지정.
@Target(ElementType.METHOD)

// 'RequestMapping'이라는 이름의 어노테이션을 정의
// 이 어노테이션은 메소드에 붙여 사용하며, 해당 메소드가 처리할 HTTP 요청의 URL 경로를 지정
// 이때 URL 경로는 'value' 속성을 통해 지정
public @interface RequestMapping {
    // 이 메소드에 매핑되는 URL경로 지정
    String value();
}
