package com.example.demo.service.impl;

import com.example.demo.db.entity.Category;
import com.example.demo.db.entity.Product;
import com.example.demo.dto.UpdateProductRequest;
import com.example.demo.repository.ICategoryRepository;
import com.example.demo.repository.IProductRepository;
import com.example.demo.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements IProductService {
    private final IProductRepository repo;
    private final ICategoryRepository categoryRepository;

    @Override
    public List<Product> getAllProducts() {
        return repo.findAll();
    }

    @Override
    public Product getProductById(int id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Product not found: " + id));
    }

    @Override
    public Product createProduct(Product product) {
        return repo.save(product);
    }

    @Override
    public Product updateProduct(int id, UpdateProductRequest req) {
        Product existing = getProductById(id);
        existing.setName(req.getName());
        existing.setPrice(req.getPrice());
        Category cat = categoryRepository.findById(req.getCategoryId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Category not found"));
        existing.setCategory(cat);
        return repo.save(existing);
    }

    @Override
    public void deleteProduct(int id) {
        repo.deleteById(id);
    }
}
