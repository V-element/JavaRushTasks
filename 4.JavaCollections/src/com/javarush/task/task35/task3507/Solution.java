package com.javarush.task.task35.task3507;

import java.util.Set;

/* 
ClassLoader - что это такое?
*/
public class Solution {
    public static void main(String[] args) {
        String s = "";
        int f = 1, d = 0;
        Set<? extends Animal> allAnimals = getAllAnimals(Solution.class.getProtectionDomain().getCodeSource().getLocation().getPath() + Solution.class.getPackage().getName().replaceAll("[.]", "/") + "/data");
        System.out.println(allAnimals);
    }

    private static Set<? extends Animal> getAllAnimals(String pathToAnimals) {
        return null;
    }
}
