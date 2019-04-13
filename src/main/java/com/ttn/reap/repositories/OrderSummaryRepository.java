package com.ttn.reap.repositories;

import com.ttn.reap.entities.Item;
import com.ttn.reap.entities.OrderSummary;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderSummaryRepository extends CrudRepository<OrderSummary, Integer> {
    OrderSummary save(OrderSummary orderSummary);

    List<OrderSummary> findAll();

    List<OrderSummary> findByUserId(Integer id);
}
