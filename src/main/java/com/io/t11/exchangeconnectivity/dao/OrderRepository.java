package com.io.t11.exchangeconnectivity.dao;

import com.io.t11.exchangeconnectivity.dto.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
