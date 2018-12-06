package com.entity;

import java.util.Objects;

public class Shop {
  private Long id;
  private String name;
  private String address;

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
