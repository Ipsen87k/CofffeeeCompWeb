package com.cofffeeecomp.pro.io;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import org.apache.commons.compress.archivers.sevenz.SevenZOutputFile;
import org.springframework.web.multipart.MultipartFile;

public class SevenZip implements Compressable,Decompressable{
    private String ext = ".7z";
    private String outputPath = "";

    @Override
    public void decompress(List<MultipartFile> fileLists,String output) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'decompress'");
    }

    @Override
    public void compress(List<MultipartFile> fileLists,Path path) {
        this.outputPath = path.toString() + this.ext;
        try(var sevenZip = new SevenZOutputFile(new File(this.outputPath))){
            for (var file:fileLists){
                try(var bis = new BufferedInputStream(file.getInputStream())){
                    var entry = sevenZip.createArchiveEntry(new File(this.outputPath), "entry");
                    sevenZip.putArchiveEntry(entry);

                    var buffer = new byte[1024];
                    var nRead=0;
                    while ((nRead=bis.read(buffer))!=-1) {
                        sevenZip.write(buffer,0,nRead);
                    }
                    sevenZip.closeArchiveEntry();
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public String toString(){
        return "7z";
    }

    @Override
    public String getOutputPath() {
        return this.outputPath;
    }
    
}
