package com.service;

import java.util.List;
import java.util.Optional;

import com.entity.Product;
import com.entity.Shop;

public interface ShopService {
  List<Product> possibleToBuyProductsOn(Shop shop, Integer amountOfMoney);

  // it's possible to make specific price for each product
  // but for educational purposes it's enough though
  List<Product> deliverToShop(Shop shop, List<Product> products,
      Boolean newPrice);

  Optional<Integer> buyProduct(Shop shop, Product product);

  Optional<Shop> whereToBuyAtProfit(List<Product> products);
}