package com;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PolynomTest {
  Fraction f1, f2, f3, f4, f5;

  @Before
  public void setUp() {
    f1 = new Fraction(1, 50); // 0.02
    f2 = new Fraction(5, 10); // 0.5
    f3 = new Fraction(300, 2); // 150.0
    f4 = new Fraction(1, 100); // 0.01

    f5 = new Fraction(1, 8); // 0.125
  }

  @Test
  public void testSum() throws CloneNotSupportedException {
    Polynom pln1 = new Polynom(f1, f2, f3, f4);
    Polynom pln2 = new Polynom(f1, f2, f3, f4);
    ArrayList<Fraction> actual = pln1.sumWith(pln2).getСoefficients();
    ArrayList<Fraction> expected = new ArrayList<Fraction>(
        Arrays.asList(f1.addition(f1), f2.addition(f2), f3.addition(f3), f4.addition(f4)));

    Assert.assertTrue(actual.equals(expected));

    pln1 = new Polynom(f1, f2, f3);
    pln2 = new Polynom(f1, f2, f3, f4, f5);
    actual = pln1.sumWith(pln2).getСoefficients();
    expected = new ArrayList<Fraction>(Arrays.asList(f1.addition(f1), f2.addition(f2), f3.addition(f3), f4, f5));

    Assert.assertTrue(actual.equals(expected));
  }
}