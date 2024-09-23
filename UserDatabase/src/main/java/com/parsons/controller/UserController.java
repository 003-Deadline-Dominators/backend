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

}
