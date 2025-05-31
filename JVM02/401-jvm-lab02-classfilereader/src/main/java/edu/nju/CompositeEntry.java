package edu.nju;

import java.io.File;
import java.io.IOException;

/**
 * format : dir/subdir;dir/subdir/*;dir/target.jar*
 */
public class CompositeEntry extends Entry{
    public CompositeEntry(String classpath) {super(classpath);}

    @Override
    public byte[] readClassFile(String className) throws IOException, ClassNotFoundException {

        String[] paths = classpath.split(File.pathSeparator);
        for (String path : paths){
            Entry entry = ClassFileReader.chooseEntryType(path);

            if(entry == null){
                continue;
            }

            byte[] bytes = entry.readClassFile(className);

            if(bytes != null){
                return bytes;
            }
        }


        return null;

    }
}
