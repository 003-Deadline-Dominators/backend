package com.parsons.pojo;

import lombok.Data;

@Data
public class Topic {
    private String name;
    private String description;

    public Topic(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
