package com.io.t11.exchangeconnectivity.service;

import com.io.t11.exchangeconnectivity.dao.CreatedOrderRepository;
import com.io.t11.exchangeconnectivity.dto.CreatedOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderService implements IOrderService {

    @Autowired
    CreatedOrderRepository orderRepository;

    public OrderService(CreatedOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public CreatedOrder updateOrderUniqueId(Long id, String uid) {
        Optional<CreatedOrder> createdOrder = orderRepository.findById(id);
        createdOrder.ifPresent(order->{
            order.setUniqueOrderId(uid);
            orderRepository.save(order);
        });
        return createdOrder.get();
    }

}
