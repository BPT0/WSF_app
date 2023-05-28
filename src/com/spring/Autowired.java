package com.spring;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 어노테이션 정보를 런타임 시 까지 유지하도록 설정
@Retention(RetentionPolicy.RUNTIME)  

//어노테이션을 필드에 적용 가능하게 지정
@Target(ElementType.FIELD) 

//'Autowired' 어노테이션을 정의
// 의존성 주입 기능을 사용하여 해당 타입의 빈을 자동으로 연결.
public @interface Autowired {  
}
