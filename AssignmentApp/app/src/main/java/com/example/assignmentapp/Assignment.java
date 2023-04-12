package com.example.assignmentapp;

import java.util.Date;

public class Assignment {
    private String course;
    private String name;
    private String description;
    private Date dueDate;

    public Assignment(String course, Date dueDate, String name, String description) {
        this.course = course;
        this.dueDate = dueDate;
        this.name = name;
        this.description = description;
    }


    public String getCourse() {
        return course;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Date getDueDate() {
        return dueDate;
    }
}

