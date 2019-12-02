package com.javarush.task.task33.task3310.tests;

import com.javarush.task.task33.task3310.Helper;
import com.javarush.task.task33.task3310.Shortener;
import com.javarush.task.task33.task3310.strategy.HashBiMapStorageStrategy;
import com.javarush.task.task33.task3310.strategy.HashMapStorageStrategy;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class SpeedTest {

    public long getTimeToGetIds(Shortener shortener, Set<String> strings, Set<Long> ids) {
        Date date = new Date();
        for (String s: strings) {
            ids.add(shortener.getId(s));
        }
        Date date1 = new Date();
        return date1.getTime() - date.getTime();
    }

    public long getTimeToGetStrings(Shortener shortener, Set<Long> ids, Set<String> strings) {
        Date date = new Date();
        for (Long l: ids) {
            strings.add(shortener.getString(l));
        }
        Date date1 = new Date();
        return date1.getTime() - date.getTime();
    }

    @Test
    public void testHashMapStorage() {
        HashMapStorageStrategy hashMapStorageStrategy = new HashMapStorageStrategy();
        Shortener shortener1 = new Shortener(hashMapStorageStrategy);
        HashBiMapStorageStrategy hashBiMapStorageStrategy = new HashBiMapStorageStrategy();
        Shortener shortener2 = new Shortener(hashBiMapStorageStrategy);
        HashSet<String> origStrings = new HashSet<>();
        for (int i = 0; i < 10000; i++) {
            origStrings.add(Helper.generateRandomString());
        }
        HashSet<Long> ids1 = new HashSet<>();
        HashSet<Long> ids2 = new HashSet<>();
        Long time1 = getTimeToGetIds(shortener1,origStrings, ids1);
        Long time2 = getTimeToGetIds(shortener2,origStrings, ids2);
        Assert.assertTrue(time1 > time2);

        Long time3 = getTimeToGetStrings(shortener1, ids1, new HashSet<>());
        Long time4 = getTimeToGetStrings(shortener2, ids2, new HashSet<>());
        Assert.assertEquals(time3, time4, 30);
    }
}
