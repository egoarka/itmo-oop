package com;

import java.util.Objects;

public class ClientDecorator {
  private final Client client;

  public ClientDecorator(Client client) {
    this.client = client;
  }

  public boolean isDoubftul() {
    String passport = client.getPassport();
    return Objects.nonNull(passport) && passport.length() > 0;
  }
}
