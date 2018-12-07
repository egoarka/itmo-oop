package com.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.MyLogger;
import com.dao.ProductDao;
import com.dao.ShopDao;
import com.dao.impl.ProductDaoImpl;
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
          return new Product(p.getName(), resultQuantity);
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
    List<Product> result = new ArrayList<>();
    //@formatter:off
    try (
      ShopDao shopDao = new ShopDaoImpl();
      ProductDao productDao = new ProductDaoImpl();
    ) {
      Set<String> shopProducts = shopDao.fetchProducts(shop)
        .stream()
        .map(Product::getName)
        .collect(Collectors.toSet());
      
        result = products.stream()
          .map(p -> {
            Boolean inShop = shopProducts.contains(p.getName());
            if (inShop) {
              return shopDao.updateProduct(shop, p, newPrice);
            } else {
              return productDao
                // check if product exists
                .findByName(p.getName())
                .map(Product::getName)
                // if product doesn't exists in our db
                // we insert it
                .or(() -> productDao
                  .insert(p)
                  // and futher we insert it to shop
                  .flatMap(product -> shopDao.insertProduct(shop, p))
                );
            }
          })
          .flatMap(Optional::stream)
          .map(Product::new)
          .map(p -> shopDao.fetchProduct(shop, p))
          .flatMap(Optional::stream)
          .collect(Collectors.toList());
    } catch (Exception e) {
      logger.severe(e.getMessage());
    }
    return result;
  }

  @Override
  public Optional<Integer> buyProduct(Shop shop, Product product) {
 
    Optional<Integer> totalPurchaseAmount = Optional.empty();
    //@formatter:off
    try (
      ShopDao shopDao = new ShopDaoImpl();
      ProductDao productDao = new ProductDaoImpl();
    ) {
      totalPurchaseAmount = Optional
        .of(product)
        // check if this product exists
        .flatMap(p -> productDao.findByName(p.getName()))
        // and whether it exists in shop
        .flatMap(p -> shopDao.fetchProduct(shop, p))
        // and if enough quantity in shop
        .filter(p -> p.getQuantity() >= product.getQuantity())
        // if everything is ok
        .flatMap((p) -> {
          Integer qty = Math.abs(product.getQuantity());
          Product wantToBuy = new Product(
            product.getName(),
            // (!!!) we're negate qty
            // because update product method
            // will decrease qty this way
            // not ok but ok :)
            Math.negateExact(qty)
          );
          return shopDao.updateProduct(
            shop, 
            wantToBuy,
            false
          )
          // quantity multiple by recent derived product from shop
          .map(($) -> qty * p.getPrice());
        });

    //@formatter:on
    } catch (Exception e) {
      logger.severe(e.getMessage());
    }
    return totalPurchaseAmount;
  }

  @Override
  public Optional<Shop> whereToBuyAtProfit(List<Product> products) {
    Optional<Shop> shop = Optional.empty();
    //@formatter:off
    try (
      ShopDao shopDao = new ShopDaoImpl();
      ProductDao productDao = new ProductDaoImpl();
    ) {
     Map<String, Integer> byProductName = products
      .stream()
      .collect(
        Collectors.toMap(
          product -> product.getName(),
          product -> product.getQuantity()
        )
      );
      Map<Integer, List<Product>> byShopId = shopDao
        .shopsHavingAnyOfTheseProducts(products)
        .stream()
        .collect(
          Collectors.groupingBy(
            pair -> pair.getValue0(),
            Collectors.mapping(pair -> pair.getValue1(), Collectors.toList())
          )
        );

      // shopId, amount 
      Map<Integer, Integer> possible = byShopId.entrySet().stream()
        // if query result product quantity ge than input product quantity
        // and if filtered size eq to input's one
        // then it satisfies our requirements
        .filter(entry ->
          entry.getValue().stream()
            .filter(product -> 
              product.getQuantity() >= byProductName.get(product.getName())
            ).count() == products.size()
        )
        // in end we get amount of:
        // products multiplied by input quantity
        .collect(
          Collectors.toMap(
            e -> e.getKey(),
            e -> e.getValue().stream()
              .map(product -> 
                byProductName.get(product.getName()) * product.getPrice()
              ).reduce(0, Integer::sum)
          )
        );

      // look for min value among others
      shop = 
        possible.entrySet().stream()
          .min((a, b) -> a.getValue().compareTo(b.getValue()))
          .flatMap(entry -> shopDao.findById(
            Long.valueOf(entry.getKey())
          ));

      System.out.println("---");
    } catch (Exception e) {
      logger.severe(e.getMessage());
    }

    return shop;
  }

}