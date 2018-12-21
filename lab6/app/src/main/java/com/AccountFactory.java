package com;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class AccountFactory {
  final static Map<String, Supplier<Account>> map = new HashMap<>();
  static {
    map.put("simple", SimpleAccount::new);
    map.put("deposit", DepositAccount::new);
    map.put("credit", CreditAccount::new);
  }

  // return object only Common type of account
  public static Account getAccount(String accountType) {
    Supplier<Account> account = map.get(accountType.toLowerCase());
    if (account != null) {
      return account.get();
    }
    throw new IllegalArgumentException(
        "No such account " + accountType.toLowerCase());
  }

  // real type of account is set explicitly
  // or by its features (credit limit - credit account, period - deposit)
  public static DepositAccount getAccount(LocalDate fromPeriod,
      LocalDate toPeriod) {
    return new DepositAccount(fromPeriod, toPeriod);
  }

  public static CreditAccount getAccount(Boolean hasCreditLimit) {
    return new CreditAccount();
  }
}