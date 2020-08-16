package com.ce.controller;

import com.ce.domain.Orders;
import com.ce.service.OrderService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 查询全部订单 分页
     */
    @RequestMapping("/findAll.do")
    // @Secured("ROLE_ADMIN")
    public ModelAndView findAll(@RequestParam(name = "page", required = true, defaultValue = "1") Integer page, @RequestParam(name = "size", required = true, defaultValue = "4") Integer size) {
        ModelAndView modelAndView = new ModelAndView("orders-page-list");
        List<Orders> ordersList = orderService.findAll(page, size);
        PageInfo<Orders> pageInfo = new PageInfo<>(ordersList);
        modelAndView.addObject("pageInfo", pageInfo);
        return modelAndView;
    }

    @RequestMapping("/findById.do")
    public ModelAndView findById(@RequestParam(name = "id", required = true) String ordersId) {
        ModelAndView modelAndView = new ModelAndView("orders-show");
        Orders orders = orderService.findById(ordersId);
        modelAndView.addObject("orders", orders);
        return modelAndView;
    }
}
