package com.dynamicdb.entity;

import lombok.Data;

@Data
public class Order {
    //主键ID
    private Long orderId;
    //订单号
    private String orderNo;
    //状态
    private Integer status;
    //创建时间
    private String createTime;
    //更新时间
    private String modifyTime;
}
