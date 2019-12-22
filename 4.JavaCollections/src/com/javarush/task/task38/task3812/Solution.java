package com.javarush.task.task38.task3812;

/* 
Обработка аннотаций
*/

import java.util.Arrays;

public class Solution {
    public static void main(String[] args) {
        printFullyQualifiedNames(Solution.class);
        printFullyQualifiedNames(SomeTest.class);

        printValues(Solution.class);
        printValues(SomeTest.class);
    }

    public static boolean printFullyQualifiedNames(Class c) {
        if (c.isAnnotationPresent(PrepareMyTest.class)) {
            PrepareMyTest prepareMyTest = (PrepareMyTest) c.getAnnotation(PrepareMyTest.class);
            String[] s = prepareMyTest.fullyQualifiedNames();
            for (String str: s) {
                System.out.println(str);
            }
            return true;
        } else {
            return false;
        }
    }

    public static boolean printValues(Class c) {
        if (c.isAnnotationPresent(PrepareMyTest.class)) {
            PrepareMyTest prepareMyTest = (PrepareMyTest) c.getAnnotation(PrepareMyTest.class);
            Class<?>[] classes = prepareMyTest.value();
            for (Class clazz: classes) {
                System.out.println(clazz.getSimpleName());
            }
            //System.out.println(Arrays.toString(classes));
            return true;
        } else {
            return false;
        }
    }
}
