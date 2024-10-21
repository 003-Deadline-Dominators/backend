package com.parsons.pojo;

import lombok.Data;

@Data
public class Topic {
    private String topicTitle;
    private String topicDescription;
    private String  rating;

    public Topic(){}
    public Topic(String topicTitle, String topicDescription, String  rating) {
        this.topicTitle = topicTitle;
        this.topicDescription = topicDescription;
        this.rating = rating;
    }

    public String getTopicTitle() {
        return topicTitle;
    }

    public String getTopicDescription() {
        return topicDescription;
    }
}
