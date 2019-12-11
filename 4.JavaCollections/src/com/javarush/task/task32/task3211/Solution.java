package com.javarush.task.task32.task3211;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.security.MessageDigest;

/* 
Целостность информации
*/

public class Solution {
    public static void main(String... args) throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(new String("test string"));
        oos.flush();
        System.out.println(compareMD5(bos, "5a47d12a2e3f9fecf2d9ba1fd98152eb")); //true

    }

    public static boolean compareMD5(ByteArrayOutputStream byteArrayOutputStream, String md5) throws Exception {

        MessageDigest m1 = MessageDigest.getInstance("MD5");
        //m1.update(byteArrayOutputStream.toByteArray());
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b: m1.digest(byteArrayOutputStream.toByteArray())) {
            stringBuilder.append(String.format("%02x", b));
        }
        if (stringBuilder.length() < 32) {
            stringBuilder.append("0");
        }


        return stringBuilder.toString().equals(md5);
    }
}
