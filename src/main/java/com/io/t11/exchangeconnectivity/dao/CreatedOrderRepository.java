package com.io.t11.exchangeconnectivity.dao;

import com.io.t11.exchangeconnectivity.dto.CreatedOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreatedOrderRepository extends JpaRepository<CreatedOrder,Long> {
}
