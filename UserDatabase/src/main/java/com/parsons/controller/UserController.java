package com.parsons.controller;
import com.parsons.pojo.User;
import com.parsons.service.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping
    public String defaultResponse() {
        return "UserController is up and running!";
    }

    @PostMapping("/insert")
    public void insertUser(@RequestBody User user) {
        System.out.println("insert invoked");
        userService.insertUser(user);
    }
    @GetMapping("/all")
    public List<User> getAllUsers() {
        List<User> users = userService.selectAllUsers();
        System.out.println("getAllUsers invoked");
        System.out.println(users);
        return users;
    }
}
