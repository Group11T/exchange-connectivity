package com.io.t11.exchangeconnectivity.service;


import com.io.t11.exchangeconnectivity.dto.CreatedOrder;

public interface IOrderService {

    CreatedOrder updateOrderUniqueId(Long id, String uid);
}
