package com.parsons.test;
import com.parsons.pojo.Context;
import com.parsons.mapper.UserMapper;
import com.parsons.pojo.Topic;
import com.parsons.pojo.User;
import com.parsons.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testInsertUser() {
        User user = new User();
        user.setIpAddress("127.0.0.1");

        // Mock encryptIPAddress 方法的行为
        User spyUser = spy(user);
        doReturn("encryptedIP").when(spyUser).encryptIPAddress("127.0.0.1");

        // 执行insertUser方法
        userService.insertUser(spyUser);

        // 验证是否正确调用了userMapper.insertUser
        verify(userMapper, times(1)).insertUser(spyUser);
        assertEquals("encryptedIP", spyUser.getIpAddress());
    }

    @Test
    void testSelectAllUsers() {
        List<User> expectedUsers = new ArrayList<>();
        when(userMapper.selectAllUsers()).thenReturn(expectedUsers);

        List<User> actualUsers = userService.selectAllUsers();

        // 验证是否调用了userMapper.selectAllUsers
        verify(userMapper, times(1)).selectAllUsers();
        assertEquals(expectedUsers, actualUsers);
    }

    @Test
    void testSelectUsersByTopic() {
        String topic = "testTopic";
        List<User> expectedUsers = new ArrayList<>();
        when(userMapper.selectUsersByTopic(topic)).thenReturn(expectedUsers);

        List<User> actualUsers = userService.selectUsersByTopic(topic);

        verify(userMapper, times(1)).selectUsersByTopic(topic);
        assertEquals(expectedUsers, actualUsers);
    }

    @Test
    void testInsertTopic() {
        Topic topic = new Topic();
        userService.insertTopic(topic);

        verify(userMapper, times(1)).insertTopic(topic);
    }

    @Test
    void testInsertContext() {
        Context context = new Context();
        userService.insertContext(context);

        verify(userMapper, times(1)).insertContext(context);
    }

    @Test
    void testSelectNumberOfQuestions() {
        List<String> expectedQuestions = new ArrayList<>();
        when(userMapper.SelectNumberOfQuestions()).thenReturn(expectedQuestions);

        List<String> actualQuestions = userService.SelectNumberOfQuestions();

        verify(userMapper, times(1)).SelectNumberOfQuestions();
        assertEquals(expectedQuestions, actualQuestions);
    }

    @Test
    void testSelectNumberOfQuestionsByTopic() {
        String topic = "testTopic";
        List<String> expectedQuestions = new ArrayList<>();
        when(userMapper.SelectNumberOfQuestionsByTopic(topic)).thenReturn(expectedQuestions);

        List<String> actualQuestions = userService.SelectNumberOfQuestionsByTopic(topic);

        verify(userMapper, times(1)).SelectNumberOfQuestionsByTopic(topic);
        assertEquals(expectedQuestions, actualQuestions);
    }

    @Test
    void testSelectUsersByTopicDESC() {
        String topic = "testTopic";
        List<User> expectedUsers = new ArrayList<>();
        when(userMapper.selectUsersByTopicDESC(topic)).thenReturn(expectedUsers);

        List<User> actualUsers = userService.selectUsersByTopicDESC(topic);

        verify(userMapper, times(1)).selectUsersByTopicDESC(topic);
        assertEquals(expectedUsers, actualUsers);
    }

    @Test
    void testSelectUsersByTopicASC() {
        String topic = "testTopic";
        List<User> expectedUsers = new ArrayList<>();
        when(userMapper.selectUsersByTopicASC(topic)).thenReturn(expectedUsers);

        List<User> actualUsers = userService.selectUsersByTopicASC(topic);

        verify(userMapper, times(1)).selectUsersByTopicASC(topic);
        assertEquals(expectedUsers, actualUsers);
    }

    @Test
    void testSelectAllUsersASC() {
        List<User> expectedUsers = new ArrayList<>();
        when(userMapper.selectAllUsersASC()).thenReturn(expectedUsers);

        List<User> actualUsers = userService.selectAllUsersASC();

        verify(userMapper, times(1)).selectAllUsersASC();
        assertEquals(expectedUsers, actualUsers);
    }
}

