package com.javarush.task.task38.task3810;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.ANNOTATION_TYPE)
public @interface Author {
    String value() default "";
    Position position() default Position.OTHER;
}
