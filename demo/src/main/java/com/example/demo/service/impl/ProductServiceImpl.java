package com.example.demo.service.impl;

import com.example.demo.db.entity.Product;
import com.example.demo.repository.IProductRepository;
import com.example.demo.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements IProductService {
    private final IProductRepository repo;

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
    public Product updateProduct(int id, Product product) {
        Product existing = getProductById(id);
        existing.setName(product.getName());
        existing.setPrice(product.getPrice());
        existing.setCategory(product.getCategory());
        return repo.save(existing);
    }

    @Override
    public void deleteProduct(int id) {
        repo.deleteById(id);
    }
}
