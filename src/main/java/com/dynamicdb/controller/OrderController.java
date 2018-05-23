package com.dynamicdb.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.dynamicdb.common.CommonResponse;
import com.dynamicdb.entity.Order;
import com.dynamicdb.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 订单
 * */

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 查询单一对象
     * */
    @RequestMapping(value = "/getOrder")
    public CommonResponse getOrder(@RequestParam(value = "id",required = false,defaultValue = "") Long id){
        Order order = orderService.select(id);
        return new CommonResponse(order,200);
    }

    /**
     * 查询所有对象
     * */
    @RequestMapping(value = "/getAll")
    public CommonResponse getAll(){
        List<Order> list = orderService.getAll();
        return new CommonResponse(list,200);
    }

    /**
     * 新增对象
     * */
    @RequestMapping(value = "/add")
    public Integer add(Order order){
        if (order == null){
            return  0;
        }
        order.setCreateTime(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        order.setModifyTime(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        orderService.add(order);

        return CommonResponse.SUCCESSCODE;
    }
}
