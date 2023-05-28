package com.demo;


import com.spring.Autowired;
import com.spring.Controller;
import com.spring.RequestMapping;
import com.spring.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller //컨트롤러 어노테이션: 스프링이 자동 Controller 객체로 인식
public class BasicController {

    @Autowired
    private MovieService service;  //필드 자동 주입

    public BasicController() { //스프링이 자동 Controller 객체로 인식
        System.out.println("BasicController 객체 생성");
    }

    @RequestMapping("/count") //RequestMapping 어노테이션: 클라이언트의 요청 path에 대해 실행될 메소드를 지정
    @ResponseBody //ResponseBody 어노테이션: http요청의 본문 전달
    public String count(Map<String, String> param, Map<String, Object> model) {
        return  "count=" + service.count();
    }

    @RequestMapping("/list")//@ResponseBody를 사용하지 않고 ViewResolver(컨트롤러가 리턴한 이름과 확장자를 보고 실행할 뷰 컴포넌트를 찾는 역할), HTML 리턴과 view 리턴 구분
    public String list(Map<String, String> param, Map<String, Object> model) {
        List<MovieDto> movies = service.select(); //"movies"에 value를 담는다
        for(int i = 0; i < movies.size(); i++) {
            model.put("idx" + i, movies.get(i).getIdx());
            model.put("title" + i, movies.get(i).getTitle());
            model.put("image" + i, movies.get(i).getImage());
        }
        return  "list"; //"movies"를 "list.html"로 넘긴다
    }

    @RequestMapping("/read")
    public String read(Map<String, String> param, Map<String, Object> model) { //(인풋, 아웃풋)
        int idx = Integer.parseInt(param.get("idx")); //인덱스를 가져온다

        MovieDto movie = service.read(idx);  //"movie"에 value를 담는다

        model.put("idx", movie.getIdx());
        model.put("title", movie.getTitle());
        model.put("image", movie.getImage());
        model.put("content", movie.getContent());
        return  "read"; //"movie"를 "read.html"로 넘긴다
    }
}
