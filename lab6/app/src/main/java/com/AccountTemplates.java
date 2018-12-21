package com;

// account's common interface offers:
// - interface for withdraw (снятие)
// - interface for fund (пополнение)
// - interface for transfer (перевод)
//   - allowed transfer among client accounts

// for implementing methods above is suggested to use @ [template method]

interface WithDrawTemplate extends ExtraFee, Balance, AccountClient {
  default boolean withdraw(Double amount) {
    ClientDecorator client = new ClientDecorator(getClient());
    Double diff = getBalance() - amount;
    if (diff < 0 && canHoldNegative() && !client.isDoubftul()) {
      setBalance(diff);
      return true;
    }
    return false;
  }
}

interface FundTemplate extends ExtraFee, Balance {
  default boolean fund(Double amount) {
    Double fundWithFee = amount - getFee().orElse(0.0);
    Double result = getBalance() + fundWithFee;
    setBalance(result);
    return true;
  }
}

// allowed transfer among client accounts itself
interface TransferTemplate extends ExtraFee, Balance, AccountClient {
  default boolean transfer(Double amount) {
    ClientDecorator client = new ClientDecorator(getClient());
    if (client.isDoubftul())
      return false;
    Double result = getBalance() + amount;
    setBalance(result);
    return true;
  }
}

interface AccountTemplates
    extends WithDrawTemplate, FundTemplate, TransferTemplate {
}