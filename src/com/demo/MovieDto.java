package com.demo;

//로직을 갖고 있지 않는 순수한 데이터 객체
//속성과 그 속성에 접근하기 위한 getter, setter 메소드만 가진 클레스
public class MovieDto {  //Movie 객체
    private int idx;
    private String title;
    private String image;
    private String content;


    public MovieDto(int idx, String title, String image, String content) { //영화 데이터(idx, 제목, 이미지 이름, 내용)
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
        return "MovieDto{" +
                "idx=" + idx +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
