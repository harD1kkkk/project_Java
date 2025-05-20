package com.example.demo.controller;

import com.example.demo.db.entity.UserRole;
import com.example.demo.service.IRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
@Slf4j
@RequiredArgsConstructor
public class RoleRestController {
    private final IRoleService roleService;

    @GetMapping
    public List<UserRole> getAllRoles() {
        log.info("GET /api/v1/roles");
        return roleService.getAllRoles();
    }

    @GetMapping("/{id}")
    public UserRole getRoleById(@PathVariable int id) {
        log.info("GET /api/v1/roles/{}", id);
        return roleService.getRoleById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserRole createRole(@RequestBody UserRole role) {
        log.info("POST /api/v1/roles payload={}", role);
        return roleService.createRole(role);
    }

    @PutMapping("/{id}")
    public UserRole updateRole(@PathVariable int id, @RequestBody UserRole role) {
        log.info("PUT /api/v1/roles/{} payload={}", id, role);
        return roleService.updateRole(id, role);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRole(@PathVariable int id) {
        log.info("DELETE /api/v1/roles/{}", id);
        roleService.deleteRole(id);
    }
}
