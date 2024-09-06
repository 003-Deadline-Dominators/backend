package com.parsons.pojo;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class User {
    private String ip_address;
    private boolean correctness;
    private String topic_category;
    private int duration;
    private String context;
    private Timestamp timestamp;
}
