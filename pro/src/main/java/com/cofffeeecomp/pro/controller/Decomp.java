package com.cofffeeecomp.pro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.servlet.ModelAndView;

import com.cofffeeecomp.pro.io.Factory;


import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;




@Controller
@RequiredArgsConstructor
@RequestMapping("/decomp")
public class Decomp {


    private final HttpSession session;
    
    @GetMapping
    public ModelAndView index(ModelAndView mv) {
        mv.setViewName("decompForm");
        return mv;
    }

    @GetMapping("/test")
    public String getMethodName() {
        String test1 = "test.tar.xz";
        String ss = "test.zip";
        String ll = "test.gz";
        System.out.println(Factory.CreateDecompInstance(ss));;
        System.out.println(Factory.CreateDecompInstance(ll));;
        System.out.println(Factory.CreateDecompInstance(test1));;

        return "redirect:/decomp";
    }
    
    

}
