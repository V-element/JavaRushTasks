package com.javarush.task.task31.task3105;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/* 
Добавление файла в архив
*/
public class Solution {
    public static void main(String[] args) throws IOException {

        /*File file = new File(args[0]);*/
        //ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(args[1]));

        //File file = new File("C:\\Users\\Egor\\Desktop\\java\\1.txt");
        //ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream("C:\\Users\\Egor\\Desktop\\java\\test.zip"));

        Path fileName = Paths.get(args[0]);
        Path zipFile = Paths.get(args[1]);
        Map<Path, ByteArrayOutputStream> archiveFiles = new HashMap<>();
        try (ZipInputStream zipInputStream = new ZipInputStream(Files.newInputStream(zipFile))) {
            ZipEntry zipEntry = zipInputStream.getNextEntry();

            while (zipEntry != null) {
                archiveFiles.put(Paths.get(zipEntry.getName()), new ByteArrayOutputStream());
                copyData(zipInputStream, archiveFiles.get(Paths.get(zipEntry.getName())));
                zipInputStream.closeEntry();
                zipEntry = zipInputStream.getNextEntry();
            }
        }
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(Files.newOutputStream(zipFile))) {

            //if (Files.isRegularFile(fileName)) {
                if (archiveFiles.containsKey(Paths.get("new").resolve(fileName.getFileName()))) {
                    archiveFiles.get(Paths.get("new").resolve(fileName.getFileName())).close();
                    archiveFiles.remove(Paths.get("new").resolve(fileName.getFileName()));
                }
                Path archivePath = Paths.get("new").resolve(fileName.getFileName());
                zipOutputStream.putNextEntry(new ZipEntry(archivePath.toString()));
                Files.copy(fileName, zipOutputStream);
                zipOutputStream.closeEntry();
            /*} else
                throw new IOException();*/
            for (Map.Entry file : archiveFiles.entrySet()) {
                zipOutputStream.putNextEntry(new ZipEntry(file.getKey().toString()));
                ((ByteArrayOutputStream) file.getValue()).writeTo(zipOutputStream);
                ((ByteArrayOutputStream) file.getValue()).close();
                zipOutputStream.closeEntry();
            }
        }
    }

    private static void copyData(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[8 * 1024];
        int len;
        while ((len = in.read(buffer)) > 0) {
            out.write(buffer, 0, len);
        }
    }


}
