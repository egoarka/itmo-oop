package com;

import java.util.Objects;

public class Client {
  private String firstName;
  private String lastName;
  private String passport;

  public String getFirstName() {
    return this.firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return this.lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getPassport() {
    return this.passport;
  }

  public void setPassport(String passport) {
    this.passport = passport;
  }

  public Client(String firstName, String lastName, String passport) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.passport = passport;
  }

  public static class Builder {
    private String firstName;
    private String lastName;
    private String passport;

    public Builder(String firstName, String lastName) {
      this.firstName = Objects.requireNonNull(firstName);
      this.lastName = Objects.requireNonNull(lastName);
    }

    public Builder setPassport(String passport) {
      this.passport = passport;
      return this;
    }

    public Client build() {
      return new Client(firstName, lastName, passport);
    }
  }

  // @formatter:off
  @Override
  public String toString() {
    return "{" +
      " firstName='" + firstName + "'" +
      ", lastName='" + lastName + "'" +
      ", passport='" + passport + "'" +
      "}";
  }
}

// ### btw

// https://stackoverflow.com/a/31754787 (GenericBuilder)

// Person value = GenericBuilder.of(Person::new)
//   .with(Person::setName, "Otto")
//   .with(Person::setAge, 5).build();

// ### or this

// https://benjiweber.co.uk/blog/2014/11/02/builder-pattern-with-java-8-lambdas/

//  public static BurgerBuilder burger() {
//    return patty -> topping -> new Burger(patty, topping);
//  }