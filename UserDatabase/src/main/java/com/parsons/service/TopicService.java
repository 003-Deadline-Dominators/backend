package com.parsons.service;

import com.parsons.mapper.TopicMapper;
import com.parsons.pojo.Context;
import com.parsons.pojo.Topic;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicService {
    private final TopicMapper topicMapper;

    public TopicService(TopicMapper topicMapper) { this.topicMapper = topicMapper; }
    public List<Topic> selectAllTopics() { return topicMapper.selectAllTopics(); }
    public List<Context> selectContextsByTopic(int topicId) { return topicMapper.selectContextsByTopic(topicId); }


}
