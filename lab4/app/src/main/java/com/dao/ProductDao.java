package com.dao;

import java.util.Optional;

import com.entity.Product;

public interface ProductDao extends AutoCloseable {
  // I wanted to implement batching but forgive me
  // I don't have enough time
  public Optional<String> insert(Product product);

  public Optional<Product> findByName(String name);
}