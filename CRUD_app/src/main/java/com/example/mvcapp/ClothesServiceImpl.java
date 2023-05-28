package com.example.mvcapp;

import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ClothesServiceImpl implements ClothService {

    private ArrayList<ClothDto> db = new ArrayList();

    public ClothesServiceImpl() {
        System.out.println("ClothesServiceImpl 객체 생성");
        db.add(new ClothDto(1, "외투", "cloth1.jpg", "항공점퍼"));
        db.add(new ClothDto(2, "외투", "cloth2.jpg", "롱패딩"));
        db.add(new ClothDto(3,  "상의", "cloth3.jpg", "반팔티"));
        db.add(new ClothDto(4, "상의", "cloth4.jpg", "반팔티"));    }

    @Override
    public ArrayList<ClothDto> select() {
        return db;
    }

    @Override
    public ClothDto read(int idx) {
        ClothDto find = db.stream().filter(m -> m.getIdx() == idx).findAny().get();
        return find;
    }

    @Override
    public boolean delete(int idx) {
        db.remove(db.stream().filter(m -> m.getIdx() == idx).findAny().get());
        return true;
    }

    @Override
    public boolean insert(ClothDto cloth) {
        db.add(cloth);
        return true;
    }

    @Override
    public boolean update(ClothDto cloth) {
        ClothDto temp = db.stream().filter(m -> m.getIdx() == cloth.getIdx()).findAny().get();
        temp.setGroup(cloth.getGroup());
        temp.setImage((cloth.getImage()));
        temp.setDetail(cloth.getDetail());
        return true;
    }


}
