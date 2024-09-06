package com.parsons.service;

import com.parsons.mapper.UserMapper;
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
        userMapper.insertUser(user);
    }
    public List<User> selectAllUsers() {
        return userMapper.selectAllUsers();
    }
    public List<User> selectUsersByTopic(String topic) {
        return userMapper.selectUsersByTopic(topic);
    }
}
