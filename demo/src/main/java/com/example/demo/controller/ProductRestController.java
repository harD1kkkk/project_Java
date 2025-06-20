package com.example.demo.controller;

import com.example.demo.db.entity.Category;
import com.example.demo.db.entity.Product;
import com.example.demo.dto.CreateProductRequest;
import com.example.demo.dto.UpdateProductRequest;
import com.example.demo.repository.ICategoryRepository;
import com.example.demo.service.IProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@Slf4j
@RequiredArgsConstructor
public class ProductRestController {
    private final IProductService productService;
    private final ICategoryRepository categoryRepository;

    @GetMapping
    public List<Product> getAllProducts() {
        log.info("GET /api/v1/products");
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable int id) {
        log.info("GET /api/v1/products/{}", id);
        return productService.getProductById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product createProduct(@RequestBody CreateProductRequest req) {
        log.info("POST /api/v1/products payload={}", req);
        Category cat = categoryRepository.findById(req.getCategoryId())
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
        Product p = new Product();
        p.setName(req.getName());
        p.setPrice(req.getPrice());
        p.setCategory(cat);
        return productService.createProduct(p);
    }

    @PutMapping("/{id}")
    public Product updateProduct(
            @PathVariable int id,
            @RequestBody UpdateProductRequest req) {
        log.info("PUT /api/v1/products/{} payload={}", id, req);
        return productService.updateProduct(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable int id) {
        log.info("DELETE /api/v1/products/{}", id);
        productService.deleteProduct(id);
    }
}
