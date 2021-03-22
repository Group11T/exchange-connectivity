package com.io.t11.exchangeconnectivity.dto;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="created_orders")
public class CreatedOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id;

    private String product;

    private int quantity;

    private double price;

    private String side;

    private String validationStatus;

    private String uniqueOrderId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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

    public String getValidationStatus() {
        return validationStatus;
    }

    public void setValidationStatus(String validationStatus) {
        this.validationStatus = validationStatus;
    }

    public String getUniqueOrderId() {
        return uniqueOrderId;
    }

    public void setUniqueOrderId(String uniqueOrderId) {
        this.uniqueOrderId = uniqueOrderId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreatedOrder that = (CreatedOrder) o;
        return quantity == that.quantity &&
                Double.compare(that.price, price) == 0 &&
                Objects.equals(id, that.id) &&
                Objects.equals(product, that.product) &&
                Objects.equals(side, that.side) &&
                Objects.equals(validationStatus, that.validationStatus) &&
                Objects.equals(uniqueOrderId, that.uniqueOrderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, product, quantity, price, side, validationStatus, uniqueOrderId);
    }

    @Override
    public String toString() {
        return "CreatedOrder{" +
                "id=" + id +
                ", product='" + product + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", side='" + side + '\'' +
                ", validationStatus='" + validationStatus + '\'' +
                ", uniqueOrderId='" + uniqueOrderId + '\'' +
                '}';
    }
}
