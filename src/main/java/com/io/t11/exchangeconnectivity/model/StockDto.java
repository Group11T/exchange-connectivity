package com.io.t11.exchangeconnectivity.model;

public class StockDto {

    private Long orderid;

    private String uniqueId;

    private String exchangeTradedOn;

    private Long userId;

    public Long getOrderid() {
        return orderid;
    }

    public void setOrderid(Long orderid) {
        this.orderid = orderid;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getExchangeTradedOn() {
        return exchangeTradedOn;
    }

    public void setExchangeTradedOn(String exchangeTradedOn) {
        this.exchangeTradedOn = exchangeTradedOn;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "StockDto{" +
                "orderid=" + orderid +
                ", uniqueId='" + uniqueId + '\'' +
                ", exchangeTradedOn='" + exchangeTradedOn + '\'' +
                ", userId=" + userId +
                '}';
    }
}
