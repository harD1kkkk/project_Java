package com.example.demo.service;

import com.example.demo.db.entity.Product;
import java.util.List;

public interface IProductService {
    List<Product> getAllProducts();
    Product getProductById(int id);
    Product createProduct(Product product);
    Product updateProduct(int id, Product product);
    void deleteProduct(int id);
}
