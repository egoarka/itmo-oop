package com;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

import com.dao.ShopDao;
import com.dao.impl.FileShopDaoImpl;
import com.entity.Shop;

import org.junit.Test;

public class FileShopDaoTest {
  private ShopDao shopDao = new FileShopDaoImpl();

  @Test
  public void testFindById() {
    Optional<Shop> actual = shopDao.findById(2L);
    // 2,Lenta,SPB2
    assertEquals(new Shop(2L, "Lenta", "SPB2"), actual.get());
  }
}