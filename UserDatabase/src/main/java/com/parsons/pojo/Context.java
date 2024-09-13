package com.parsons.pojo;

import lombok.Data;

@Data
public class Context {
    private String contextTitle;
    private String topicTitle;

    public String getTopicTitle() {
        return topicTitle;
    }

    public String getContextTitle() {
        return contextTitle;
    }

    public void setContextTitle(String contextTitle) {
        this.contextTitle = contextTitle;
    }

    public void setTopicTitle(String topicTitle) {
        this.topicTitle = topicTitle;
    }
}
