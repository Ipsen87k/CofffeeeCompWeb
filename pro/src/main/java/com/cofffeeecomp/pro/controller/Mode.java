package com.cofffeeecomp.pro.controller;

import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/mode")
public class Mode {

    @GetMapping("/zip")
    public ResponseEntity<Object> zipMode(HttpSession session, HttpServletResponse res) {
        session.setAttribute("comp", "zip");
        return ResponseEntity.ok().build();
    }

    @GetMapping("/7z")
    public ResponseEntity<Object> sevenZMode(HttpSession session, HttpServletResponse res) {
        session.setAttribute("comp", "7z");
        return ResponseEntity.ok().build();
    }

    @GetMapping("/gzip")
    public ResponseEntity<Object> gzMode(HttpSession session, HttpServletResponse res) {
        session.setAttribute("comp", "gz");
        return ResponseEntity.ok().build();
    }

    @GetMapping("/bzip2")
    public ResponseEntity<Object> bz2Mode(HttpSession session, HttpServletResponse res) {
        session.setAttribute("comp", "bz2");
        return ResponseEntity.ok().build();
    }

    @GetMapping("/xz")
    public ResponseEntity<Object> xzMode(HttpSession session, HttpServletResponse res) {
        session.setAttribute("comp", "xz");
        return ResponseEntity.ok().build();

    }

    @GetMapping("/changeDecomp")
    public void decompMode(HttpSession session, HttpServletResponse res) {
        session.setAttribute("compMode", "decomp");
        try {
            res.sendRedirect("/decomp");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/changeComp")
    public void compMode(HttpSession session, HttpServletResponse res) {
        session.setAttribute("compMode", "comp");
        try {
            res.sendRedirect("/comp");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
