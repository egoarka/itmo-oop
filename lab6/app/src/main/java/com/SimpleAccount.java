package com;

import java.util.Optional;

class SimpleAccount extends AbstractAccount implements Simple {
  private static final Double ANNUAL_RATE = 12.0;

  // no extra fee
  @Override
  public Optional<Double> getFee() {
    return Optional.empty();
  }

  // fixed interest on the balance
  @Override
  public Optional<Double> interest() {
    Double computed = (balance * ANNUAL_RATE) / (365 * 100);
    return Optional.of(computed);
  }

  // ain't allowed to hold a negative balance
  @Override
  public boolean canHoldNegative() {
    return false;
  }

}
