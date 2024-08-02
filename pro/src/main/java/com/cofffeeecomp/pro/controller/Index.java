package com.cofffeeecomp.pro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class Index {
    @GetMapping("/")
    public ModelAndView index(ModelAndView mv) {
        mv.setViewName("compForm");
        return mv;
    }
    
}
