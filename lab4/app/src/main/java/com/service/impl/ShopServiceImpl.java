package com.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.MyLogger;
import com.dao.ShopDao;
import com.dao.impl.ShopDaoImpl;
import com.entity.Product;
import com.entity.Shop;
import com.service.ShopService;

public class ShopServiceImpl implements ShopService {
  private static Logger logger = MyLogger
      .getLogger(ShopDaoImpl.class.getName());

  @Override
  public List<Product> possibleToBuyProductsOn(Shop shop,
      Integer amountOfMoney) {
    List<Product> products = new ArrayList<>();
    try (ShopDao shopDao = new ShopDaoImpl()) {
      //@formatter:off
      products = shopDao
        .fetchProducts(shop)
        .stream()
        .map(p -> {
          Integer actualQuantity = p.getQuantity();
          Integer possibleQuantity = Math.floorDiv(amountOfMoney, p.getPrice());
          Integer resultQuantity = Math.min(actualQuantity, possibleQuantity); 
          p.setQuantity(resultQuantity);
          return p;
        })
        // .filter(p -> p.getQuantity() > 0)
        .collect(Collectors.toList());
      //@formatter:on
    } catch (Exception e) {
      logger.severe(e.getMessage());
    }
    return products;
  }

  @Override
  public List<Product> deliverToShop(Shop shop, List<Product> products,
      Boolean newPrice) {
    return null;
  }

  @Override
  public Optional<Integer> buyProduct(Shop shop, Product product) {
    return null;
  }

  @Override
  public Optional<Shop> whereToBuyAtProfit(List<Product> products) {
    return null;
  }

}