package edu.nju;

import java.io.*;
import java.nio.file.Files;

/**
 * format : dir/subdir/.../
 */
public class DirEntry extends Entry{
    public DirEntry(String classpath) {
        super(classpath);
    }

    @Override
    public byte[] readClassFile(String className) throws IOException {

        className = IOUtil.transform(className);
        File classFile = new File(classpath,className);

        if(!classFile.exists()){
            return null;
        }

        try(InputStream is = Files.newInputStream(classFile.toPath())){

            return IOUtil.readFileByBytes(is);
        }catch (IOException e){
            throw new IOException("read class file error: " + className);
        }

    }
}
