package com.javarush.task.task32.task3201;

import java.io.RandomAccessFile;

/*
Запись в существующий файл
*/
public class Solution {
    public static void main(String... args) throws Exception {
        RandomAccessFile randomAccessFile = new RandomAccessFile(args[0], "rw");

        randomAccessFile.seek(Math.min(Integer.parseInt(args[1]),randomAccessFile.length()));

        randomAccessFile.write(args[2].getBytes());

        randomAccessFile.close();
    }
}
