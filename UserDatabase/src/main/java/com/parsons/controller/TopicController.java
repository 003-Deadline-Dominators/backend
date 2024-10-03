package com.parsons.controller;

import com.parsons.pojo.Context;
import com.parsons.pojo.Topic;
import com.parsons.service.TopicService;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
@RequestMapping("/topics")
public class TopicController {
    private final TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @GetMapping("/all")
    public List<Topic> getAllTopics() {
        List<Topic> topics = topicService.selectAllTopics();
        return topics;
    }

    @GetMapping("/contexts/{topicTitle}")
    public List<Context> getContextsByTopic(@PathVariable String topicTitle) {
        topicTitle = URLDecoder.decode(topicTitle, StandardCharsets.UTF_8);
        System.out.println(topicTitle);
        return topicService.selectContextsByTopic(topicTitle);
    }

    @GetMapping("/contexts/{ip}/{topic}")
    public List<List<String>> getNumberOfTrueContexts(@PathVariable String ip, @PathVariable String topic) {
        ip = URLDecoder.decode(ip, StandardCharsets.UTF_8);
        topic = URLDecoder.decode(topic, StandardCharsets.UTF_8);
        return topicService.numberOfTrueContexts(ip, topic);
    }
}
