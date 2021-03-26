package com.io.t11.exchangeconnectivity.model;

public enum ExchangeDetails {

    EXCHANGE_1("exchange_1","https://exchange.matraining.com/22ad2f56-d075-46c9-9186-fc7d57a1ed6a/order"),
    EXCHANGE_2("exchange_2","https://exchange.matraining.com/22ad2f56-d075-46c9-9186-fc7d57a1ed6a/order");

    private final String exchangeName;
    private final String url;

    ExchangeDetails(String exchangeName,String url) {
        this.exchangeName = exchangeName;
        this.url = url;
    }

    public String getExchangeName() {
        return exchangeName;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "ExchangeDetails{" +
                "exchangeName='" + exchangeName + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
