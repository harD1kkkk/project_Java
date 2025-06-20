package com.example.demo.controller;

import com.example.demo.db.entity.User;
import com.example.demo.db.entity.UserRole;
import com.example.demo.repository.IUserRepository;
import com.example.demo.repository.IRoleRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserRestControllerIntegrationTest {

    @Autowired private MockMvc mvc;
    @Autowired private IUserRepository userRepo;
    @Autowired private IRoleRepository roleRepo;
    @Autowired private ObjectMapper mapper;
    private UserRole role;

    @BeforeEach
    void setup() {
        userRepo.deleteAll();
        roleRepo.deleteAll();
        role = roleRepo.save(new UserRole(0, "TestRole", null));
    }

    @Test
    void createUser() throws Exception {
        User create = new User();
        create.setName("Alice");
        create.setEmail("alice@test.com");
        create.setRole(role);
        String body = mapper.writeValueAsString(create);

        mvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("Alice"))
                .andExpect(jsonPath("$.email").value("alice@test.com"));
    }

    @Test
    void getAllUsers() throws Exception {
        User user = userRepo.save(new User(0, "Alice", "alice@test.com", role));
        mvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Alice"));
    }

    @Test
    void getUserById() throws Exception {
        User user = userRepo.save(new User(0, "Alice", "alice@test.com", role));
        mvc.perform(get("/api/v1/users/{id}", user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("alice@test.com"));
    }

    @Test
    void updateUser() throws Exception {
        User user = userRepo.save(new User(0, "Alice", "alice@test.com", role));
        user.setName("Alice Smith");
        user.setEmail("alice.smith@test.com");
        String update = mapper.writeValueAsString(user);

        mvc.perform(put("/api/v1/users/{id}", user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(update))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Alice Smith"))
                .andExpect(jsonPath("$.email").value("alice.smith@test.com"));
    }

    @Test
    void deleteUser() throws Exception {
        User user = userRepo.save(new User(0, "Alice", "alice@test.com", role));
        mvc.perform(delete("/api/v1/users/{id}", user.getId()))
                .andExpect(status().isNoContent());
        mvc.perform(get("/api/v1/users/{id}", user.getId()))
                .andExpect(status().is5xxServerError());
    }
}
