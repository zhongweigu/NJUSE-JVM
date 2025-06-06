package edu.nju;

import java.io.File;
import java.io.IOException;

public class ClassFileReader {
    private static final String FILE_SEPARATOR = File.separator;
    private static final String PATH_SEPARATOR = File.pathSeparator;

    private static Entry bootStrapReader;

    public static Entry chooseEntryType(String classpath) {
        /**
         * tips:
         *      Every case can correspond to a class
         *      These cases are disordered
         *      You should take care of the order of if-else
         * case 1 classpath with wildcard
         * case 2 normal dir path
         * case 3 archived file
         * case 4 classpath with path separator
         */

        File file = new File(classpath);

        if(classpath.contains(PATH_SEPARATOR)){
            return new CompositeEntry(classpath);
        }

        if(file.isDirectory()){
            return new DirEntry(classpath);
        }else if(classpath.endsWith(".jar") ||classpath.endsWith(".JAR")
                ||classpath.endsWith(".zip") ||classpath.endsWith(".ZIP")){
            return new ArchivedEntry(classpath);
        }else if(classpath.endsWith("*")){
            return new WildEntry(classpath);
        }

        return null;
    }

    /**
     *
     * @param classpath where to find target class
     * @param className format: /package/.../class
     * @return content of classfile
     */
    public static byte[] readClassFile(String classpath,String className) throws ClassNotFoundException{
        if(className ==null){
            throw new ClassNotFoundException();
        }
        className = IOUtil.transform(className);
        className += ".class";
        bootStrapReader = chooseEntryType(classpath);
        byte[] ret = new byte[0];
        try {
            ret = bootStrapReader.readClassFile(className);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (ret==null)throw new ClassNotFoundException();
        return ret;
    }
}
