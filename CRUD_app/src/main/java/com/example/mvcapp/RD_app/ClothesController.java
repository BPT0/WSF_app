package com.example.miniProject.demo2;

import com.example.miniProject.demo2.ClothesDto;
import com.example.miniProject.spring.Autowired;
import com.example.miniProject.spring.Controller;
import com.example.miniProject.spring.RequestMapping;
import com.example.miniProject.spring.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

@Controller
public class ClothesController {
    @Autowired
    private ClothesService service;

    public ClothesController() {
        System.out.println("ClothesController 객체 생성");
    }

    @RequestMapping("/count")
    @ResponseBody
    public String count(Map<String, String> param, Map<String, Object> model) {
        return  "count=" + service.count();
    }

    @RequestMapping("/list")
    public String list(Map<String, String> param, Map<String, Object> model) {
        List<ClothesDto> clothes = service.select();
        for(int i = 0; i < clothes.size(); i++) {
            model.put("idx" + i, clothes.get(i).getIdx());
            model.put("title" + i, clothes.get(i).getTitle());
            model.put("image" + i, clothes.get(i).getImage());
        }
        return  "list";
    }

    //Read 수정
    @RequestMapping("/read")
    public String read( @PathVariable Map<String, String> param, Map<String, Object> model) {
        int idx = Integer.parseInt(param.get("idx"));

        ClothesDto clothes = service.read(idx);

        model.put("idx", clothes.getIdx());
        model.put("title", clothes.getTitle());
        model.put("image", clothes.getImage());
        model.put("content", clothes.getContent());
        return  "read";
    }

    //Delete 추가
    @RequestMapping("/delete/{idx}") //반드시 idx가 존재해야함(필수 파라미터)
    public String delete(@PathVariable Map<String, String> param) { //@PathVariable로 값을 읽는다
        int idx = Integer.parseInt(param.get("idx"));
        service.delete(idx);
        return  "redirect:/list";//redirect 기능 추가: 이 서비스가 종료되고 나서 다시 브라우저가 요청해야할 url 주소를 넘겨준다 (삭제후 /list로 이동)
    }

}


