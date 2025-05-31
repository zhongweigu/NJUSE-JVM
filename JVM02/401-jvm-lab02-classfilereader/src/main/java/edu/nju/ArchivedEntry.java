package edu.nju;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * format : dir/subdir/target.jar
 */
public class ArchivedEntry extends Entry{
    public ArchivedEntry(String classpath) {
        super(classpath);
    }

    @Override
    public byte[] readClassFile(String className) throws IOException {

        className = className.replace(File.separator, "/");

        try(ZipFile zipFile = new ZipFile(classpath)){

            ZipEntry entry = zipFile.getEntry(className);

            if(entry == null){
                return null;
            }

            InputStream is = zipFile.getInputStream(entry);

            return IOUtil.readFileByBytes(is);
        }catch (IOException e){
            throw new IOException("read class file error",e);
        }

    }
}
