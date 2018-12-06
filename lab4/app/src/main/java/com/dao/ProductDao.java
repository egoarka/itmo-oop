package com.dao;

import java.util.Optional;

import com.entity.Product;

public interface ProductDao {
  // I wanted to implement batching but forgive me
  // I don't have enough time
  public Optional<String> insert(Product product);

  public Optional<Product> findByName(String name);

  /*
   * public List<Product> findAll();
   * 
   * public Product findById(Long id);
   * 
   * public void insert(Product item);
   * 
   * public void update(Product item);
   * 
   * public void delete(Product item);
   */
}