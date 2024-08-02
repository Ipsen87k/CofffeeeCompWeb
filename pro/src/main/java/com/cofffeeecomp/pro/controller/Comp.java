package com.cofffeeecomp.pro.controller;

import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.cofffeeecomp.pro.io.Compressable;
import com.cofffeeecomp.pro.io.Factory;
import com.cofffeeecomp.pro.utils.Date;
import com.cofffeeecomp.pro.utils.FS;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;


@RequiredArgsConstructor
@Controller
@RequestMapping("/comp")
public class Comp {
    private final HttpSession session;

    @GetMapping
    public ModelAndView LoginSow(ModelAndView mv) {
        mv.setViewName("compForm");
        return mv;
    }

    @PostMapping("/upload")
    public String uploadAttachedFiles(@RequestParam("fileContents") List<MultipartFile> files) {

        var someExtConfig= Optional.ofNullable(session.getAttribute("comp"));
        var extConfig = (String)someExtConfig.orElse("zip");

        var output = Paths.get("tmp",session.getId(),Date.getDateString());
        FS.createDir(output.toString());

        Compressable compresser = Factory.CreateCompressInstance(extConfig);

        compresser.compress(files, output);

        session.setAttribute("output", compresser.getOutputPath());
        System.out.println(session.getAttribute("output"));
        
        return "redirect:/comp";
    }

    
    
}
