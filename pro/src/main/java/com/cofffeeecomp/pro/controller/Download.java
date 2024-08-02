package com.cofffeeecomp.pro.controller;

import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Optional;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class Download {

    @GetMapping("/comp/download")
    public ResponseEntity<Resource> download(HttpSession session) {
        try{
            var someFilePath = Optional.ofNullable(session.getAttribute("output"));
            if (someFilePath.isPresent()) {
                var filePath = (String) someFilePath.get();
                var file = new File(filePath);

                if (!file.exists()) {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }

                var res = new FileSystemResource(file);
                var headers = new HttpHeaders();
                headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());

                return ResponseEntity.ok()
                        .headers(headers)
                        .contentLength(file.length())
                        .body(res);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch(IllegalStateException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/decomp/download")
    public ResponseEntity<Resource> decompDownload(@RequestParam("file") String fileName,HttpSession session,HttpServletResponse res) {
        var fileListsObj = session.getAttribute("decompFiles");
        if (fileListsObj == null){
            System.out.println("null");
        }

        var fileList = (List<String>)fileListsObj;
        try{
            for(var file:fileList){
                var io_file = new File(file);
                if (fileName.equals(io_file.getName())){
                    if (!io_file.exists()){
                        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                    }
                    System.out.println(file);

                    var resource = new FileSystemResource(io_file);
                    ContentDisposition cd = ContentDisposition.builder("attachment")
                        .filename(URLEncoder.encode(io_file.getName(), "UTF-8"))
                        .build();

                    return ResponseEntity.ok()
                            .header(HttpHeaders.CONTENT_DISPOSITION, cd.toString())
                            .body(resource);
                }

            }
        }catch(IllegalStateException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    

}
