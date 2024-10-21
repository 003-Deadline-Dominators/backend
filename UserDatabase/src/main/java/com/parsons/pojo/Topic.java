package com.parsons.pojo;

import lombok.Data;

@Data
public class Topic {
    private String topicTitle;
    private String topicDescription;
<<<<<<< Updated upstream
=======
    private String  rating;
    public Topic() {

    }
>>>>>>> Stashed changes

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
