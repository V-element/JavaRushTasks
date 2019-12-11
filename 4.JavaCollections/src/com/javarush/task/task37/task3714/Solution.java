package com.javarush.task.task37.task3714;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/* 
Древний Рим
*/
public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Input a roman number to be converted to decimal: ");
        String romanString = bufferedReader.readLine();
        System.out.println("Conversion result equals " + romanToInteger(romanString));
    }

    public static int romanToInteger(String s) {
        int sum = 0;
        char[] chars = s.toUpperCase().toCharArray();
        int[] ints = new int[chars.length];
        for (int i = 0; i < chars.length; i++) {

            switch (chars[i]) {
                case ('I'):
                    ints[i] = 1;
                    break;
                case ('V'):
                    ints[i] = 5;
                    break;
                case ('X'):
                    ints[i] = 10;
                    break;
                case ('L'):
                    ints[i] = 50;
                    break;
                case ('C'):
                    ints[i] = 100;
                    break;
                case ('D'):
                    ints[i] = 500;
                    break;
                case ('M'):
                    ints[i] = 1000;
                    break;
            }
        }

        for (int i = 0; i < ints.length; i++) {
            if (i + 1 < ints.length && ints[i] < ints[i + 1]) {
                sum -= ints[i];
            } else {
                sum += ints[i];
            }

        }

        return sum;

    }
}
