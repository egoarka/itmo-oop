package com;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

import com.dao.ProductDao;
import com.dao.impl.ProductDaoImpl;
import com.entity.Product;
import com.scripts.DBScripts;

import org.junit.Before;
import org.junit.Test;

// import com.github.BenoitDuffez;

/**
 * Unit test for simple App.
 */
public class SimpleJdbcTest {
  // private ProductDao productDao = new ProductDaoImpl();
  private ProductDao productDao = new ProductDaoImpl();

  @Before
  public void init() {
    // recommends to run this
    // if you didn't run test yet

    DBScripts.dropTables();
    DBScripts.createTables();
    DBScripts.fillDB();

    // run this if you already created tables
    // DBScripts.deleteTablesRows();
    // DBScripts.fillDB();
  }

  @Test
  public void testProductDAO() {
    // create new product
    Product product = new Product("codeine");
    Optional<String> newProduct = productDao.insert(product);
    assertEquals(product.getName(), newProduct.get());

    // catch dublicate key
    // repeat create to make sure everything is ok
    Optional<String> dublicateProduct = productDao.insert(product);
    assertEquals(true, dublicateProduct.isEmpty());

    // query for new product
    // and check whether it exists or not
    Optional<Product> productFromDB = productDao.findByName(newProduct.get());
    assertEquals(product.getName(), productFromDB.get().getName());

    // remove new product
    // and check for it absence
    // TODO: not necessary, but preferable to complete
  }

}