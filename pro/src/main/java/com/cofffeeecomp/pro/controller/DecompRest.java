package com.cofffeeecomp.pro.controller;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cofffeeecomp.pro.io.DecompBase;
import com.cofffeeecomp.pro.io.Factory;
import com.cofffeeecomp.pro.utils.FS;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/decomp")
public class DecompRest {
    @PostMapping("/upload")
    public List<String> upload(@RequestParam("fileContents") List<MultipartFile> files,HttpSession session) {

        DecompBase decompresser = Factory.CreateDecompInstance(files.get(0).getOriginalFilename());
        if (decompresser == null){
            return null;
        }
        
        var output = Paths.get("tmp",session.getId(),"input");
        FS.mkdirAll(output);

        decompresser.decompress(files, output.toString());
        session.setAttribute("decompFiles", decompresser.getFilePaths());

        return decompresser.getFilePaths().stream().map(x->Path.of(x).getFileName().toString()).toList();
    }
}
