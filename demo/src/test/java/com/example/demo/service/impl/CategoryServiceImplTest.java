package com.example.demo.service.impl;

import com.example.demo.db.entity.Category;
import com.example.demo.repository.ICategoryRepository;
import com.example.demo.repository.IProductRepository;
import org.junit.jupiter.api.*;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryServiceImplTest {

    @Mock private ICategoryRepository catRepo;
    @Mock private IProductRepository prodRepo;
    @InjectMocks private CategoryServiceImpl service;
    private AutoCloseable mocks;

    @BeforeEach void setUp() { mocks = MockitoAnnotations.openMocks(this); }
    @AfterEach void tearDown() throws Exception { mocks.close(); }

    @Test void getAllCategories() {
        var c = new Category(1,"C",null);
        when(catRepo.findAll()).thenReturn(List.of(c));
        assertEquals(1, service.getAllCategories().size());
    }

    @Test void getCategoryById_found() {
        var c = new Category(1,"C",null);
        when(catRepo.findById(1)).thenReturn(Optional.of(c));
        assertEquals("C", service.getCategoryById(1).getName());
    }

    @Test void createCategory() {
        var c = new Category(0,"New",null);
        when(catRepo.save(c)).thenReturn(c);
        assertSame(c, service.createCategory(c));
    }

    @Test void updateCategory() {
        var old = new Category(1,"A",null);
        when(catRepo.findById(1)).thenReturn(Optional.of(old));
        when(catRepo.save(any())).thenAnswer(i->i.getArgument(0));
        var upd = service.updateCategory(1, new Category(0,"B",null));
        assertEquals("B", upd.getName());
    }

    @Test void deleteCategory() {
        int categoryId = 2;
        Category fakeCategory = new Category();
        fakeCategory.setId(categoryId);
        fakeCategory.setName("TestCat");
        fakeCategory.setProducts(null);
        when(catRepo.findById(categoryId))
                .thenReturn(Optional.of(fakeCategory));
        doNothing().when(catRepo).delete(fakeCategory);
        service.deleteCategory(categoryId);
        verify(catRepo).delete(fakeCategory);
        verifyNoInteractions(prodRepo);
    }
}
