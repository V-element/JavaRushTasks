package com.javarush.task.task31.task3113;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/* 
Что внутри папки?
*/
public class Solution {
    public static int countFiles = 0, countFolders = 0;
    public static long size = 0;

    public static void main(String[] args) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String pathString = bufferedReader.readLine();
        bufferedReader.close();

        Path folder = Paths.get(pathString);
        if (!Files.isDirectory(folder)) {
            System.out.println(folder.toString() + " - не папка");
            return;
        } else {
            Files.walkFileTree(folder, new MyFileVisitor());
        }

        System.out.println("Всего папок - " + (countFolders - 1));
        System.out.println("Всего файлов - " + countFiles);
        System.out.println("Общий размер - " + size);

    }
}
