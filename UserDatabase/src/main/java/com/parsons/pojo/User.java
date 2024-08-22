package com.parsons.pojo;

import lombok.Data;

@Data
public class User {
    private String ip_address;
    private boolean correctness;
    private String topic_category;
    private int duration;
    private String context;
}
