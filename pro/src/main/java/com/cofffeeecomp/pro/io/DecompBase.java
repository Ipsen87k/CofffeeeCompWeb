package com.cofffeeecomp.pro.io;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

public abstract class DecompBase implements Decompressable{

    @Getter
    protected List<String> filePaths = new ArrayList<>();
    protected String outputPaths = "";

    private void addFile(String path){
        this.filePaths.add(path);
    }

    protected <T extends InputStream> void writeDecompFile(final T input,String fileName) throws IOException{
        var entryPath = Paths.get(this.outputPaths, fileName);

        addFile(entryPath.toString());

        try(var bos = new BufferedOutputStream(new FileOutputStream(entryPath.toString()))){
            var nRead = 0;
            var buffer = new byte[1024];
            while ((nRead = input.read(buffer))!=-1) {
                bos.write(buffer,0,nRead);
            }
        }
    }
}
