package com.parsons.service;

import com.parsons.mapper.TopicMapper;
import com.parsons.pojo.Context;
import com.parsons.pojo.Topic;
import com.parsons.pojo.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TopicService {
    private final TopicMapper topicMapper;

    public TopicService(TopicMapper topicMapper) { this.topicMapper = topicMapper; }
    public List<Topic> selectAllTopics() { return topicMapper.selectAllTopics(); }
    public List<Context> selectContextsByTopic(String topicTitle) { return topicMapper.selectContextsByTopic(topicTitle); }
    public List<List<String>> numberOfTrueContexts(String ip, String topic) {
        String encryptedIPAddress = User.encryptIPAddress(ip);
        List<Context> contexts = topicMapper.selectContextsByTopic(topic);
        List<List<String>> result = new ArrayList<>();
        for (Context context : contexts) {
            List<String> temp = new ArrayList<>();
            temp.add(context.getContextTitle());
            String correctness = String.valueOf(topicMapper.numberOfTrueContexts(encryptedIPAddress, context.getContextTitle()));
            temp.add(correctness);
            result.add(temp);
        }
        return result;
    }

}
