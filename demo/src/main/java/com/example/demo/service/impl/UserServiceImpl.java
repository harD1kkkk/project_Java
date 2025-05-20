package com.example.demo.service.impl;

import com.example.demo.db.entity.User;
import com.example.demo.repository.IUserRepository;
import com.example.demo.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements IUserService {
    private final IUserRepository repo;

    @Override
    public List<User> getAllUsers() {
        return repo.findAll();
    }

    @Override
    public User getUserById(int id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("User not found: " + id));
    }

    @Override
    public User createUser(User user) {
        return repo.save(user);
    }

    @Override
    public User updateUser(int id, User user) {
        User existing = getUserById(id);
        existing.setName(user.getName());
        existing.setEmail(user.getEmail());
        existing.setRole(user.getRole());
        return repo.save(existing);
    }

    @Override
    public void deleteUser(int id) {
        repo.deleteById(id);
    }
}
