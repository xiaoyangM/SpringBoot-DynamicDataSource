package com.dynamicdb.service;

import com.dynamicdb.entity.Order;

import java.util.List;

public interface OrderService {

    /**
     * 查询订单对象
     * */
    Order select(Long id);

    /**
     * 查询所有对象
     * */
    List<Order> getAll();

    /**
     * 新增对象
     * */
    void add(Order order);
}
