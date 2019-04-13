package com.ttn.reap.services;

import com.ttn.reap.entities.Item;
import com.ttn.reap.entities.OrderSummary;
import com.ttn.reap.repositories.OrderSummaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderSummaryService {
    @Autowired
    OrderSummaryRepository orderSummaryRepository;

    public OrderSummary save(OrderSummary orderSummary) {
        return orderSummaryRepository.save(orderSummary);
    }

    public List<OrderSummary> getAllOrders() {
        return orderSummaryRepository.findAll();
    }

    public List<OrderSummary> getAllOrdersByUserId(Integer id) {
        return orderSummaryRepository.findByUserId(id);
    }
}
