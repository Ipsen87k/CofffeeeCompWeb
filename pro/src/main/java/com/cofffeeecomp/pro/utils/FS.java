package com.cofffeeecomp.pro.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermissions;

public class FS {
    public static void createDir(String path){
        File parentDir = new File(path).getParentFile();

        if(!parentDir.exists()){
            if(!parentDir.mkdirs()){
                return;
            }
        }
    }

    public static void mkdirAll(Path path){
        try{
            Files.createDirectories(path,PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString("rwxrwxrwx")));
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public static String removeExt(String filePath){
        return filePath.substring(0,filePath.lastIndexOf("."));
    }
}
