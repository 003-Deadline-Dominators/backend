package com.parsons.mapper;

import com.parsons.pojo.Topic;
import com.parsons.pojo.Context;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TopicMapper {

    // 查询所有Topic
    List<Topic> selectAllTopics();

    // 根据 topicId 查询 Context
    List<Context> selectContextsByTopic(String topicTitle);

    // 根据 topicId 查询 Context
    List<Context> selectTopicByTitle(String topicTitle);
    int numberOfTrueContexts(@Param("ip") String ip, @Param("context") String context);

}
