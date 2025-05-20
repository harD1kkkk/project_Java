package com.example.demo.service;

import com.example.demo.db.entity.User;
import java.util.List;

public interface IUserService {
    List<User> getAllUsers();
    User getUserById(int id);
    User createUser(User user);
    User updateUser(int id, User user);
    void deleteUser(int id);
}
