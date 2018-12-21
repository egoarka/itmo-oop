package com;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class AppTest {

  @Test
  public void buildClientTest() {
    Client actual = new Client.Builder("Foo", "Bar")
        .setPassport("0312")
        .build();

    String expected = "{ firstName='Foo', lastName='Bar', passport='0312'}";
    assertEquals(expected, actual.toString());
  }

  public void processRequestTest() {
    Account account = AccountFactory.getAccount("simple");
    ProcessRequest<Account> p1 = new InterestRequest();
    ProcessRequest<Account> p2 = new FeeRequest();
    p1.setSuccessor(p2);
    Account result = p1.handle(account);
  }
}
