package com.javarush.task.task34.task3413;

import java.lang.ref.SoftReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SoftCache {
    private Map<Long, SoftReference<AnyObject>> cacheMap = new ConcurrentHashMap<>();

    public AnyObject get(Long key) {
        SoftReference<AnyObject> softReference = cacheMap.get(key);

        //напишите тут ваш код
        try {
            return softReference.get();
        } catch (NullPointerException e) {
            return null;
        }
    }

    public AnyObject put(Long key, AnyObject value) {
        AnyObject anyObject = get(key);
        SoftReference<AnyObject> softReference = cacheMap.put(key, new SoftReference<>(value));

        //напишите тут ваш код
        if (softReference != null) {
            softReference.clear();
        }
        return anyObject;
    }

    public AnyObject remove(Long key) {
        AnyObject anyObject = get(key);
        SoftReference<AnyObject> softReference = cacheMap.remove(key);

        //напишите тут ваш код
        if (softReference != null) {
            softReference.clear();
        }
        return anyObject;
    }
}