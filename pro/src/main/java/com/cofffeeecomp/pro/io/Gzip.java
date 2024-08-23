package com.cofffeeecomp.pro.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;
import org.springframework.web.multipart.MultipartFile;

import com.cofffeeecomp.pro.utils.FS;

public class Gzip extends DecompBase implements Compressable{
    private String ext = ".tar.gz";
    private String outputPath = "";

    @Override
    public void compress(List<MultipartFile> fileLists, Path path) {
        if (fileLists.size()==1){
            this.outputPath = path.toString() + ".gz";
            try(var gcos = new GzipCompressorOutputStream(new BufferedOutputStream(new FileOutputStream(this.outputPath)))){
                try(var bis = new BufferedInputStream(fileLists.get(0).getInputStream())){
                        var buffer = new byte[1024];
                        var nRead=0;
                        while((nRead=bis.read(buffer))!=-1){
                            gcos.write(buffer,0,nRead);
                        }
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }else{
            this.outputPath = path.toString() + this.ext;
            try(var taos = new TarArchiveOutputStream(new GzipCompressorOutputStream(new BufferedOutputStream(new FileOutputStream(this.outputPath))))){
                for(var multiPartFile:fileLists){
    
                    var entry = new TarArchiveEntry(multiPartFile.getOriginalFilename());
                    entry.setSize(multiPartFile.getSize());
                    taos.putArchiveEntry(entry);
    
                    try(var bis = new BufferedInputStream(multiPartFile.getInputStream())){
                        var buffer = new byte[1024];
                        var nRead=0;
                        while((nRead=bis.read(buffer))!=-1){
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

    }

    @Override
    public void decompress(List<MultipartFile> fileLists,String output) {
        try{
            for(var file:fileLists){
                if(file.getOriginalFilename().contains(".tar.gz")){
                    decompTarGzipFile(file);
                }else if(file.getOriginalFilename().contains(".gz")){
                    decompGzipFile(file);
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private void decompTarGzipFile(MultipartFile file) throws IOException{
        try(var tis = new TarArchiveInputStream(new GzipCompressorInputStream(new BufferedInputStream(file.getInputStream())))){
            ArchiveEntry entry;
            while ((entry=tis.getNextEntry())!=null) {
                if(!entry.isDirectory()){
                    var entryName = Path.of(entry.getName());
                    writeDecompFile(tis, entryName.toString());
                }
            }
        }
    }

    private void decompGzipFile(MultipartFile file) throws IOException{
        try(var gzipIn = new GzipCompressorInputStream(new BufferedInputStream(file.getInputStream()))){
            writeDecompFile(gzipIn, FS.removeExt(file.getOriginalFilename()));
        }
    }
    
    @Override
    public String toString(){
        return "Gzip";
    }

    @Override
    public String getOutputPath() {
        return this.outputPath;
    }

}
