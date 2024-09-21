package com.parsons.pojo;

import lombok.Data;

@Data
public class Topic {
    private String topicTitle;
    private String topicDescription;

    public Topic(String topicTitle, String topicDescription) {
        this.topicTitle = topicTitle;
        this.topicDescription = topicDescription;
    }

    public String getTopicTitle() {
        return topicTitle;
    }

    public String getTopicDescription() {
        return topicDescription;
    }
}
