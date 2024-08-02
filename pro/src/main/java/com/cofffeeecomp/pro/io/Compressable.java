
package com.cofffeeecomp.pro.io;

import java.nio.file.Path;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface Compressable {
    void compress(List<MultipartFile> fileLists,Path path);
    String getOutputPath();
}