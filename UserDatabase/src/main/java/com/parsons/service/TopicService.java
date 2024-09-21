package com.parsons.service;

import com.parsons.pojo.Topic;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class TopicService {
    private final List<Topic> topics = Arrays.asList(
            new Topic("DataFrame", "Learn about DataFrames"),
            new Topic("Correlation", "Understanding correlations"),
            new Topic("Linear Regression", "Learn about linear regression"),
            new Topic("NMI", "Learn about Normalized Mutual Information"),
            new Topic("Sentence splitting using nltk", "Learn how to split sentences using nltk"),
            new Topic("Reading/Writing CSV files", "Learn how to handle CSV files"),
            new Topic("Decision Tree Classifier", "Introduction to Decision Tree Classifier")
    );
    public List<Topic> getAllTopics() {
        return topics;
    }

    public Topic getTopicByName(String name) {
        return topics.stream().filter(topic -> topic.getName().equals(name)).findFirst().get();
    }

}
