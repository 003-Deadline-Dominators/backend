package com.parsons.pojo;

import lombok.Data;

@Data
public class Context {
    private String contextTitle;
    private String topicTitle;

    public Context(String contextTitle, String topicTitle) {
        this.contextTitle = contextTitle;
        this.topicTitle = topicTitle;
    }
}
