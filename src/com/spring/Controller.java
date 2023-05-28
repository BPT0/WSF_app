package com.spring;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 어노테이션 정보를 런타임 시 까지 유지하도록 설정
@Retention(RetentionPolicy.RUNTIME)

// 어노테이션을 클래스에만 적용 가능하게 지정
@Target(ElementType.TYPE)

// 'Controller' 어노테이션을 정의
// 이 어노테이션은 클래스에 붙여 사용하며, 해당 클래스가 MVC 패턴에서 Controller의 역할을 한다는 것을 명시합니다.
public @interface Controller {
}
