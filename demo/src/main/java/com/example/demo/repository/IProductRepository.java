package com.example.demo.repository;

import com.example.demo.db.entity.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductRepository extends JpaRepository<Product, Integer> {
    @Modifying
    @Transactional
    void deleteAllByCategoryId(int categoryId);
}
