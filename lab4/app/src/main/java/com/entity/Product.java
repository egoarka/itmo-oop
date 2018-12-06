package com.entity;

import java.util.Objects;

public class Product {
  public static final String TABLE_NAME = "products";
  public static final String ID_COLUMN = "name";

  private String name;

  public Product() {

  }

  public Product(String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean equals(Object o) {
    if (o == this)
      return true;
    if (!(o instanceof Product)) {
      return false;
    }
    Product product = (Product) o;
    return Objects.equals(name, product.name);
  }

  public int hashCode() {
    return Objects.hashCode(name);
  }

  public String toString() {
    return "{" + " name='" + getName() + "'" + "}";
  }
}
