package com.javarush.task.task28.task2810.model;

import com.javarush.task.task28.task2810.vo.Vacancy;

import java.util.List;

public interface Strategy {
    public List<Vacancy> getVacancies(String searchString);
}
