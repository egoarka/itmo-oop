package com;

public abstract class ProcessRequest<T> {
  protected ProcessRequest<T> successor;

  public void setSuccessor(ProcessRequest<T> successor) {
    this.successor = successor;
  }

  public T handle(T input) {
    T result = handleWork(input);
    if (successor != null) {
      return successor.handle(result);
    }
    return result;
  }

  abstract protected T handleWork(T input);
}

class InterestRequest extends ProcessRequest<Account> {
  @Override
  protected Account handleWork(Account input) {
    Double withInterest = input.getBalance()
        + input.interest().orElse(0.0);
    input.setBalance(withInterest);
    return input;
  }
}

class FeeRequest extends ProcessRequest<Account> {
  @Override
  protected Account handleWork(Account input) {
    Double withFee = input.getBalance() - input.getFee().orElse(0.0);
    input.setBalance(withFee);
    return input;
  }
}
