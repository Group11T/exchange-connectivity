package com.io.t11.exchangeconnectivity.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.io.t11.exchangeconnectivity.model.StockDto;

public interface IExchangeConnectivityPublisher {

    void publishToRecords(StockDto stockDto) throws JsonProcessingException;

}
