package com.cofffeeecomp.pro.controller;

import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/mode")
public class Mode {

    @GetMapping("/zip")
    public void zipMode(HttpSession session, HttpServletResponse res) {
        session.setAttribute("comp", "zip");
        try {
            res.sendRedirect("/comp");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/7z")
    public void sevenZMode(HttpSession session, HttpServletResponse res) {
        session.setAttribute("comp", "7z");
        try {
            res.sendRedirect("/comp");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/gzip")
    public void gzMode(HttpSession session, HttpServletResponse res) {
        session.setAttribute("comp", "gz");
        try {
            res.sendRedirect("/comp");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/bzip2")
    public void bz2Mode(HttpSession session, HttpServletResponse res) {
        session.setAttribute("comp", "bz2");
        try {
            res.sendRedirect("/comp");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/xz")
    public void xzMode(HttpSession session, HttpServletResponse res) {
        session.setAttribute("comp", "xz");
        try {
            res.sendRedirect("/comp");
        } catch (IOException e) {
            e.printStackTrace();
        }
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
