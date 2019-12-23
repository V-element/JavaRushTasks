package com.javarush.task.task39.task3901;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

/* 
Уникальные подстроки
*/
public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Please enter your string: ");
        String s = bufferedReader.readLine();

        System.out.println("The longest unique substring length is: \n" + lengthOfLongestUniqueSubstring(s));
    }

    public static int lengthOfLongestUniqueSubstring(String s) {
        if (s == null || s.equals("")) {
            return 0;
        } else {
            char[] chars = s.toCharArray();
            int maxLenght = 1, tempLenght = 1;
            char previousChar = chars[0];
            for (char ch: chars) {
                if (previousChar != ch) {
                    tempLenght++;
                } else {
                    maxLenght = Math.max(maxLenght, tempLenght);
                }
            }
            maxLenght = Math.max(maxLenght, tempLenght);
            return maxLenght;
        }
    }
}
