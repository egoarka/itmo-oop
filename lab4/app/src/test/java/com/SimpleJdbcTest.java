package com;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.dao.ProductDao;
import com.dao.ShopDao;
import com.dao.impl.ProductDaoImpl;
import com.dao.impl.ShopDaoImpl;
import com.entity.Product;
import com.entity.Shop;
import com.scripts.DBScripts;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;

// import com.github.BenoitDuffez;

/**
 * Unit test for simple App.
 */
public class SimpleJdbcTest {
  private ShopDao shopDao = new ShopDaoImpl();
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
  public void testProductDao() {
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

  @Test
  public void testDifferentListButSameStructure() {
    List<String> actual = Arrays.asList("fee", "fi", "foe");
    List<String> expected = Arrays.asList("fee", "fi", "foe");

    assertThat(actual, CoreMatchers.is(expected));
  }

  @Test
  public void testDifferentObjectButSameStructure() {
    Product product = new Product("milk");
    Product product2 = new Product("milk");
    assertThat(product, CoreMatchers.is(product2));
  }

  @Test
  public void testShopDao() {

    // create new shop
    Shop shop = new Shop("karusel", "st. meolka");
    Optional<Long> newShop = shopDao.insert(shop);
    assertTrue(newShop.get() > 0);

    // query for new shop
    // and check whether it exists or not
    Optional<Shop> shopFromDB = shopDao.findById(newShop.get());
    assertEquals(shop.getName(), shopFromDB.get().getName());

    // take milk as main product
    Product product = new Product("milk", 1, 5);

    Optional<Shop> shopHavingCheapestProduct = shopDao
        .havingCheapestProduct(product)
        .flatMap(shopDao::findById);

    assertEquals("5ka", shopHavingCheapestProduct.get().getName());

    // add this product to karusel shop with lower price
    shopHavingCheapestProduct = shopDao.insertProduct(shop, product)
        .map(Product::new)
        .flatMap(shopDao::havingCheapestProduct)
        .flatMap(shopDao::findById);

    List<Product> karuselProducts = shopDao.fetchProducts(shop);
    assertThat(Arrays.asList(product), CoreMatchers.is(karuselProducts));
    assertEquals("karusel", shopHavingCheapestProduct.get().getName());

    // and 5ka is leading again **
    // because we raised price to milk in karusel
    // + we delivered 3 pack of milk, 3 + 1 = 4 in total
    // + new price = 30
    product.setPrice(30);
    product.setQuantity(3);

    // set price true
    product = shopDao.updateProduct(shop, product, true)
        .map(Product::new)
        .flatMap(p -> shopDao.fetchProduct(shop, p))
        .get();

    Product expectedToUpdate = new Product("milk", 4, 30);
    assertThat(product, CoreMatchers.is(expectedToUpdate));

    // set price false
    product.setPrice(99999); // set price false, therefore current price is prev
    product.setQuantity(1); // prev + current = 4 + 1 = 5
    product = shopDao.updateProduct(shop, product, false)
        .map(Product::new)
        .flatMap(p -> shopDao.fetchProduct(shop, p))
        .get();

    expectedToUpdate = new Product("milk", 5, 30);
    assertThat(product, CoreMatchers.is(expectedToUpdate));

    // **
    shopHavingCheapestProduct = shopDao.havingCheapestProduct(product)
        .flatMap(shopDao::findById);
    assertEquals("5ka", shopHavingCheapestProduct.get().getName());
  }

}