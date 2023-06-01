package com.example.miniProject.demo2;

import com.example.miniProject.demo2.ClothesDto;
import com.example.miniProject.spring.Service;

import java.util.ArrayList;


@Service
public class ClothesServiceImpl implements ClothesService{

    private ArrayList<ClothesDto> db = new ArrayList<>();

    public ClothesServiceImpl() {
        System.out.println("ClothesServiceImpl 객체 생성");
        db.add(new ClothesDto(5, "옷5", "cloth5.jpg", "옷 정보 \n5"));
        db.add(new ClothesDto(6, "옷6", "cloth6.jpg", "옷 정보 \n6"));
        db.add(new ClothesDto(7,  "옷7", "cloth7.jpg", "옷 정보 \n7"));
        db.add(new ClothesDto(8, "옷8", "cloth8.jpg", "옷 정보 \n8"));
    }

    @Override
    public ArrayList<ClothesDto>  select() {
        return db;
    };

    public ClothesDto  read(int idx) {
        ClothesDto ret = db.stream().filter(m -> m.getIdx() == idx).findAny().get() ;
        return ret;
    }

    //Delete 추가
    @Override
    public boolean delete(int idx) {//delete 추가됨
        db.remove(db.stream().filter(m -> m.getIdx() == idx).findAny().get()); //idx에 해당하는 dto 찾고 삭제
        return true; //별도의 예외 처리x
    }

    @Override
    public int count() {
        return db.size();
    }

}
