package com.parsons.controller;
import com.parsons.pojo.Context;
import com.parsons.pojo.Topic;
import com.parsons.pojo.User;
import com.parsons.service.TopicService;
import com.parsons.service.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://54.252.5.239")
@RequestMapping("/admin")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping
    public String defaultResponse() {
        return "UserController is up and running!";
    }

    @PostMapping("/insertData")
    public void insertUser(@RequestBody User user) {
        userService.insertUser(user);
    }

    @GetMapping("/all")
    public List<User> getAllUsers() {
        List<User> users = userService.selectAllUsers();
        System.out.println("getAllUsers invoked");
        System.out.println(users);
        return users;
    }
    @GetMapping("/topic/{topic}")
    public List<User> getUsersByTopic(@PathVariable String topic) {
        List<User> users = userService.selectUsersByTopic(topic);
        return users;
    }
    @PostMapping("/insertTopic")
    public void insertTopic(@RequestBody Topic topic) {
        userService.insertTopic(topic);
    }
    @PostMapping("/insertContext")
    public void insertContext(@RequestBody Context context) {
        userService.insertContext(context);
    }
    @GetMapping("/selectNumberOfQuestions")
    public List<String> selectNumberOfQuestions() {
        return userService.SelectNumberOfQuestions();
    }
    @GetMapping("/selectNumberOfQuestions/{topic}")
    public List<String> selectNumberOfQuestionsByTopic(@PathVariable String topic) {
        System.out.println("Received topic: " + topic);
        return userService.SelectNumberOfQuestionsByTopic(topic);
    }
    @GetMapping("/selectUsersByTopicDESC/{topic}/{SortBy}")
    public List<User> selectUsersByTopicDESC(@PathVariable String topic, @PathVariable String SortBy) {
        System.out.println(topic);
        if (SortBy.equals("Oldest")) {
            if(topic.equals("All topics")) {
                return userService.selectAllUsersASC();
            }
            return userService.selectUsersByTopicASC(topic);
        }
        else{
            if (topic.equals("All topics")) {
                return userService.selectAllUsers();
        }
        return userService.selectUsersByTopicDESC(topic);
    }
    }
}
