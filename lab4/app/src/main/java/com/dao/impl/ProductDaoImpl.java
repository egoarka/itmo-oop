package com.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Logger;

import com.DataSource;
import com.MyLogger;
import com.dao.ProductDao;
import com.entity.Product;

public class ProductDaoImpl implements ProductDao {
  private static final String SQL_INSERT = "INSERT INTO products (name) VALUES (?)";
  private static final String SQL_FIND_BY_NAME = "SELECT * FROM products where name = ?";

  private static Logger logger = MyLogger
      .getLogger(ProductDaoImpl.class.getName());

  @Override
  public Optional<String> insert(Product product) {
    //@formatter:off
    try (
      Connection connection = DataSource.getConnection();
      PreparedStatement ps = connection.prepareStatement(
        SQL_INSERT
      );
    ) {
    //@formatter:on

      ps.setString(1, product.getName());
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
  public Optional<Product> findByName(String name) {
    Product product = null;
    //@formatter:off
    try (
      Connection connection = DataSource.getConnection();
      PreparedStatement ps = connection.prepareStatement(
        SQL_FIND_BY_NAME
      );
    ) {
    //@formatter:on

      ps.setString(1, name);
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        product = new Product();
        product.setName(rs.getString(Product.ID_COLUMN));
      }
    } catch (SQLException e) {
      logger.severe(e.getMessage());
      // e.printStackTrace();
    }
    return Optional.ofNullable(product);
  }

}