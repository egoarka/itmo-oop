package com.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.logging.Logger;

import com.DataSource;
import com.MyLogger;
import com.dao.ShopDao;
import com.entity.Product;
import com.entity.Shop;

public class ShopDaoImpl implements ShopDao {
  //@formatter:off
  private static final String SQL_INSERT = "INSERT INTO shops (name, address) VALUES (?, ?)";
  private static final String SQL_FIND_BY_ID = "SELECT * FROM shops where id = ?";
  private static final String SQL_FETCH_SHOP_PRODUCT = "SELECT * FROM shop_product WHERE shop_id = ? AND product_name = ?";
  private static final String SQL_FETCH_SHOP_PRODUCTS = "SELECT * FROM shop_product WHERE shop_id = ?";
  private static final String SQL_SHOP_HAVING_CHEAPEST_PRODUCT = 
    "SELECT shop_id FROM shop_product WHERE price = (" +
    "  SELECT min(price) FROM shop_product WHERE product_name = ?" +
    ")";
  private static final String SQL_INSERT_PRODUCT = 
    "INSERT INTO shop_product (shop_id, product_name, quantity, price)" +
    "  VALUES (?, ?, ?, ?)";

  private static final Function<Boolean, String> SQL_UPDATE_PRODUCT = (Boolean newPrice) ->
    "UPDATE shop_product SET" +
      "  quantity = quantity + ? " +
      (newPrice ?
      ",  price = ? " : " "
      ) +
    " WHERE shop_id = ? AND product_name = ?";
  //@formatter:on

  private static Logger logger = MyLogger
      .getLogger(ShopDaoImpl.class.getName());

  @Override
  public Optional<Long> insert(Shop shop) {
    Long shopId = null;
    //@formatter:off
    try (
      Connection connection = DataSource.getConnection();
      PreparedStatement ps = connection.prepareStatement(
        SQL_INSERT,
        Statement.RETURN_GENERATED_KEYS
      );
    ) {
    //@formatter:on

      ps.setString(1, shop.getName());
      ps.setString(2, shop.getAddress());
      int affectedRows = ps.executeUpdate();
      if (affectedRows == 0) {
        throw new SQLException("Inserting shop failed, no rows affected.");
      }
      ResultSet generatedkeys = ps.getGeneratedKeys();
      if (generatedkeys.next()) {
        shopId = generatedkeys.getLong(1);
      }
    } catch (SQLException e) {
      logger.severe(e.getMessage());
      // e.printStackTrace();
    }
    shop.setId(shopId);
    return Optional.ofNullable(shopId);
  }

  @Override
  public Optional<Long> havingCheapestProduct(Product product) {
    Long shopId = null;
    //@formatter:off
    try (
      Connection connection = DataSource.getConnection();
      PreparedStatement ps = connection.prepareStatement(
        SQL_SHOP_HAVING_CHEAPEST_PRODUCT
      );
    ) {
    //@formatter:on

      ps.setString(1, product.getName());

      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        shopId = rs.getLong("shop_id");
      }
    } catch (SQLException e) {
      logger.severe(e.getMessage());
      // e.printStackTrace();
    }
    return Optional.ofNullable(shopId);
  }

  @Override
  public Optional<Shop> findById(Long id) {
    Shop shop = null;
    //@formatter:off
    try (
      Connection connection = DataSource.getConnection();
      PreparedStatement ps = connection.prepareStatement(
        SQL_FIND_BY_ID
      );
    ) {
    //@formatter:on

      ps.setLong(1, id);
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        shop = new Shop();
        shop.setId(rs.getLong(Shop.ID_COLUMN));
        shop.setName(rs.getString(Shop.NAME_COLUMN));
        shop.setAddress(rs.getString(Shop.ADDRESS_COLUMN));
      }
    } catch (SQLException e) {
      logger.severe(e.getMessage());
      // e.printStackTrace();
    }
    return Optional.ofNullable(shop);
  }

  @Override
  public Optional<String> insertProduct(Shop shop, Product product) {
    //@formatter:off
    try (
      Connection connection = DataSource.getConnection();
      PreparedStatement ps = connection.prepareStatement(
        SQL_INSERT_PRODUCT
      );
    ) {
    //@formatter:on
      ps.setLong(1, shop.getId());
      ps.setString(2, product.getName());
      ps.setInt(3, product.getQuantity());
      ps.setInt(4, product.getPrice());

      int affectedRows = ps.executeUpdate();
      if (affectedRows == 1) {
        return Optional.of(product.getName());
      }

    } catch (SQLException e) {
      logger.severe(e.getMessage());
      // e.printStackTrace();
    }
    return Optional.ofNullable(null);
  }

  @Override
  public Optional<String> updateProduct(Shop shop, Product product,
      Boolean newPrice) {

    //@formatter:off
    try (
      Connection connection = DataSource.getConnection();
      PreparedStatement ps = connection.prepareStatement(
        SQL_UPDATE_PRODUCT.apply(newPrice)
      );
    ) {
    //@formatter:on
      // to align query fields
      Integer fooPlus = newPrice ? 0 : -1;
      ps.setInt(1, product.getQuantity());
      if (newPrice) {
        ps.setInt(2, product.getPrice());
      }
      ps.setLong(3 + fooPlus, shop.getId());
      ps.setString(4 + fooPlus, product.getName());
      int affectedRows = ps.executeUpdate();
      if (affectedRows == 1) {
        return Optional.of(product.getName());
      }

    } catch (SQLException e) {
      logger.severe(e.getMessage());
      // e.printStackTrace();
    }
    return Optional.ofNullable(null);
  }

  @Override
  public List<Product> fetchProducts(Shop shop) {
    ArrayList<Product> products = new ArrayList<>();
    //@formatter:off
    try (
      Connection connection = DataSource.getConnection();
      PreparedStatement ps = connection.prepareStatement(
        SQL_FETCH_SHOP_PRODUCTS
      );
    ) {
    //@formatter:on
      ps.setLong(1, shop.getId());
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        Product product = new Product();
        product.setName(rs.getString("product_name"));
        product.setPrice(rs.getInt("price"));
        product.setQuantity(rs.getInt("quantity"));
        products.add(product);
      }
    } catch (SQLException e) {
      logger.severe(e.getMessage());
      // e.printStackTrace();
    }
    return products;
  }

  @Override
  public Optional<Product> fetchProduct(Shop shop, Product product) {
    Product newProduct = null;
    //@formatter:off
    try (
      Connection connection = DataSource.getConnection();
      PreparedStatement ps = connection.prepareStatement(
        SQL_FETCH_SHOP_PRODUCT
      );
    ) {
    //@formatter:on
      ps.setLong(1, shop.getId());
      ps.setString(2, product.getName());
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        newProduct = new Product();
        newProduct.setName(rs.getString("product_name"));
        newProduct.setPrice(rs.getInt("price"));
        newProduct.setQuantity(rs.getInt("quantity"));
      }
    } catch (SQLException e) {
      logger.severe(e.getMessage());
      // e.printStackTrace();
    }
    return Optional.ofNullable(newProduct);
  }
}