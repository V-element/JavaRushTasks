package com.javarush.task.task32.task3204;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/* 
Генератор паролей
*/
public class Solution {
    public static void main(String[] args) throws Exception {
        ByteArrayOutputStream password = getPassword();
        System.out.println(password.toString());
    }

    public static ByteArrayOutputStream getPassword() throws Exception {

        InputStream inputStream = new ByteArrayInputStream(generatePassword().getBytes());
        BufferedInputStream bis = new BufferedInputStream(inputStream);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        while (bis.available() > 0)
        {
            int data = bis.read();
            byteArrayOutputStream.write(data);
        }

        return byteArrayOutputStream;
    }

    public static String generatePassword() {
        List<String> charCategories = new ArrayList<>(3);
        charCategories.add("abcdefghijklmnopqrstuvwxyz");
        charCategories.add("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        charCategories.add("0123456789");
        List<Character> charPass = new ArrayList<>(8);

        StringBuilder password = new StringBuilder(8);
        Random random = new Random(System.nanoTime());

        for (int i = 0; i < 3; i++) {
            String charCategory = charCategories.get(0);
            int position = random.nextInt(charCategory.length());
            charPass.add(charCategory.charAt(position));
        }

        for (int i = 0; i < 3; i++) {
            String charCategory = charCategories.get(1);
            int position = random.nextInt(charCategory.length());
            charPass.add(charCategory.charAt(position));
        }

        for (int i = 0; i < 2; i++) {
            String charCategory = charCategories.get(2);
            int position = random.nextInt(charCategory.length());
            charPass.add(charCategory.charAt(position));
        }

        Collections.shuffle(charPass);
        for (Character c: charPass) {
            password.append(c);
        }

        return new String(password);

    }
}