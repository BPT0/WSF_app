package com.example.miniProject.demo2;

import lombok.*;
public class ClothesDto {
    private int idx;
    private String title;
    private String image;
    private String content;


    public ClothesDto(int idx, String title, String image, String content) {
        this.idx = idx;
        this.title = title;
        this.image = image;
        this.content = content;
    }


    public int getIdx() {
        return idx;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "ClothesDto{" +
                "idx=" + idx +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}

