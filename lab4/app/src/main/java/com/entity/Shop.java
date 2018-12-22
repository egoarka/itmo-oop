package com.entity;

import java.util.Objects;

public class Shop {
  public static final String TABLE_NAME = "shops";
  public static final String ID_COLUMN = "id";
  public static final String NAME_COLUMN = "name";
  public static final String ADDRESS_COLUMN = "address";

  private Long id;
  private String name;
  private String address;

  public Shop(String name, String address) {
    this.name = name;
    this.address = address;
  }

  public Shop(Long id, String name, String address) {
    this.id = id;
    this.name = name;
    this.address = address;
  }

  public Shop() {
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAddress() {
    return this.address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public boolean equals(Object o) {
    if (o == this)
      return true;
    if (!(o instanceof Shop)) {
      return false;
    }
    Shop shop = (Shop) o;
    return Objects.equals(id, shop.id) && Objects.equals(name, shop.name)
        && Objects.equals(address, shop.address);
  }

  public int hashCode() {
    return Objects.hash(id, name, address);
  }

  public String toString() {
    return "{" + " id='" + getId() + "'" + ", name='" + getName() + "'"
        + ", address='" + getAddress() + "'" + "}";
  }
}
