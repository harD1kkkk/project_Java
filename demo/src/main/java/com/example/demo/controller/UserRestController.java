package com.example.demo.controller;

import com.example.demo.db.entity.User;
import com.example.demo.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@Slf4j
@RequiredArgsConstructor
public class UserRestController {
    private final IUserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        log.info("GET /api/v1/users");
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable int id) {
        log.info("GET /api/v1/users/{}", id);
        return userService.getUserById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody User user) {
        log.info("POST /api/v1/users payload={}", user);
        return userService.createUser(user);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable int id, @RequestBody User user) {
        log.info("PUT /api/v1/users/{} payload={}", id, user);
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable int id) {
        log.info("DELETE /api/v1/users/{}", id);
        userService.deleteUser(id);
    }
}
