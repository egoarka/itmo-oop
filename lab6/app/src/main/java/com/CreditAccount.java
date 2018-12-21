package com;

import java.util.Optional;

class CreditAccount extends AbstractAccount implements Credit {

  private static final Double FIXED_FEE = 100.0;

  public CreditAccount() {
  }

  // fixed fee for usage if client hold a negative balance
  @Override
  public Optional<Double> getFee() {
    return getBalance() < 0 ? Optional.of(FIXED_FEE)
        : Optional.empty();
  }

  // no fixed interest on the balance
  @Override
  public Optional<Double> interest() {
    return Optional.empty();
  }

  // has credit limit in terms of what you can hold a negative balance
  @Override
  public boolean canHoldNegative() {
    return true;
  }
}