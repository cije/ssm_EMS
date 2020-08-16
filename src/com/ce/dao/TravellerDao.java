package com.ce.dao;

import com.ce.domain.Traveller;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TravellerDao {

    /**
     * 根据orderId查询traveller信息
     *
     * @param ordersId ordersId
     * @return
     */
    @Select("SELECT * FROM traveller WHERE id IN (SELECT travellerId FROM order_traveller WHERE orderId =#{ordersId})")
    List<Traveller> findByOrdersId(String ordersId);
}
