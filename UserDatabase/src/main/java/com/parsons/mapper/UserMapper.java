package com.parsons.mapper;

import com.parsons.pojo.Context;
import com.parsons.pojo.Topic;
import com.parsons.pojo.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    int insertUser(User user);
    List<User> selectAllUsers();
    List<User> selectUsersByTopic(String topic);
    int insertTopic(Topic topic);
    int insertContext(Context context);

    List<String> SelectNumberOfQuestions();

    List<String> SelectNumberOfQuestionsByTopic(String topic);

    List<User> selectUsersByTopicDESC(String topic);

    List<User> selectUsersByTopicASC(String topic);

    List<User> selectAllUsersASC();
}
