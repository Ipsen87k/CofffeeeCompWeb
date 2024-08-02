package com.cofffeeecomp.pro.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.springframework.web.multipart.MultipartFile;

public class Zip extends DecompBase implements Compressable{
    private String ext = ".zip";
    private String outputPath = "";

    @Override
    public void compress(List<MultipartFile> files,Path path){
        this.outputPath = path.toString()+this.ext;
        try(var zos = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(this.outputPath)))){
            for (var file:files){
                var zipEntry = new ZipEntry(file.getOriginalFilename());
                

                zos.putNextEntry(zipEntry);
                try(var bis = new BufferedInputStream(file.getInputStream())){
                    var buffer = file.getBytes();
                    var nRead=0;
                    while ((nRead=bis.read(buffer))!=-1) {
                        zos.write(buffer, 0, nRead);
                    }
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void decompress(List<MultipartFile> fileLists,String output) {
        // TODO Auto-generated method stub
        this.outputPaths = output;
        try{
            for(var file:fileLists){
                decompZipFile(file);
            }
        }catch(IOException e){
            e.printStackTrace();
            
        }
    }

    private void decompZipFile(MultipartFile file) throws IOException{
        try(var zis = new ZipInputStream(new BufferedInputStream(file.getInputStream()))){
            ZipEntry entry = null;
            while ((entry = zis.getNextEntry()) !=null) {
                if(!entry.isDirectory()){
                    var entryName = Path.of(entry.getName());
                    writeDecompFile(zis, entryName.getFileName().toString());
                }
            }
        }catch(IllegalArgumentException e){
            //windowsで作成されたものに対応
            try(var zis = new ZipInputStream(new BufferedInputStream(file.getInputStream()),Charset.forName("MS932"))){
                ZipEntry entry = null;
                while ((entry = zis.getNextEntry()) !=null) {
                    if(!entry.isDirectory()){
                        var entryName = Path.of(entry.getName());
                        writeDecompFile(zis, entryName.getFileName().toString());
                    }
                }
            }
        }
    }

    @Override
    public String toString(){
        return "zip";
    }

    @Override
    public String getOutputPath() {
        return this.outputPath;
    }

}
