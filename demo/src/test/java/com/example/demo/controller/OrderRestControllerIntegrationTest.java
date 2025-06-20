package com.example.demo.controller;

import com.example.demo.db.entity.*;
import com.example.demo.db.entity.Order;
import com.example.demo.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class OrderRestControllerIntegrationTest {

    @Autowired private MockMvc mvc;
    @Autowired private IUserRepository userRepo;
    @Autowired private IRoleRepository roleRepo;
    @Autowired private ICategoryRepository catRepo;
    @Autowired private IProductRepository prodRepo;
    @Autowired private IOrderRepository orderRepo;
    @Autowired private ObjectMapper mapper;

    private User user;
    private Product product;

    @BeforeEach void setup() {
        orderRepo.deleteAll();
        prodRepo.deleteAll();
        userRepo.deleteAll();
        roleRepo.deleteAll();
        catRepo.deleteAll();

        UserRole role = roleRepo.save(new UserRole(0, "CUSTOMER", null));
        user = userRepo.save(new User(0, "Bob", "bob@test.com", role));
        Category category = catRepo.save(new Category(0, "Gadgets", null));
        product = prodRepo.save(new Product(0, "Smartphone", 199.99, category));
    }

    @Test void createOrder() throws Exception {
        Order o = new Order(0, LocalDateTime.now(), user, product, 2);
        String body = mapper.writeValueAsString(o);

        mvc.perform(post("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.quantity").value(2));
    }

    @Test void getAllOrders() throws Exception {
        orderRepo.save(new Order(0, LocalDateTime.now(), user, product, 2));

        mvc.perform(get("/api/v1/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test void getOrderById() throws Exception {
        Order saved = orderRepo.save(new Order(0, LocalDateTime.now(), user, product, 2));

        mvc.perform(get("/api/v1/orders/{id}", saved.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity").value(2));
    }

    @Test void updateOrder() throws Exception {
        Order saved = orderRepo.save(new Order(0, LocalDateTime.now(), user, product, 2));
        saved.setQuantity(5);
        String update = mapper.writeValueAsString(saved);

        mvc.perform(put("/api/v1/orders/{id}", saved.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(update))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity").value(5));
    }

    @Test void deleteOrder() throws Exception {
        Order saved = orderRepo.save(new Order(0, LocalDateTime.now(), user, product, 2));

        mvc.perform(delete("/api/v1/orders/{id}", saved.getId()))
                .andExpect(status().isNoContent());

        mvc.perform(get("/api/v1/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }
}
