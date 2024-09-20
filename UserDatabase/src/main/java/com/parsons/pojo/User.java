package com.parsons.pojo;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class User {
    private String ipAddress;
    private boolean correctness;
    private String topicCategory;
    private int duration;
    private String contexts;
    private Timestamp dataTimestamps;
}
