package com.pcshop.demoone.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import com.pcshop.demoone.api.model.User;
import com.pcshop.demoone.service.UserService;

@RestController
@RequestMapping(value = "/PCshop")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;

    }

    @GetMapping("/user")
    public User getUser(@RequestParam Integer id) {
        return userService.getUser(id);
    }

    @GetMapping("/users")
    public List<User> getUserList() {
        return userService.getUserList();
    }

    @PostMapping("/user/register")
    public String registerUser(@RequestBody User user) {
        return userService.registerUser(user);
    }
}
