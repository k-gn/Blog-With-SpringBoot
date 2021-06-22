package com.cos.blog.test;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TempControllerTest {

    @GetMapping("/temp/home")
    public String tempHome(Model model) {
        model.addAttribute("hello", "Hello Spring");
        return "home";
    }
}
