package com.parsons.service;

import com.parsons.mapper.UserMapper;
import com.parsons.pojo.Context;
import com.parsons.pojo.Topic;
import com.parsons.pojo.User;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {
    private final UserMapper userMapper;
    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
    public void insertUser(User user) {
        String encryptedIPAddress = user.encryptIPAddress(user.getIpAddress());
        user.setIpAddress(encryptedIPAddress);
        userMapper.insertUser(user);
    }
    public List<User> selectAllUsers() {
        return userMapper.selectAllUsers();
    }
    public List<User> selectUsersByTopic(String topic) { return userMapper.selectUsersByTopic(topic); }
    public void insertTopic(Topic topic) { userMapper.insertTopic(topic);}
    public void insertContext(Context context) { userMapper.insertContext(context); }
    public List<String> SelectNumberOfQuestions() {return userMapper.SelectNumberOfQuestions();}
    public List<String> SelectNumberOfQuestionsByTopic(String topic) {
        return userMapper.SelectNumberOfQuestionsByTopic(topic);
    }
    public List<User> selectUsersByTopicDESC(String topic) { return userMapper.selectUsersByTopicDESC(topic); }
    public List<User> selectUsersByTopicASC(String topic) { return userMapper.selectUsersByTopicASC(topic); }
    public List<User> selectAllUsersASC() { return userMapper.selectAllUsersASC(); }
}