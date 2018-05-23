package com.dynamicdb.service.impl;

import com.dynamicdb.dao.OrderDao;
import com.dynamicdb.entity.Order;
import com.dynamicdb.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;

    @Override
    public Order select(Long id) {
        return orderDao.select(id);
    }

    @Override
    public List<Order> getAll() {
        return orderDao.selectAll();
    }

    @Override
    public void add(Order order) {
        orderDao.insert(order);
    }
}
