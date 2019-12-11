package com.javarush.task.task34.task3408;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.WeakHashMap;

public class Cache<K, V> {
    private Map<K, V> cache = new WeakHashMap<K,V>();   //TODO add your code here

    public V getByKey(K key, Class<V> clazz) throws Exception {
        //TODO add your code here
        if (!cache.containsKey(key)) {
            Constructor<V> constructor = clazz.getConstructor(key.getClass());
            V obj = constructor.newInstance(key);
            cache.put(key, obj);
        }
        return cache.get(key);
    }

    public boolean put(V obj) {
        //TODO add your code here

        try {
            int lastSize = size();
            Class clazz = obj.getClass();
            Method method = clazz.getDeclaredMethod("getKey");
            method.setAccessible(true);
            cache.put((K) method.invoke(obj), obj);

            if (size() > lastSize) {
                return true;
            } else {
                return false;
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            return false;
        }
    }

    public int size() {
        return cache.size();
    }
}
