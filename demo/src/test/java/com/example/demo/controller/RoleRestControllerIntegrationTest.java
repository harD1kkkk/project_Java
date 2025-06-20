package com.example.demo.controller;

import com.example.demo.db.entity.UserRole;
import com.example.demo.repository.IRoleRepository;
import com.example.demo.repository.IUserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class RoleRestControllerIntegrationTest {

    @Autowired private MockMvc mvc;
    @Autowired private IRoleRepository repo;
    @Autowired private IUserRepository userRepo;
    @Autowired private ObjectMapper mapper;

    private int roleId;

    @BeforeEach
    void setup() throws Exception {
        userRepo.deleteAll();
        repo.deleteAll();
        UserRole r = new UserRole();
        r.setName("Manager");
        String body = mapper.writeValueAsString(r);
        String json = mvc.perform(post("/api/v1/roles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
        UserRole created = mapper.readValue(json, UserRole.class);
        roleId = created.getId();
    }

    @Test
    void testCreateRole() throws Exception {
        UserRole r = new UserRole();
        r.setName("Developer");
        String body = mapper.writeValueAsString(r);
        mvc.perform(post("/api/v1/roles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("Developer"));
    }

    @Test
    void testReadAllRoles() throws Exception {
        mvc.perform(get("/api/v1/roles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void testReadRoleById() throws Exception {
        mvc.perform(get("/api/v1/roles/{id}", roleId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Manager"));
    }

    @Test
    void testUpdateRole() throws Exception {
        UserRole updatedRole = new UserRole();
        updatedRole.setId(roleId);
        updatedRole.setName("Supervisor");
        String update = mapper.writeValueAsString(updatedRole);
        mvc.perform(put("/api/v1/roles/{id}", roleId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(update))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Supervisor"));
    }

    @Test
    void testDeleteRole() throws Exception {
        mvc.perform(delete("/api/v1/roles/{id}", roleId))
                .andExpect(status().isNoContent());

        mvc.perform(get("/api/v1/roles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }
}
