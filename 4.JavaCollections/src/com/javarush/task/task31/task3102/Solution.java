package com.javarush.task.task31.task3102;

import java.io.File;
import java.io.IOException;
import java.util.*;

/* 
Находим все файлы
*/
public class Solution {
    public static List<String> getFileTree(String root) throws IOException {
        Queue<String> stringQueue = new PriorityQueue<>();
        List<String> fileArray = new ArrayList<>();

        File folder = new File(root);
        if (folder.isDirectory()) {
            stringQueue.add(folder.getAbsolutePath());
            while (!stringQueue.isEmpty()) {
                folder = new File(stringQueue.element());
                for (File file: folder.listFiles()) {
                    if (file.isDirectory()) {
                        stringQueue.add(file.getAbsolutePath());
                    } else {
                        fileArray.add(file.getAbsolutePath());
                    }
                }
                stringQueue.remove();
            }
        }

        return fileArray;
    }

    public static void main(String[] args) {
        
    }
}
