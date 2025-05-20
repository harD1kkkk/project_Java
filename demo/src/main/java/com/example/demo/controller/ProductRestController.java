package com.example.demo.controller;

import com.example.demo.db.entity.Product;
import com.example.demo.service.IProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@Slf4j
@RequiredArgsConstructor
public class ProductRestController {
    private final IProductService productService;

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
    public Product createProduct(@RequestBody Product product) {
        log.info("POST /api/v1/products payload={}", product);
        return productService.createProduct(product);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable int id, @RequestBody Product product) {
        log.info("PUT /api/v1/products/{} payload={}", id, product);
        return productService.updateProduct(id, product);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable int id) {
        log.info("DELETE /api/v1/products/{}", id);
        productService.deleteProduct(id);
    }
}
