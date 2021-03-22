package com.io.t11.exchangeconnectivity.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.io.t11.exchangeconnectivity.dto.OrderDto;

public interface IExchangeConnectivityPublisher {

    void publishToRegister(OrderDto orderDto) throws JsonProcessingException;

}
