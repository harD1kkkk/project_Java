package com.example.demo.controller;

import com.example.demo.db.entity.Category;
import com.example.demo.service.ICategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@Slf4j
@RequiredArgsConstructor
public class CategoryRestController {
    private final ICategoryService categoryService;

    @GetMapping
    public List<Category> getAllCategories() {
        log.info("GET /api/v1/categories");
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public Category getCategoryById(@PathVariable int id) {
        log.info("GET /api/v1/categories/{}", id);
        return categoryService.getCategoryById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Category createCategory(@RequestBody Category category) {
        log.info("POST /api/v1/categories payload={}", category);
        return categoryService.createCategory(category);
    }

    @PutMapping("/{id}")
    public Category updateCategory(@PathVariable int id, @RequestBody Category category) {
        log.info("PUT /api/v1/categories/{} payload={}", id, category);
        return categoryService.updateCategory(id, category);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable int id) {
        log.info("DELETE /api/v1/categories/{}", id);
        categoryService.deleteCategory(id);
    }
}
