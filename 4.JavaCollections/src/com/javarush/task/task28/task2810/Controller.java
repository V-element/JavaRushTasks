package com.javarush.task.task28.task2810;

import com.javarush.task.task28.task2810.model.Provider;

import java.util.Arrays;

public class Controller {
    private Provider[] providers;

    public Controller(Provider...providers) {
        this.providers = providers;
        if (providers.length == 0) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public String toString() {
        return "Controller{" +
                "providers=" + Arrays.toString(providers) +
                '}';
    }
}
