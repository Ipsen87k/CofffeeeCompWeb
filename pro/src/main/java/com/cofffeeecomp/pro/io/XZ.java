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
import org.apache.commons.compress.compressors.xz.XZCompressorInputStream;
import org.apache.commons.compress.compressors.xz.XZCompressorOutputStream;
import org.springframework.web.multipart.MultipartFile;

import com.cofffeeecomp.pro.utils.FS;

public class XZ extends DecompBase implements Compressable{
    private String ext = ".tar.xz";
    private String outputPath = "";

    @Override
    public void decompress(List<MultipartFile> fileLists,String output) {
        try{
            for(var file:fileLists){
                if(file.getOriginalFilename().contains(".tar.xz")){
                    decompTarXZFile(file);
                }else if(file.getOriginalFilename().contains(".xz")){
                    decompXZFile(file);
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private void decompTarXZFile(MultipartFile file) throws IOException{
        try(var tis = new TarArchiveInputStream(new XZCompressorInputStream(new BufferedInputStream(file.getInputStream())))){
            ArchiveEntry entry;
            while ((entry=tis.getNextEntry())!=null) {
                if(!entry.isDirectory()){
                    var entryName = Path.of(entry.getName());
                    writeDecompFile(tis, entryName.toString());
                }
            }
        }
    }

    private void decompXZFile(MultipartFile file) throws IOException{
        try(var XZIn = new XZCompressorInputStream(new BufferedInputStream(file.getInputStream()))){
            writeDecompFile(XZIn, FS.removeExt(file.getOriginalFilename()));
        }
    }

    @Override
    public void compress(List<MultipartFile> fileLists, Path path) {
        this.outputPath=path.toString()+this.ext;
        try(var taos = new TarArchiveOutputStream(new XZCompressorOutputStream(new BufferedOutputStream(new FileOutputStream(this.outputPath))))){
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

        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    @Override
    public String toString(){
        return "xz";
    }

    @Override
    public String getOutputPath() {
        return this.outputPath;
    }
}
