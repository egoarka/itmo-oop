package com.entity;

import java.util.Objects;

public class Product {
  public static final String TABLE_NAME = "products";
  public static final String ID_COLUMN = "name";

  private String name;
  private Integer quantity;
  private Integer price;

  public Product(String name, Integer quantity, Integer price) {
    this.name = name;
    this.quantity = quantity;
    this.price = price;
  }

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

  public Integer getQuantity() {
    return this.quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  public Integer getPrice() {
    return this.price;
  }

  public void setPrice(Integer price) {
    this.price = price;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this)
      return true;
    if (!(o instanceof Product)) {
      return false;
    }
    Product product = (Product) o;
    return Objects.equals(name, product.name)
        && Objects.equals(quantity, product.quantity)
        && Objects.equals(price, product.price);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, quantity, price);
  }

  @Override
  public String toString() {
    return "{" + " name='" + getName() + "'" + ", quantity='" + getQuantity()
        + "'" + ", price='" + getPrice() + "'" + "}";
  }

}
