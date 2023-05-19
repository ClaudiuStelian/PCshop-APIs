package com.pcshop.demoone.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.pcshop.demoone.api.model.User;

@Service
public class UserService {

    public static List<User> userList = new ArrayList<>();

    public User getUser(Integer id) {
        for (User user : userList) {
            if (id == user.getId()) {
                return user;
            }
        }
        return null;
    }

    public String registerUser(User user) {
        userList.add(user);
        return "User is created successfully";
    }

    public List<User> getUserList() {
        return userList;
    }

    public String getUser(String userName) {
        for (User user : userList) {
            String nameUser = user.getName();
            if (userName.equals(nameUser)) {
                return userName;
            }
        }
        return null;
    }
}
