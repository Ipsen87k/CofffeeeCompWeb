package com.cofffeeecomp.pro.io;

import org.apache.commons.compress.utils.FileNameUtils;

public class Factory {
    public static DecompBase CreateDecompInstance(String filePath){
        String ext = FileNameUtils.getExtension(filePath);
        
        if (ext.equals("zip")){
            return new Zip();
        }else if (ext.equals("tar.bz2") || ext.equals("bz2")){
            return new Bzip2();
        }else if (ext.equals("tar.gz") || ext.equals("gz")){
            return new Gzip();
        }else if (ext.equals("tar.xz") || ext.equals("xz")){
            return new XZ();
        }else{
            return null;
        }
    }

    public static Compressable CreateCompressInstance(String mode){
        if (mode.equals("zip")){
            return new Zip();
        }else if (mode.equals("gz")){
            return new Gzip();
        }else if (mode.equals("bz2")){
            return new Bzip2();
        }else if(mode.equals("xz")){
            return new XZ();
        }else{
            return new Zip();
        }
    }
}
