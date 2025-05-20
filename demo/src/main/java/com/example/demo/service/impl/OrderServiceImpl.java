package com.example.demo.service.impl;

import com.example.demo.db.entity.Order;
import com.example.demo.repository.IOrderRepository;
import com.example.demo.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements IOrderService {
    private final IOrderRepository repo;

    @Override
    public List<Order> getAllOrders() {
        return repo.findAll();
    }

    @Override
    public Order getOrderById(int id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Order not found: " + id));
    }

    @Override
    public Order createOrder(Order order) {
        return repo.save(order);
    }

    @Override
    public Order updateOrder(int id, Order order) {
        Order existing = getOrderById(id);
        existing.setProduct(order.getProduct());
        existing.setUser(order.getUser());
        existing.setQuantity(order.getQuantity());
        return repo.save(existing);
    }

    @Override
    public void deleteOrder(int id) {
        repo.deleteById(id);
    }
}
