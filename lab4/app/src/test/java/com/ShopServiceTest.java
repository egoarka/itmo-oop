package com;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.dao.ShopDao;
import com.dao.impl.ShopDaoImpl;
import com.entity.Product;
import com.entity.Shop;
import com.scripts.DBScripts;
import com.service.ShopService;
import com.service.impl.ShopServiceImpl;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

public class ShopServiceTest {
  private ShopService shopService = new ShopServiceImpl();
  private ShopDao shopDao = new ShopDaoImpl();

  @Before
  public void init() {
    // recommends to run this
    // if you didn't run test yet
    DBScripts.dropTables();
    DBScripts.createTables();
    DBScripts.fillDBServices();

    // run this if you already created tables
    // DBScripts.deleteTablesRows();
    // DBScripts.fillDB();
  }

  @Test
  public void testPossibleToBuyProductsOn() {
    Optional<Shop> shop = shopDao.findById(111L);
    assertEquals("service-shop-1", shop.get().getName());

    Integer amountOfMoney = 50;
    List<Product> products = shopService.possibleToBuyProductsOn(shop.get(),
        amountOfMoney);
    List<Product> expected = Arrays.asList(new Product("salt", 10),
        new Product("milk", 3));

    assertThat(products, Matchers.containsInAnyOrder(expected.toArray()));

    amountOfMoney = 29;
    products = shopService.possibleToBuyProductsOn(shop.get(), amountOfMoney);
    expected = Arrays.asList(new Product("salt", 5), new Product("milk", 1));

    assertThat(products, Matchers.containsInAnyOrder(expected.toArray()));
  }

  @Test
  public void testDeliverToShop() {
    Optional<Shop> shop = shopDao.findById(222L);
    assertEquals("service-shop-2", shop.get().getName());

    List<Product> shopProducts = shopDao.fetchProducts(shop.get());
    List<Product> expected = Arrays.asList(new Product("milk", 7, 35),
        new Product("salt", 1, 2));

    assertThat(shopProducts, Matchers.containsInAnyOrder(expected.toArray()));

    List<Product> productsToDeliver = Arrays.asList(new Product("milk", 2, 15),
        new Product("chocolate", 10, 5));

    List<Product> delivered = shopService.deliverToShop(shop.get(),
        productsToDeliver, true);

    expected = Arrays.asList(new Product("milk", 9, 15),
        new Product("chocolate", 10, 5));

    assertThat(delivered, Matchers.containsInAnyOrder(expected.toArray()));
  }

  @Test
  public void testBuyProduct() {
    Optional<Shop> shop = shopDao.findById(333L);
    assertEquals("service-shop-3", shop.get().getName());

    List<Product> shopProducts = shopDao.fetchProducts(shop.get());
    List<Product> expected = Arrays.asList(new Product("milk", 3, 5),
        new Product("salt", 4, 2));

    assertThat(shopProducts, Matchers.containsInAnyOrder(expected.toArray()));

    Product productToBuy = new Product("milk", 2);
    Optional<Integer> totalPurchaseAmount = shopService.buyProduct(shop.get(),
        productToBuy);

    Optional<Product> remainInStore = shopDao.fetchProduct(shop.get(),
        productToBuy);

    assertEquals((Integer) (2 * 5), totalPurchaseAmount.get());
    assertEquals(new Product("milk", 1, 5), remainInStore.get());

    // once again

    totalPurchaseAmount = shopService.buyProduct(shop.get(), productToBuy);

    remainInStore = shopDao.fetchProduct(shop.get(), productToBuy);

    assertEquals(true, totalPurchaseAmount.isEmpty());
    assertEquals(new Product("milk", 1, 5), remainInStore.get());

  }

  @Test
  public void whereToBuyAtProfit() {

    List<Product> products = Arrays.asList(new Product("milk", 1));
    Optional<Shop> shop = shopService.whereToBuyAtProfit(products);
    assertEquals("service-shop-3", shop.get().getName());

    // once again

    products = Arrays.asList(new Product("milk", 4));
    shop = shopService.whereToBuyAtProfit(products);
    assertEquals("service-shop-2", shop.get().getName());

    // once again

    products = Arrays.asList(new Product("milk", 3), new Product("salt", 3));
    shop = shopService.whereToBuyAtProfit(products);
    assertEquals("service-shop-3", shop.get().getName());

    // and last time :)

    products = Arrays.asList(new Product("milk", 1), new Product("salt", 5));
    shop = shopService.whereToBuyAtProfit(products);
    assertEquals("service-shop-1", shop.get().getName());
  }
}