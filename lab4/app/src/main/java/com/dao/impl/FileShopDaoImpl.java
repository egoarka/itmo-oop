package com.dao.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.FileDataSource;
import com.dao.ShopDao;
import com.entity.Product;
import com.entity.Shop;

import org.javatuples.Pair;

public class FileShopDaoImpl implements ShopDao {
  @Override
  public void close() throws Exception {

  }

  @Override
  public Optional<Long> insert(Shop shop) {
    return null;
  }

  @Override
  public Optional<Long> havingCheapestProduct(Product product) {
    return null;
  }

  @Override
  public List<Pair<Integer, Product>> shopsHavingAnyOfTheseProducts(
      List<Product> products) {
    return null;
  }

  @Override
  public Optional<Shop> findById(Long id) {
    return FileDataSource.getShopsStream()
        .map(line -> Arrays.asList(line.split(",")))
        .map(
            csv -> new Shop(Long.parseLong(csv.get(0)), csv.get(1), csv.get(2)))
        .filter(shop -> shop.getId().equals(id))
        .findAny();
  }

  @Override
  public Optional<String> insertProduct(Shop shop, Product product) {

    return null;
  }

  @Override
  public Optional<String> updateProduct(Shop shop, Product product,
      Boolean newPrice) {
    return null;
  }

  @Override
  public List<Product> fetchProducts(Shop shop) {
    return null;
  }

  @Override
  public Optional<Product> fetchProduct(Shop shop, Product product) {
    return null;
  }

}