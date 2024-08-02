package com.cofffeeecomp.pro.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream;
import org.springframework.web.multipart.MultipartFile;

import com.cofffeeecomp.pro.utils.FS;

public class Bzip2 extends DecompBase implements Compressable{
    private String ext = ".tar.bz2";
    private String outputPath = "";

    @Override
    public void decompress(List<MultipartFile> fileLists,String output) {
        try{
            for(var file:fileLists){
                if(file.getOriginalFilename().contains(".tar.bz2")){
                    decompTarBz2File(file);
                }else if(file.getOriginalFilename().contains(".bz2")){
                    decompBz2File(file);
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void compress(List<MultipartFile> fileLists,Path path) {
        this.outputPath=path.toString()+this.ext;
        try(var taos = new TarArchiveOutputStream(new BZip2CompressorOutputStream(new BufferedOutputStream(new FileOutputStream(this.outputPath))))){
            for (var multiPartFile:fileLists){
                var tempOutput = Paths.get(path.getParent().toString(), multiPartFile.getOriginalFilename());
                multiPartFile.transferTo(tempOutput);
                var file = tempOutput.toFile();
                var entry = new TarArchiveEntry(file,file.getName());
                taos.putArchiveEntry(entry);

                try(var bis = new BufferedInputStream(multiPartFile.getInputStream())){
                    var buffer = new byte[1024];
                    var nRead = 0;
                    while((nRead=bis.read(buffer)) !=-1){
                        taos.write(buffer,0,nRead);
                    }
                }
                taos.closeArchiveEntry();
            }
            taos.finish();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private void decompTarBz2File(MultipartFile file) throws IOException{
        try(var tis = new TarArchiveInputStream(new BZip2CompressorInputStream(new BufferedInputStream(file.getInputStream())))){
            ArchiveEntry entry;
            while ((entry = tis.getNextEntry())!=null) {
                if(!entry.isDirectory()){
                    var entryName = Path.of(entry.getName());
                    writeDecompFile(tis, entryName.toString());
                }
            }
        }
    }

    private void decompBz2File(MultipartFile file) throws IOException{
        try(var bzip2In = new BZip2CompressorInputStream(new BufferedInputStream(file.getInputStream()))){
            writeDecompFile(bzip2In, FS.removeExt(file.getOriginalFilename()));
        }
    }


    @Override
    public String toString(){
        return "Bzip2";
    }

    @Override
    public String getOutputPath() {
        return this.outputPath;
    }
    
}
