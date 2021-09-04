package com.meru.ecommerce.price.priceservice.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.NumberFormat;

@Entity
@Table(name = "Price")
public class Price implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int priceId;
    @Column
    private int productId;
    @Column
    private String productName;
    @Column
    @NumberFormat(pattern = "#,###,###,###.##")
    private double price;

    public Price() {
    }

    public Price(int productId, String productName, double price) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
    }

    public Price(int priceId, int productId, String productName, double price) {
        this.priceId = priceId;
        this.productId = productId;
        this.productName = productName;
        this.price = price;
    }

    public int getPriceId() {
        return priceId;
    }

    public void setPriceId(int priceId) {
        this.priceId = priceId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Price [priceId=" + priceId + ", productId=" + productId + ", productName=" + productName + ", price="
                + price + "]";
    }
}
