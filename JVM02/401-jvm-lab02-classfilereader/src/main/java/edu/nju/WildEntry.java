package edu.nju;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

/**
 * format : dir/.../*
 */
public class WildEntry extends Entry{
    public WildEntry(String classpath) {
        super(classpath);
    }

    @Override
    public byte[] readClassFile(String className) throws IOException {
        className = IOUtil.transform(className);

        classpath = classpath.replaceFirst("\\*", "");

        File dir = new File(classpath);

        File[] jarFiles = dir.listFiles(file -> file.getName().toLowerCase().endsWith(".jar"));

        if(jarFiles == null){
            return null;
        }

        classpath = classpath.replace(File.separator,"/");
        className = className.replace(File.separator,"/");

        for (File jarFile : jarFiles) {
            String path = classpath + jarFile.getName();
            try(JarFile jar = new JarFile(path)) {
                ZipEntry entry = jar.getEntry(className);
                if(entry==null){
                    continue;
                }
                try(InputStream is = jar.getInputStream(entry)){
                    return IOUtil.readFileByBytes(is);
                }
            }catch (Exception e){
                throw new IOException(e);
            }
        }

        return null;
    }
}
