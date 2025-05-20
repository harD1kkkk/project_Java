package com.example.demo.service;

import com.example.demo.db.entity.Order;
import java.util.List;

public interface IOrderService {
    List<Order> getAllOrders();
    Order getOrderById(int id);
    Order createOrder(Order order);
    Order updateOrder(int id, Order order);
    void deleteOrder(int id);
}
