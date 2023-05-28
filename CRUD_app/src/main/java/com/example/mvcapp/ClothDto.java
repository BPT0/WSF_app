package com.example.mvcapp;


import lombok.*;

@Getter @Setter @ToString @NoArgsConstructor @AllArgsConstructor
public class ClothDto {
    private int idx;
    private String group;
    private String image;
    private String detail;
}
