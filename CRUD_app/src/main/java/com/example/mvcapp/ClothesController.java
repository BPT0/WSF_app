package com.example.mvcapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ClothesController {

    @Autowired
    private ClothService clothService;
    @RequestMapping("/list")
    public  String list(Model model) {
        model.addAttribute("clothes",
                clothService.select());
        return "list.html";
    }

    @RequestMapping("/read/{idx}")
    public  String read(@PathVariable int idx, Model model) {
        model.addAttribute("clothes",
                clothService.read(idx));
        return "read.html";
    }

    @RequestMapping("/delete/{idx}")
    public  String delete(@PathVariable int idx) {
        clothService.delete(idx);
        return "redirect:/list";
    }

    @RequestMapping("/insertForm")
    public  String insertForm() {
        return "insertForm";
    }

    @RequestMapping("/insert")
    public  String insert(ClothDto clothes) {
        clothService.insert(clothes);
        return "redirect:/list";
    }

    @RequestMapping("/updateForm/{idx}")
    public  String updateForm(@PathVariable int idx, Model model) {
        model.addAttribute("movie", clothService.read(idx));
        return "updateForm";
    }

    @RequestMapping("/update")
    public  String update(ClothDto movie) {
        clothService.update(movie);
        return "redirect:/read/" + movie.getIdx();
    }


}
