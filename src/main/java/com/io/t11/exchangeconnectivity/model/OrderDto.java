package com.io.t11.exchangeconnectivity.model;

import java.util.HashMap;
import java.util.Map;

public class OrderDto {

    private String product;
    private double price;
    private String side;
    //Map of each exchange and the quantity to trade
    private Map<String,Integer> exchangeNumber1 = new HashMap<>();
    private Map<String,Integer> exchangeNumber2 = new HashMap<>();

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public Map<String, Integer> getExchangeNumber1() {
        return exchangeNumber1;
    }

    public void setExchangeNumber1(Map<String, Integer> exchangeNumber1) {
        this.exchangeNumber1 = exchangeNumber1;
    }

    public Map<String, Integer> getExchangeNumber2() {
        return exchangeNumber2;
    }

    public void setExchangeNumber2(Map<String, Integer> exchangeNumber2) {
        this.exchangeNumber2 = exchangeNumber2;
    }

    @Override
    public String toString() {
        return "OrderDto{" +
                "product='" + product + '\'' +
                ", price=" + price +
                ", side='" + side + '\'' +
                ", exchangeNumber1=" + exchangeNumber1 +
                ", exchangeNumber2=" + exchangeNumber2 +
                '}';
    }
}
