package com.ce.dao;

import com.ce.domain.Product;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ProductDao {
    /**
     * 查询所有产品信息
     */
    @Select("Select * from product")
    List<Product> findAll();

    /**
     * 添加产品
     */
    @Insert("insert into product(productNum,productName,cityName,departureTime,productPrice,productDesc,productStatus) values(#{productNum},#{productName},#{cityName},#{departureTime},#{productPrice},#{productDesc},#{productStatus})")
    void save(Product product);

    /**
     * 根据id查询产品
     */
    @Select("select * from product where id=#{id} limit 1")
    Product findById(String id);

}
