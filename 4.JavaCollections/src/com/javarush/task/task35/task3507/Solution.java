package com.javarush.task.task35.task3507;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

/* 
ClassLoader - что это такое?
*/
public class Solution {
    public static void main(String[] args) {
        Set<? extends Animal> allAnimals = getAllAnimals(Solution.class.getProtectionDomain().getCodeSource().getLocation().getPath() + Solution.class.getPackage().getName().replaceAll("[.]", "/") + "/data");
        System.out.println(allAnimals);
    }

    public static Set<? extends Animal> getAllAnimals(String pathToAnimals) {
        HashSet<Animal> hashSet = new HashSet<>();
        File path = new File(pathToAnimals);
        for (File file: path.listFiles()) {
            if (file.getName().endsWith(".class")) {
                try {
                    //String className = file.getName().split("\\.")[0];
                    MyLoader myLoader = new MyLoader();
                    Class clazz = myLoader.findClass(file.toString());
                    //for (Constructor constructor : clazz.getConstructors()) {
                        Constructor constructor = clazz.getConstructor();
                        if (constructor.getParameterCount() == 0 && constructor.getModifiers() == Modifier.PUBLIC && Animal.class.isAssignableFrom(clazz)) {
                            hashSet.add((Animal) clazz.newInstance());
                        }
                    //}
                } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException e) {

                }
            }
        }

        return hashSet;
    }
}


class MyLoader extends ClassLoader {

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] bytes = new byte[0];
        try {
            bytes = Files.readAllBytes(Paths.get(name));
        } catch (IOException e) {
        }
        Class<?> cl = defineClass(null, bytes, 0, bytes.length);
        return cl;
    }
}