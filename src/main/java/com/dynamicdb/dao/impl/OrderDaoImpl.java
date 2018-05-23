package com.dynamicdb.dao.impl;

import com.dynamicdb.dao.OrderDao;
import com.dynamicdb.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class OrderDaoImpl implements OrderDao {


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Order select(Long id) {
        Order order = null;
        StringBuilder sql = new StringBuilder(" select order_id,order_no,`status`,create_time,modify_time from `order`  where order_id = ? ");
        List<Order> list = jdbcTemplate.query(sql.toString(),new BeanPropertyRowMapper<>(Order.class),id);
        if (list!=null&&list.size()>0){
            order = list.get(0);
        }
        return order;
    }

    @Override
    public List<Order> selectAll() {
        StringBuilder sql = new StringBuilder(" select order_id,order_no,`status`,create_time,modify_time from `order` ");
        return jdbcTemplate.query(sql.toString(),new BeanPropertyRowMapper<>(Order.class));
    }

    @Override
    public void insert(Order order) {
        StringBuilder s=new StringBuilder();
        s.append("insert into `order`(");
        Map<String, Object> m = map(order);
        s.append(String.join(",", m.keySet()));
        s.append(") values (");
        List<String> query = m.keySet().stream().map(t -> "?").collect(Collectors.toList());
        s.append(String.join(",", query));
        s.append(")");
        jdbcTemplate.update(s.toString(), m.values().toArray());
    }

    private static Map<String,Object> map(Order order) {
        Map<String,Object> map=new HashMap();
        if (order.getOrderId() != null) {
            map.put("order_id",order.getOrderId());
        }
        if (order.getOrderNo()!=null&&order.getOrderNo()!=""&&!"".equals(order.getOrderNo())) {
            map.put("order_no",order.getOrderNo());
        }
        if (order.getStatus() != null) {
            map.put("status",order.getStatus());
        }
        if (order.getCreateTime()!=null&&order.getCreateTime()!=""&&!"".equals(order.getCreateTime())) {
            map.put("create_time",order.getCreateTime());
        }
        if (order.getModifyTime()!=null&&order.getModifyTime()!=""&&!"".equals(order.getModifyTime())) {
            map.put("modify_time",order.getModifyTime());
        }
        return map;
    }
}
