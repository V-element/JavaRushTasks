package com.javarush.task.task36.task3602;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/* 
Найти класс по описанию
*/
public class Solution {
    public static void main(String[] args) {
        System.out.println(getExpectedClass());
    }

    public static Class getExpectedClass() {
        Class clazz;
        Class[] classes = Arrays.stream(Collections.class.getDeclaredClasses())
                .filter(c-> List.class.isAssignableFrom(c)
                        && Modifier.isStatic(c.getModifiers())
                        && Modifier.isPrivate(c.getModifiers()))
                .toArray(Class[]::new);

            for (Class c:classes) {
                Method method = null;
                try {
                    method = c.getDeclaredMethod("get", int.class);
                    method.setAccessible(true);
                } catch (NoSuchMethodException e) {
                    //e.printStackTrace();
                }
                Constructor constructor = null;
                try {
                    constructor = c.getDeclaredConstructor();
                    constructor.setAccessible(true);
                } catch (NoSuchMethodException e) {
                    //e.printStackTrace();
                }
                try {
                    if (constructor != null)
                        method.invoke(constructor.newInstance(),0);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    if (e.getCause().toString().contains("IndexOutOfBoundsException"))
                        return c;
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }

        }


        return null;
    }
}
