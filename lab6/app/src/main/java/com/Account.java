package com;

import java.util.Optional;

interface ExtraFee {
  Optional<Double> getFee();
}

interface Balance {
  Optional<Double> interest();

  Double getBalance();

  void setBalance(Double balance);

  boolean canHoldNegative();
}

interface AccountClient {
  Client getClient();
}

// WithDrawTemplate, FundTemplate, TransferTemplate
interface Account
    extends ExtraFee, Balance, AccountClient, AccountTemplates {
}

abstract class AbstractAccount {

  protected Double balance;
  protected Client client;

  public Client getClient() {
    return this.client;
  }

  public void setClient(Client client) {
    this.client = client;
  }

  public void setBalance(Double balance) {
    this.balance = balance;
  }

  public Double getBalance() {
    return this.balance;
  }
}

interface Simple extends Account {
  // cashing out in any time
}

interface Deposit extends Account {
  boolean hasExpired();
}

interface Credit extends Account {
}
