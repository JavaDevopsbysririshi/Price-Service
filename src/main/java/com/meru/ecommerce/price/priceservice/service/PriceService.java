package com.meru.ecommerce.price.priceservice.service;

import java.util.List;

import com.meru.ecommerce.price.priceservice.entity.Price;

public interface PriceService {
    public List<Price> getAllProductsPrice();

    public Price getPriceById(int priceId);

    public Price getPriceByProductId(int productId);

    public boolean createOrUpdatePrice(Price price);

    public boolean removePrice(int priceId);
}
