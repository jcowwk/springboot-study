package com.example.study;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FirstController {

    @GetMapping("/hello")
    public String Hello(Model model){
        model.addAttribute("username", "jcowwk");
        return "hello";
    }

    @GetMapping("/bye")
    public String Bye(Model model){
        model.addAttribute("username","jcowwk");
        return "bye";
    }
}