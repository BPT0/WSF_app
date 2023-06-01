package com.example.miniProject.demo2;

import java.util.ArrayList;

public interface ClothesService {
    public ArrayList<ClothesDto> select();
    public ClothesDto  read(int idx);
    public boolean  delete(int idx); //delete 추가
    public int count();
}
