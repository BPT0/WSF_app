package com.demo;


import com.spring.Service;

import java.util.ArrayList;


@Service
public class MovieServiceImpl implements  MovieService{

    private ArrayList<MovieDto> db = new ArrayList<>();

    public MovieServiceImpl() {
        System.out.println("MovieServiceImpl 객체 생성");
        db.add(new MovieDto(5, "선다운", "movie5.jpg", "영화 내용 \n선다운"));
        db.add(new MovieDto(6, "노스맨", "movie6.jpg", "영화 내용 \n노스맨"));
        db.add(new MovieDto(7,  "콘서트", "movie7.jpg", "영화 내용 \n콘서트"));
        db.add(new MovieDto(8, "K클래식", "movie8.jpg", "영화 내용 \nK클래식"));
    }

    @Override
    public ArrayList<MovieDto>  select() {
        return db;
    };

    public MovieDto  read(int idx) {
        MovieDto ret = db.stream().filter(m -> m.getIdx() == idx).findAny().get() ;
        return ret;
    }
    @Override
    public int count() {
        return db.size();
    }


}
