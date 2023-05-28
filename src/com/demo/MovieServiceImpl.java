package com.demo;


import com.spring.Service;

import java.util.ArrayList;


@Service
public class MovieServiceImpl implements  MovieService{ //구현체

    private ArrayList<MovieDto> db = new ArrayList<>(); //Array 리스트를 이용해 가상의 DataBase 구축, 영화 목록 관리

    public MovieServiceImpl() { //디폴트 생성자에서 데이터 초기화
        System.out.println("MovieServiceImpl 객체 생성");
        db.add(new MovieDto(5, "선다운", "movie5.jpg", "영화 내용 \n선다운"));
        db.add(new MovieDto(6, "노스맨", "movie6.jpg", "영화 내용 \n노스맨"));
        db.add(new MovieDto(7,  "콘서트", "movie7.jpg", "영화 내용 \n콘서트"));
        db.add(new MovieDto(8, "K클래식", "movie8.jpg", "영화 내용 \nK클래식"));
    }

    @Override
    public ArrayList<MovieDto>  select() { //서비스 함수 1
        return db; //전체 메모리 db에 있는 내용을 전체 리턴
    };

    public MovieDto  read(int idx) { //서비스 함수 2
        MovieDto ret = db.stream().filter(m -> m.getIdx() == idx).findAny().get() ;  //stream을 만든 다음 filter를 통해 특정 조건에 맞는 레코드만 찾는다
        return ret;
    }
    @Override
    public int count() { //서비스 함수 3
        return db.size();
    }


}
