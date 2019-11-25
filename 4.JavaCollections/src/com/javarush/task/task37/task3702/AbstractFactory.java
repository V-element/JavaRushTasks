package com.javarush.task.task37.task3702;

import com.javarush.task.task37.task3702.female.KidGirl;
import com.javarush.task.task37.task3702.female.TeenGirl;
import com.javarush.task.task37.task3702.female.Woman;

public interface AbstractFactory {

    Human getPerson(int age);
}
