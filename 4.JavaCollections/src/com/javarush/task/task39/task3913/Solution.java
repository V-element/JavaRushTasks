package com.javarush.task.task39.task3913;

import java.nio.file.Paths;
import java.util.Date;

public class Solution {
    public static void main(String[] args) {
        LogParser logParser = new LogParser(Paths.get("/home/nataly/IdeaProjects/"));
        System.out.println(logParser.getNumberOfUniqueIPs(null, new Date()));
        System.out.println(logParser.getNumberOfUniqueIPs(new Date(), null));
        System.out.println(logParser.getNumberOfUsers(new Date(), null));
        System.out.println(logParser.getNumberOfSuccessfulAttemptToSolveTask(1, null, null));
        System.out.println(logParser.execute("get ip for user = \"Eduard Petrovich Morozko Moroz\""));
    }
}