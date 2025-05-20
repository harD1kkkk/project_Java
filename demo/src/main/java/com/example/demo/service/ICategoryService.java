package com.example.demo.service;

import com.example.demo.db.entity.Category;
import java.util.List;

public interface ICategoryService {
    List<Category> getAllCategories();
    Category getCategoryById(int id);
    Category createCategory(Category category);
    Category updateCategory(int id, Category category);
    void deleteCategory(int id);
}
