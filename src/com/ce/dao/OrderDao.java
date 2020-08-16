package com.ce.dao;

import com.ce.domain.Member;
import com.ce.domain.Orders;
import com.ce.domain.Product;
import com.ce.domain.Traveller;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface OrderDao {

    @Select("select * from orders")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "orderNum", column = "orderNum"),
            @Result(property = "orderTime", column = "orderTime"),
            @Result(property = "orderStatus", column = "orderStatus"),
            @Result(property = "peopleCount", column = "peopleCount"),
            @Result(property = "orderNum", column = "orderNum"),
            @Result(property = "payType", column = "payType"),
            @Result(property = "orderDesc", column = "orderDesc"),
            @Result(property = "product", column = "productId", javaType = Product.class, one = @One(select = "com.ce.dao.ProductDao.findById")),
    })
    List<Orders> findAll();


    @Select("select * from orders where id=#{ordersId} limit 1")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "orderNum", column = "orderNum"),
            @Result(property = "orderTime", column = "orderTime"),
            @Result(property = "orderStatus", column = "orderStatus"),
            @Result(property = "peopleCount", column = "peopleCount"),
            @Result(property = "orderNum", column = "orderNum"),
            @Result(property = "payType", column = "payType"),
            @Result(property = "orderDesc", column = "orderDesc"),
            @Result(property = "product", column = "productId", javaType = Product.class, one = @One(select = "com.ce.dao.ProductDao.findById")),
            @Result(property = "member", column = "memberId", javaType = Member.class, one = @One(select = "com.ce.dao.MemberDao.findById")),
            @Result(property = "travellers", column = "id", javaType = List.class, many = @Many(select = "com.ce.dao.TravellerDao.findByOrdersId")),
    })
    Orders findById(String ordersId);
}
