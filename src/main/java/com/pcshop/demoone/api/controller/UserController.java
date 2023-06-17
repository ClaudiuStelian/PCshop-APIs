package com.pcshop.demoone.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pcshop.demoone.api.model.User;
import com.pcshop.demoone.service.UserService;

@RestController
@RequestMapping(value = "/PCshop")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/userid/{id}")
    public ResponseEntity<?> findUser(@PathVariable int id) {
        return userService.getUser(id);
    }

    @GetMapping("/user/{email}")
    public ResponseEntity<?> findUserByEMail(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }

    @GetMapping("/users")
    public ResponseEntity<?> getUserList() {
        return userService.getAllUserList();
    }

    @PostMapping("/user/register")
    public ResponseEntity<?> registerUser(@RequestBody(required = false) User user) {
        if (((user == null)) || user.getCard() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userService.userHint());
        }
        return userService.registerUser(user);
    }

    @PatchMapping("/user/patch")
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @DeleteMapping("/user/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(required = false) int id,
            @RequestHeader(value = "password", required = false) String password) {
        return userService.deleterUser(id, password);
    }

}
