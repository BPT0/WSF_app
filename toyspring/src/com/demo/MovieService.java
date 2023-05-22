package com.demo;

import com.spring.Service;

import java.util.ArrayList;

@Service
public interface MovieService {
    public ArrayList<MovieDto> select();
    public MovieDto  read(int idx);
    public int count();
}
