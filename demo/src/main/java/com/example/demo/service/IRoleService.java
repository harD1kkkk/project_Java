package com.example.demo.service;

import com.example.demo.db.entity.UserRole;
import java.util.List;

public interface IRoleService {
    List<UserRole> getAllRoles();
    UserRole getRoleById(int id);
    UserRole createRole(UserRole role);
    UserRole updateRole(int id, UserRole role);
    void deleteRole(int id);
}
