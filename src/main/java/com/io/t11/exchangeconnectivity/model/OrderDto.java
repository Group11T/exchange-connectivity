package com.io.t11.exchangeconnectivity.model;

import java.util.HashMap;
import java.util.Map;

public class OrderDto {

    private String product;
    private double price;
    private String side;
    //Map of each exchange and the quantity to trade
    private Map<String,Integer> tradeDetails = new HashMap<>();

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

    public Map<String, Integer> getTradeDetails() {
        return tradeDetails;
    }

    public void setTradeDetails(Map<String, Integer> tradeDetails) {
        this.tradeDetails = tradeDetails;
    }

    @Override
    public String toString() {
        return "OrderDto{" +
                "product='" + product + '\'' +
                ", price=" + price +
                ", side='" + side + '\'' +
                ", tradeDetails=" + tradeDetails +
                '}';
    }
}
