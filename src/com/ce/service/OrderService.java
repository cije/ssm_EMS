package com.ce.service;

import com.ce.domain.Orders;

import java.util.List;

public interface OrderService {

    List<Orders> findAll(int page,int size);

    Orders findById(String ordersId);
}
