package com.javarush.task.task36.task3605;

import javax.imageio.metadata.IIOMetadataFormatImpl;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.Scanner;
import java.util.TreeSet;

/* 
Использование TreeSet
*/
public class Solution {
    public static void main(String[] args) throws IOException {
        //String fileName = args[0];
        Scanner sc = new Scanner(new File(args[0]));
        TreeSet treeSet = new TreeSet();
        while (sc.hasNext()) {
            String s = sc.nextLine().replaceAll("\\W", "").toLowerCase();//
            for (Character c : s.toCharArray()) {
                treeSet.add(c);
            }
        }

        Iterator value = treeSet.iterator();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 5;) {
            if (value.hasNext()) {
                stringBuilder.append(value.next());
                i++;
            } else {
                break;
            }

        }
        System.out.println(stringBuilder.toString());
    }
}
