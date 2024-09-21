package com.parsons.test;

import com.parsons.mapper.TopicMapper;
import com.parsons.pojo.Topic;
import com.parsons.service.TopicService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class TopicServiceTest {

    @Mock
    private TopicMapper topicMapper;

    @InjectMocks
    private TopicService topicService;

    public TopicServiceTest() {
        MockitoAnnotations.openMocks(this);  // 初始化 @Mock 和 @InjectMocks
    }

    @Test
    public void testGetAllTopics() {
        // 模拟 Mapper 返回的结果
        List<Topic> mockTopics = Arrays.asList(new Topic("1", "Topic 1"), new Topic("2", "Topic 2"));
        when(topicMapper.selectAllTopics()).thenReturn(mockTopics);

        // 调用被测试方法
        List<Topic> topics = topicService.selectAllTopics();

        // 验证结果
        assertNotNull(topics);
        assertEquals(2, topics.size());
        assertEquals("1", topics.get(0).getTopicTitle());

        // 验证 Mapper 的调用次数
        verify(topicMapper, times(1)).selectAllTopics();
    }
}

