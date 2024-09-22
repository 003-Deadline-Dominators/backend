package com.parsons.test;

import com.parsons.mapper.TopicMapper;
import com.parsons.pojo.Context;
import com.parsons.pojo.Topic;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TopicTest {

    @Autowired
    private TopicMapper topicMapper;

    @Test
    public void testSelectAllTopics() {
        List<Topic> topics = topicMapper.selectAllTopics();

        // verify that the query result is not null
        assertNotNull(topics, "Topics list should not be null");

        // at least one topic should be in the database
        assertTrue(topics.size() > 0, "There should be at least one topic in the database");

        topics.forEach(topic -> {
            assertNotNull(topic.getTopicTitle(), "Topic title should not be null");
            System.out.println(topic);
        });
    }

    // test selectContextByTopic method
    @Test
    public void testSelectContextByTopic() {
        String topicTitle = "Linear Regression";
        List<Context> topics = topicMapper.selectTopicByTitle(topicTitle);

        // verify that the query result is not null
        assertNotNull(topics, "Topic should not be null");

        // verify that at least one topic is returned
        assertFalse(topics.isEmpty(), "At least one topic should be returned");

        // verify that the topic title matches the query
        topics.forEach(topic -> {
            assertEquals(topicTitle, topic.getTopicTitle(), "The topic title should match the query");
            System.out.println(topic);
        });
    }

    // verify that no topic is returned for a non-existing title
    @Test
    public void testSelectNonExistingTopic() {
        String nonExistingTitle = "Non Existing Topic";
        List<Context> topics = topicMapper.selectTopicByTitle(nonExistingTitle);

        // verify that no topic is returned
        assertTrue(topics.isEmpty(), "No topic should be returned for a non-existing title");
    }
}
