package com.example.demo.service.impl;

import com.example.demo.db.entity.Category;
import com.example.demo.repository.ICategoryRepository;
import com.example.demo.service.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements ICategoryService {
    private final ICategoryRepository repo;

    @Override
    public List<Category> getAllCategories() {
        return repo.findAll();
    }

    @Override
    public Category getCategoryById(int id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Category not found: " + id));
    }

    @Override
    public Category createCategory(Category category) {
        return repo.save(category);
    }

    @Override
    public Category updateCategory(int id, Category category) {
        Category existing = getCategoryById(id);
        existing.setName(category.getName());
        return repo.save(existing);
    }

    @Override
    public void deleteCategory(int id) {
        Category cat = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found: " + id));
        repo.delete(cat);
    }

}
