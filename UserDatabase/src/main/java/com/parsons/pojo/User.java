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
    private Timestamp submitTime;
    private Timestamp generateTime;

    public void setGenerateTime(Timestamp generateTime) {
        this.generateTime = generateTime;
    }

    public void setSubmitTime(Timestamp submitTime) {
        this.submitTime = submitTime;
    }

    public Timestamp getGenerateTime() {
        return generateTime;
    }

    public Timestamp getSubmitTime() {
        return submitTime;
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
    public String encryptIPAddress() {
        StringBuilder encrypted = new StringBuilder();
<<<<<<< Updated upstream

        for (char c : this.ipAddress.toCharArray()) {
=======
        if (ipAddress == null) {
            return null;
        }
        for (char c : ipAddress.toCharArray()) {
>>>>>>> Stashed changes
            // convert to ASCII value
            int asciiValue = (int) c;
            // map to a-z
            char encryptedChar = (char) ('a' + (asciiValue % 26));
            encrypted.append(encryptedChar);
        }

        return encrypted.toString();
    }
    public String decryptIPAddress() {
        StringBuilder decrypted = new StringBuilder();

        // 遍历加密后的字符串的每个字符
        for (char c : this.ipAddress.toCharArray()) {
            // convert to ASCII value
            int originalAscii = ((c - 'a') + 26);
            decrypted.append((char) originalAscii);
        }

        return decrypted.toString();
    }
}