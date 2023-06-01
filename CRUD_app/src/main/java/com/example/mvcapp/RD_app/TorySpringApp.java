package com.example.miniProject.demo2;

import com.example.miniProject.spring.ToyContainer;

public class TorySpringApp {
    public static void main(String[] args) throws Exception {
        ToyContainer ctx = new ToyContainer(new String[]{"com.example.miniProject.demo2.ClothesController", "com.example.miniProject.demo2.ClothesServiceImpl"});
        ctx.run();
    }
}
