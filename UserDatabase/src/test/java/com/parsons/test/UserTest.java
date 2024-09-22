package com.parsons.test;

import com.parsons.mapper.UserMapper;
import com.parsons.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserTest {

    @Autowired
    UserMapper userMapper;

    // 测试 selectAllUsers 方法
    @Test
    public void testSelectAllUsers() {
        List<User> users = userMapper.selectAllUsers();
        assertNotNull(users);  // verify that the query result is not null
        assertTrue(users.size() > 0);  // verify that there are users in the database
        users.forEach(user -> {
            assertNotNull(user.getIpAddress());  // verify that the user's IP address is not null
            System.out.println(user);
        });
    }

    // 测试根据话题查询用户
    @Test
    public void testSelectUserByTopic() {
        String topic = "Linear Regression";
        List<User> users = userMapper.selectUsersByTopic(topic);
        assertNotNull(users);
        users.forEach(user -> {
            // verify that the user's topic category matches the expected topic
            assertEquals(topic, user.getTopicCategory());
            System.out.println(user);
        });
    }

    // 测试插入用户
    @Test
    public void testInsertUser() {
        User user = new User();
        user.setIpAddress("192.168.1.1");
        user.setCorrectness(true);
        user.setTopicCategory("Test Category");
        user.setDuration(120);
        user.setContexts("Test Context");
        user.setSubmitTime(new Timestamp(System.currentTimeMillis()));
        user.setGenerateTime(new Timestamp(System.currentTimeMillis()));

        int result = userMapper.insertUser(user);
        assertEquals(1, result);  // 验证插入操作返回1，表示成功插入了一条记录

        // 验证插入后的数据
        List<User> users = userMapper.selectUsersByTopic("Test Category");
        assertTrue(users.size() > 0);  // 验证有用户被插入到数据库
        boolean found = false;
        for (User u : users) {
            if ("192.168.1.1".equals(u.getIpAddress()) && "Test Category".equals(u.getTopicCategory())) {
                found = true;
                break;
            }
        }
        assertTrue(found, "Inserted user not found in the database.");
    }
}

