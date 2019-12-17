package com.javarush.task.task39.task3913;

import java.nio.file.Paths;
import java.util.Date;

public class Solution {
    public static void main(String[] args) {
        LogParser logParser = new LogParser(Paths.get("d:/logs/"));
        System.out.println(logParser.getNumberOfUniqueIPs(null, new Date()));
        System.out.println(logParser.getNumberOfUniqueIPs(new Date(), null));
        System.out.println(logParser.getNumberOfUsers(new Date(), null));
        System.out.println(logParser.getDateWhenUserDoneTask("Vasya Pupkin", 15, null, new Date()));
    }
}