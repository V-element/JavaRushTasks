package com.javarush.task.task33.task3313;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

@JsonAutoDetect
public class Event {
    public String name;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    public Date eventDate;

    public Event(String name) {
        this.name = name;
        this.eventDate = new Date();
    }

    public Event() {
    }

    public Event(String name, Date eventDate) {
        this.name = name;
        this.eventDate = eventDate;
    }
}