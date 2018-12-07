package com;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Optional;

import com.dao.ShopDao;
import com.dao.impl.ShopDaoImpl;
import com.entity.Product;
import com.entity.Shop;
import com.scripts.DBScripts;
import com.service.ShopService;
import com.service.impl.ShopServiceImpl;

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
    DBScripts.fillDB();

    // run this if you already created tables
    // DBScripts.deleteTablesRows();
    // DBScripts.fillDB();
  }

  @Test
  public void testPossibleToBuyProductsOn() {
    Optional<Shop> shop = shopDao.findById(1L);
    assertEquals("5ka", shop.get().getName());

    Integer amountOfMoney = 100;
    List<Product> products = shopService.possibleToBuyProductsOn(shop.get(),
        amountOfMoney);

    // List<String> expected = Arrays.asList("fee", "fi", "foe");
    // assertThat(products, CoreMatchers.is(expected));
  }
}