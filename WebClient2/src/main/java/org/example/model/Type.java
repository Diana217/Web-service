package org.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Type {
    @JsonProperty("Id")
    private int id;
    @JsonProperty("Name")
    private String name;

    public boolean validate(String value) {
        return true;
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
