package com.example.mvcapp;

import java.util.ArrayList;

public interface ClothService {
    public ArrayList<ClothDto> select();
    public ClothDto read(int idx);

    public boolean  delete(int idx);

    public boolean insert(ClothDto cloth);

    public boolean update(ClothDto cloth);

}
