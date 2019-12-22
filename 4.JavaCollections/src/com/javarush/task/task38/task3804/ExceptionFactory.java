package com.javarush.task.task38.task3804;

public class ExceptionFactory {

    public static Throwable getException(Enum e) {
        if (e != null) {
            String message = e.toString().replaceAll("_", " ").toLowerCase();
            message = message.substring(0, 1).toUpperCase() + message.substring(1);

            if (e instanceof ApplicationExceptionMessage) {
                return new Exception(message);
            } else if (e instanceof DatabaseExceptionMessage) {
                return new RuntimeException(message);
            } else if (e instanceof UserExceptionMessage) {
                return new Error(message);
            } else {
                return new IllegalArgumentException();
            }
        }
        return new IllegalArgumentException();
    }
}