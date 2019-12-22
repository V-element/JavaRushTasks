package com.javarush.task.task38.task3810;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.ANNOTATION_TYPE)
public @interface Revision {
    int revision();
    Date date();
    Author[] authors() default {};
    String comment() default "";
}
