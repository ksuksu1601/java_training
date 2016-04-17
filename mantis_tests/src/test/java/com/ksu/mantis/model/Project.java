package com.ksu.mantis.model;

/**
 * Created by Ksu on 17.04.2016.
 */
public class Project {

    private int id;

    public int getId() {
        return id;
    }

    public Project withId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Project withName(String name) {
        this.name = name;
        return this;
    }

    private String name;

}
