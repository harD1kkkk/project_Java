package com.example.demo.controller;

import com.example.demo.db.entity.Category;
import com.example.demo.repository.ICategoryRepository;
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
class CategoryRestControllerIntegrationTest {

    @Autowired private MockMvc mvc;
    @Autowired private ICategoryRepository repo;
    @Autowired private ObjectMapper mapper;


    @BeforeEach void clean() {
        repo.deleteAll();
    }

    @Test void createCategory() throws Exception {
        Category c = new Category();
        c.setName("Electronics");
        String body = mapper.writeValueAsString(c);

        mvc.perform(post("/api/v1/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("Electronics"));
    }

    @Test void getAllCategories() throws Exception {
        repo.save(new Category(0, "Electronics", null));

        mvc.perform(get("/api/v1/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test void getCategoryById() throws Exception {
        Category saved = repo.save(new Category(0, "Electronics", null));

        mvc.perform(get("/api/v1/categories/{id}", saved.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Electronics"));
    }

    @Test void updateCategory() throws Exception {
        Category saved = repo.save(new Category(0, "Electronics", null));

        saved.setName("Home Electronics");
        String update = mapper.writeValueAsString(saved);

        mvc.perform(put("/api/v1/categories/{id}", saved.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(update))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Home Electronics"));
    }

    @Test void deleteCategory() throws Exception {
        Category saved = repo.save(new Category(0, "Electronics", null));

        mvc.perform(delete("/api/v1/categories/{id}", saved.getId()))
                .andExpect(status().isNoContent());

        mvc.perform(get("/api/v1/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }
}
