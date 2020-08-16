package com.ce.service.impl;

import com.ce.dao.OrderDao;
import com.ce.domain.Orders;
import com.ce.service.OrderService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Override
    public List<Orders> findAll(int page, int size) {
        // 参数pageNum 页码值  pageSize 每页显示条数
        PageHelper.startPage(page, size);
        return orderDao.findAll();
    }

    @Override
    public Orders findById(String ordersId) {
        return orderDao.findById(ordersId);
    }
}
