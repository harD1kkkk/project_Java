package com.example.demo.controller;

import com.example.demo.db.entity.Category;
import com.example.demo.db.entity.Product;
import com.example.demo.dto.CreateProductRequest;
import com.example.demo.dto.UpdateProductRequest;
import com.example.demo.repository.ICategoryRepository;
import com.example.demo.repository.IOrderRepository;
import com.example.demo.repository.IProductRepository;
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
class ProductRestControllerIntegrationTest {

    @Autowired private MockMvc mvc;
    @Autowired private ICategoryRepository catRepo;
    @Autowired private IOrderRepository ordRepo;
    @Autowired private IProductRepository prodRepo;
    @Autowired private ObjectMapper mapper;

    private Category category;

    @BeforeEach void setup() {
        ordRepo.deleteAll();
        prodRepo.deleteAll();
        catRepo.deleteAll();
        category = catRepo.save(new Category(0, "Books", null));
    }

    @Test void createProduct() throws Exception {
        CreateProductRequest dto = new CreateProductRequest();
        dto.setName("Spring in Action");
        dto.setPrice(39.99);
        dto.setCategoryId(category.getId());

        String body = mapper.writeValueAsString(dto);

        mvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("Spring in Action"))
                .andExpect(jsonPath("$.price").value(39.99));
    }

    @Test void getAllProducts() throws Exception {
        prodRepo.save(new Product(0, "Spring in Action", 39.99, category));

        mvc.perform(get("/api/v1/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test void getProductById() throws Exception {
        Product saved = prodRepo.save(new Product(0, "Spring in Action", 39.99, category));

        mvc.perform(get("/api/v1/products/{id}", saved.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Spring in Action"));
    }

    @Test void updateProduct() throws Exception {
        Product saved = prodRepo.save(new Product(0, "Spring in Action", 39.99, category));

        UpdateProductRequest upd = new UpdateProductRequest();
        upd.setName("Spring Boot in Action");
        upd.setPrice(45.0);
        upd.setCategoryId(category.getId());

        String updateJson = mapper.writeValueAsString(upd);

        mvc.perform(put("/api/v1/products/{id}", saved.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Spring Boot in Action"))
                .andExpect(jsonPath("$.price").value(45.0));
    }

    @Test void deleteProduct() throws Exception {
        Product saved = prodRepo.save(new Product(0, "Spring in Action", 39.99, category));

        mvc.perform(delete("/api/v1/products/{id}", saved.getId()))
                .andExpect(status().isNoContent());

        mvc.perform(get("/api/v1/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }
}
