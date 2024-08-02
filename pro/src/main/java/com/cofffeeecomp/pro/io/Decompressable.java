package com.cofffeeecomp.pro.io;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface Decompressable {
    void decompress(List<MultipartFile> fileLists,String output);
}
