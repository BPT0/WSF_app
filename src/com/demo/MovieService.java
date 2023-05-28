package com.demo;

import com.spring.Service;

import java.util.ArrayList;

//비즈니스 로직을 구현(DAO 조합, 트랜잭션 처리)
//DAO에 의존성을 가지고 있음
//인터페이스와 구현체로 이루어짐

@Service //Service 어노테이션: 해당하는 객체(MovieServiceImpl)를 자동으로 new 연산자를 가지고 메모리에 생성
         //Controller 어노테이션과 비슷하지만 추가적인 기능이 더 있음
public interface MovieService { //인터페이스
    public ArrayList<MovieDto> select();
    public MovieDto  read(int idx);
    public int count();
}
