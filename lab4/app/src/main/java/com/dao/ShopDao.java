package com.dao;

import java.util.List;
import java.util.Optional;

import com.entity.Product;
import com.entity.Shop;

public interface ShopDao {
        public Optional<Long> insert(Shop shop);

        public Optional<Long> havingCheapestProduct(Product product);

        public Optional<Shop> findById(Long id);

        public Optional<String> insertProduct(Shop shop, Product product);

        public Optional<String> updateProduct(Shop shop, Product product,
                        Boolean newPrice);

        public List<Product> fetchProducts(Shop shop);

        public Optional<Product> fetchProduct(Shop shop, Product product);
}