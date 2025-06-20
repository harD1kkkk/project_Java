package com.example.demo.service.impl;

import com.example.demo.db.entity.Order;
import com.example.demo.repository.IOrderRepository;
import org.junit.jupiter.api.*;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceImplTest {

    @Mock private IOrderRepository repo;
    @InjectMocks private OrderServiceImpl service;
    private AutoCloseable mocks;

    @BeforeEach void setUp() { mocks = MockitoAnnotations.openMocks(this); }
    @AfterEach void tearDown() throws Exception { mocks.close(); }

    @Test void getAllOrders() {
        var o = new Order(1, null, null, null, 1);
        when(repo.findAll()).thenReturn(List.of(o));
        assertEquals(1, service.getAllOrders().size());
    }

    @Test void getOrderById_found() {
        var o = new Order(1, null, null, null, 1);
        when(repo.findById(1)).thenReturn(Optional.of(o));
        assertEquals(1, service.getOrderById(1).getId());
    }

    @Test void createOrder() {
        var o = new Order(0, null, null, null, 3);
        when(repo.save(o)).thenReturn(o);
        assertSame(o, service.createOrder(o));
    }

    @Test void updateOrder() {
        var old = new Order(1, null, null, null, 1);
        var updData = new Order(0, null, null, null, 5);
        when(repo.findById(1)).thenReturn(Optional.of(old));
        when(repo.save(any())).thenAnswer(i->i.getArgument(0));
        var result = service.updateOrder(1, updData);
        assertEquals(5, result.getQuantity());
    }

    @Test void deleteOrder() {
        doNothing().when(repo).deleteById(7);
        service.deleteOrder(7);
        verify(repo).deleteById(7);
    }
}
