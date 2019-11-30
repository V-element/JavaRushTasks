package com.javarush.task.task33.task3310;

import com.javarush.task.task33.task3310.strategy.HashMapStorageStrategy;
import com.javarush.task.task33.task3310.strategy.StorageStrategy;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Solution {
    public static void main(String[] args) {
        testStrategy(new HashMapStorageStrategy(), 10000);
    }

    public static Set<Long> getIds(Shortener shortener, Set<String> strings) {
        HashSet<Long> hashSet = new HashSet<>();
        for (String s: strings) {
            hashSet.add(shortener.getId(s));
        }
        return hashSet;
    }

    public static Set<String> getStrings(Shortener shortener, Set<Long> keys) {
        HashSet<String> hashSet = new HashSet<>();
        for (Long l: keys) {
            hashSet.add(shortener.getString(l));
        }
        return hashSet;
    }

    public static void testStrategy(StorageStrategy strategy, long elementsNumber) {
        Helper.printMessage(strategy.getClass().getSimpleName());
        Set<String> strings = new HashSet<>();
        Long[] elements = new Long[(int) elementsNumber];

        for (int i = 0; i < elementsNumber; i++) {
            strings.add(Helper.generateRandomString());
        }

        Shortener shortener = new Shortener(strategy);
        Date date1 = new Date();
        Set<Long> ids = getIds(shortener, strings);
        Date date2 = new Date();
        Helper.printMessage(Long.toString(date2.getTime() - date1.getTime()));

        Date date3 = new Date();
        Set<String> strings1 = getStrings(shortener, ids);
        Date date4 = new Date();
        Helper.printMessage(Long.toString(date4.getTime() - date3.getTime()));

        if (strings1.equals(strings)) {
            Helper.printMessage("Тест пройден.");
        } else {
            Helper.printMessage("Тест не пройден.");
        }
    }
}
