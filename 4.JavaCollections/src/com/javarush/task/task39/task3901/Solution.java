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
            int maxLenght = 1;
            for (int i = 0; i < chars.length; i++) {
                int tempLenght = 0;
                HashSet<Character> charList = new HashSet<>();
                for (int j = i; j < chars.length; j++) {
                    if (!charList.contains(chars[j])) {
                        tempLenght++;
                        charList.add(chars[j]);
                    } else {
                        maxLenght = Math.max(maxLenght, tempLenght);
                        break;
                    }
                }
                maxLenght = Math.max(maxLenght, tempLenght);
            }

            return maxLenght;
        }
    }
}
