package com.javarush.task.task36.task3610;

import java.io.Serializable;
import java.sql.ClientInfoStatus;
import java.util.*;

public class MyMultiMap<K, V> extends HashMap<K, V> implements Cloneable, Serializable {
    static final long serialVersionUID = 123456789L;
    private HashMap<K, List<V>> map;
    private int repeatCount;

    public MyMultiMap(int repeatCount) {
        this.repeatCount = repeatCount;
        map = new HashMap<>();
    }

    @Override
    public int size() {
        //напишите тут ваш код
        int size = 0;
        for (List<V> list : map.values()) {
            size += list.size();
        }
        return size;
    }

    @Override
    public V put(K key, V value) {
        if (!map.containsKey(key)) {
            List<V> newList = new ArrayList<V>();
            newList.add(value);
            map.put(key, newList);
            return null;
        } else {
            List<V> list = map.get(key);
            if (repeatCount == list.size()) {
                list.remove(0);
            }
            list.add(value);
            map.put(key, list);
            if (list.size() > 1) {
                return list.get(list.size() - 2);
            } else {
                return null;
            }
        }

    }

    @Override
    public V remove(Object key) {
        //напишите тут ваш код
        Object vObject = null;

        if (map.containsKey(key)) {
            List<V> list = map.get(key);
            if (list.size() > 0) {
                vObject = list.get(0);
                list.remove(0);
                if (list.size() == 0) {
                    map.remove(key);
                }
            }
            return (V) vObject;
        } else {
            return null;
        }
    }

    @Override
    public Set<K> keySet() {
        //напишите тут ваш код
        return map.keySet();
    }

    @Override
    public Collection<V> values() {
        //напишите тут ваш код
        List<V> tempList = new ArrayList<V>();
        for (List<V> list : map.values()) {
            tempList.addAll(list);
        }
        return tempList;
    }

    @Override
    public boolean containsKey(Object key) {
        //напишите тут ваш код
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        //напишите тут ваш код
        for (List<V> list : map.values()) {
            if (list.contains(value)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        for (Map.Entry<K, List<V>> entry : map.entrySet()) {
            sb.append(entry.getKey());
            sb.append("=");
            for (V v : entry.getValue()) {
                sb.append(v);
                sb.append(", ");
            }
        }
        String substring = sb.substring(0, sb.length() - 2);
        return substring + "}";
    }
}