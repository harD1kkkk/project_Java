package com.example.demo.controller;

import com.example.demo.db.entity.Order;
import com.example.demo.service.IOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@Slf4j
@RequiredArgsConstructor
public class OrderRestController {
    private final IOrderService orderService;

    @GetMapping
    public List<Order> getAllOrders() {
        log.info("GET /api/v1/orders");
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable int id) {
        log.info("GET /api/v1/orders/{}", id);
        return orderService.getOrderById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Order createOrder(@RequestBody Order order) {
        log.info("POST /api/v1/orders payload={}", order);
        return orderService.createOrder(order);
    }

    @PutMapping("/{id}")
    public Order updateOrder(@PathVariable int id, @RequestBody Order order) {
        log.info("PUT /api/v1/orders/{} payload={}", id, order);
        return orderService.updateOrder(id, order);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable int id) {
        log.info("DELETE /api/v1/orders/{}", id);
        orderService.deleteOrder(id);
    }
}
