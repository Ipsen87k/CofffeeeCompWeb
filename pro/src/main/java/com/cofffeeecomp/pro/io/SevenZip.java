package com.cofffeeecomp.pro.io;


import java.nio.file.Path;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class SevenZip implements Compressable,Decompressable{
    private String ext = ".7z";

    @Override
    public void decompress(List<MultipartFile> fileLists,String output) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'decompress'");
    }

    @Override
    public void compress(List<MultipartFile> fileLists,Path path) {
        //outputName = outputName + this.ext;
        // try(var sevenZip = new SevenZOutputFile(new File(outputName))){
        //     for (var file:fileLists){
        //         try(var bis = new BufferedInputStream(file.getInputStream())){
        //         }
        //     }
        // }catch(IOException e){
        //     e.printStackTrace();
        // }
    }

    @Override
    public String toString(){
        return "7z";
    }

    @Override
    public String getOutputPath() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getOutputPath'");
    }
    
}
