package com.njuse.seecjvm.classloader.classfilereader;

import com.njuse.seecjvm.classloader.classfilereader.classpath.*;
import com.njuse.seecjvm.util.PathUtil;
import org.apache.commons.lang3.tuple.Pair;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * This class is the simulated implementation of Java Classloader.
 */
public class ClassFileReader {
    private static ClassFileReader reader = new ClassFileReader();
    private static final String FILE_SEPARATOR = File.separator;
    private static final String PATH_SEPARATOR = File.pathSeparator;

    private ClassFileReader() {
    }

    public static ClassFileReader getInstance() {
        return reader;
    }

    private static Entry bootClasspath = null;//bootstrap class entry
    private static Entry extClasspath = null;//extension class entry
    private static Entry userClasspath = null;//user class entry

    public static void setBootClasspath(String classpath) {
        bootClasspath = chooseEntryType(classpath);
    }

    public static void setExtClasspath(String classpath) {
        extClasspath = chooseEntryType(classpath);
    }

    public static void setUserClasspath(String classpath) {
        userClasspath = chooseEntryType(classpath);
    }

    /**
     * select Entry by type of classpath
     */
    public static Entry chooseEntryType(String classpath) {
        if (classpath.contains(PATH_SEPARATOR)) {
            return new CompositeEntry(classpath);
        }
        if (classpath.endsWith("*")) {
            return new WildEntry(classpath);
        }
        if (classpath.endsWith(".jar") || classpath.endsWith(".JAR")
                || classpath.endsWith(".zip") || classpath.endsWith(".ZIP")) {
            return new ArchivedEntry(classpath);
        }
        return new DirEntry(classpath);
    }

    /**
     * @param className class to be read
     * @param privilege privilege of relevant class
     * @return content of class file and the privilege of loaded class
     */
    public Pair<byte[], Integer> readClassFile(String className, EntryType privilege) throws IOException, ClassNotFoundException {
        String realClassName = className + ".class";
        realClassName = PathUtil.transform(realClassName);
        //todo
        /**
         * Add some codes here.
         *
         * You can pass realClassName to readClass()
         *
         * Read class file in privilege order
         * HIGH has highest privileges and LOW has lowest privileges.
         * If there is no relevant class loaded before, use default privilege.
         * Default privilege is HIGH
         *
         * Return the result once you read it.
         */
        if(realClassName.contains("Object")){
            try {
                byte[] result = bootClasspath.readClass(realClassName);
                return Pair.of(result, new EntryType(EntryType.LOW).getValue());
            }catch (IOException e){
                throw new IOException();
            }
        }

        EntryType p = privilege == null ? new EntryType(EntryType.HIGH) : privilege;

        if(p.getValue() >= EntryType.HIGH){
            try {
                byte[] result = userClasspath.readClass(realClassName);
                return Pair.of(result, p.getValue());
            }catch (IOException e){
                throw new IOException();
            }
        }else if(p.getValue() >= EntryType.MIDDLE){
            try {
                byte[] result = extClasspath.readClass(realClassName);
                return Pair.of(result, p.getValue());
            }catch (IOException e){
                throw new IOException();
            }
        }else if(p.getValue() >= EntryType.LOW){
            try {
                byte[] result = bootClasspath.readClass(realClassName);
                return Pair.of(result, p.getValue());
            }catch (IOException e){
                throw new IOException();
            }
        }

        throw new ClassNotFoundException();
    }
}
