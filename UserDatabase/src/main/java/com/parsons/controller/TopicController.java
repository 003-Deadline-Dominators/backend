package com.parsons.controller;

import com.parsons.pojo.Context;
import com.parsons.pojo.Topic;
import com.parsons.service.TopicService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/topics")
public class TopicController {
    private final TopicService topicService;
    public TopicController(TopicService topicService) { this.topicService = topicService; }

    @GetMapping("/all")
    public List<Topic> getAllTopics() {
        List<Topic> topics = topicService.selectAllTopics();
        return topics;
    }

    @GetMapping("/contexts/{topicTitle}")
    public List<Context> getContextsByTopic(@PathVariable String topicTitle) {
        return topicService.selectContextsByTopic(topicTitle);
    }
}
