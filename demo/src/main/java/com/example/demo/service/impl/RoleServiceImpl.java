package com.example.demo.service.impl;

import com.example.demo.db.entity.UserRole;
import com.example.demo.repository.IRoleRepository;
import com.example.demo.service.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RoleServiceImpl implements IRoleService {
    private final IRoleRepository repo;

    @Override
    public List<UserRole> getAllRoles() {
        return repo.findAll();
    }

    @Override
    public UserRole getRoleById(int id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Role not found: " + id));
    }

    @Override
    public UserRole createRole(UserRole role) {
        return repo.save(role);
    }

    @Override
    public UserRole updateRole(int id, UserRole role) {
        UserRole existing = getRoleById(id);
        existing.setName(role.getName());
        return repo.save(existing);
    }

    @Override
    public void deleteRole(int id) {
        repo.deleteById(id);
    }
}
