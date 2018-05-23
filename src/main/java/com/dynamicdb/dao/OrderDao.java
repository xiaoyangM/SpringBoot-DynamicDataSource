package com.dynamicdb.dao;

import com.dynamicdb.entity.Order;

import java.util.List;

public interface OrderDao {

    Order select(Long id);

    List<Order> selectAll();

    void insert(Order order);
}
