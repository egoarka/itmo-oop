package com;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

class DepositAccount extends AbstractAccount implements Deposit {

  private static final Double ANNUAL_RATE = 12.0;

  public DepositAccount() {
  }

  private LocalDate fromPeriod;
  private LocalDate toPeriod;

  public DepositAccount(LocalDate fromPeriod, LocalDate toPeriod) {
    this.fromPeriod = fromPeriod;
    this.toPeriod = toPeriod;
  }

  private Integer periodDifferenceDays() {
    return Period.between(toPeriod, fromPeriod).getDays();
  }

  private Double initialAmount;

  public Double getInitialAmount() {
    return this.initialAmount;
  }

  public void setInitialAmount(Double initialAmount) {
    this.initialAmount = initialAmount;
  }

  // no extra fee
  @Override
  public Optional<Double> getFee() {
    return Optional.empty();
  }

  // interest on the balance depends on the initial amount
  // used simple interest formula
  @Override
  public Optional<Double> interest() {
    Double computed = (initialAmount * ANNUAL_RATE
        * periodDifferenceDays()) / (365 * 100);
    return Optional.of(computed);
  }

  @Override
  public boolean canHoldNegative() {
    return false;
  }

  // allowed make operations only when the account expires (some period)
  @Override
  public boolean hasExpired() {
    return Period.between(LocalDate.now(), toPeriod).isNegative();
  }
}