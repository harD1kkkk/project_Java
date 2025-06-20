package com.example.demo.service.impl;

import com.example.demo.db.entity.Category;
import com.example.demo.db.entity.Product;
import com.example.demo.dto.UpdateProductRequest;
import com.example.demo.repository.ICategoryRepository;
import com.example.demo.repository.IProductRepository;
import org.junit.jupiter.api.*;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {

    @Mock private IProductRepository repo;
    @Mock private ICategoryRepository categoryRepo;
    @InjectMocks private ProductServiceImpl service;
    private AutoCloseable mocks;

    @BeforeEach void init() { mocks = MockitoAnnotations.openMocks(this); }
    @AfterEach void close() throws Exception { mocks.close(); }

    @Test void getAllProducts() {
        var p = new Product(1,"P",10.0,null);
        when(repo.findAll()).thenReturn(List.of(p));
        assertEquals(1, service.getAllProducts().size());
    }

    @Test void getProductById_found() {
        var p = new Product(1,"P",10.0,null);
        when(repo.findById(1)).thenReturn(Optional.of(p));
        assertEquals("P", service.getProductById(1).getName());
    }

    @Test void createProduct() {
        var p = new Product(0,"X",5.0,null);
        when(repo.save(p)).thenReturn(p);
        assertSame(p, service.createProduct(p));
    }

    @Test void updateProduct() {
        Category cat = new Category(1, "Books", null);
        Product old = new Product(1, "A", 1.0, cat);

        when(repo.findById(1)).thenReturn(Optional.of(old));
        when(categoryRepo.findById(1)).thenReturn(Optional.of(cat));
        when(repo.save(any(Product.class))).thenAnswer(inv -> inv.getArgument(0));

        UpdateProductRequest req = new UpdateProductRequest("B", 2.0, 1);

        Product updated = service.updateProduct(1, req);

        assertEquals("B", updated.getName());
        assertEquals(2.0, updated.getPrice());
        assertSame(cat, updated.getCategory());

        verify(repo).save(updated);
    }

    @Test void deleteProduct() {
        doNothing().when(repo).deleteById(4);
        service.deleteProduct(4);
        verify(repo).deleteById(4);
    }
}
