package com.javarush.task.task32.task3210;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;

/* 
Используем RandomAccessFile
*/

public class Solution {
    public static void main(String... args) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(args[0], "rw");

        randomAccessFile.seek(Integer.parseInt(args[1]));
        byte[] b = new byte[args[2].getBytes().length];
        randomAccessFile.read(b, 0, args[2].length());

        randomAccessFile.seek(randomAccessFile.length());
        if (Arrays.toString(args[2].getBytes()).equals(Arrays.toString(b))) {
            randomAccessFile.write("true".getBytes());
        } else {
            randomAccessFile.write("false".getBytes());
        }

        randomAccessFile.close();
    }
}
