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

    public Timestamp getDataTimestamps() {
        return dataTimestamps;
    }

    public void setDataTimestamps(Timestamp dataTimestamps) {
        this.dataTimestamps = dataTimestamps;
    }

    public String getContexts() {
        return contexts;
    }

    public void setContexts(String contexts) {
        this.contexts = contexts;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getTopicCategory() {
        return topicCategory;
    }

    public void setTopicCategory(String topicCategory) {
        this.topicCategory = topicCategory;
    }

    public boolean isCorrectness() {
        return correctness;
    }

    public void setCorrectness(boolean correctness) {
        this.correctness = correctness;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}
