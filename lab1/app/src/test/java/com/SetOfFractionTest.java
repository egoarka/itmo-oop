package com;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SetOfFractionTest {
  SetOfFraction sof = new SetOfFraction();
  Fraction f1, f2, f3, f4, f5;

  @Before
  public void setUp() {
    sof = new SetOfFraction();
    f1 = new Fraction(1, 50); // 0.02
    f2 = new Fraction(5, 10); // 0.5
    f3 = new Fraction(300, 2); // 150.0
    f4 = new Fraction(1, 100); // 0.01

    f5 = new Fraction(1, 8); // 0.125

    // fifth is excluded
    sof.add(f1, f2, f3, f4/* , f5 */);
  }

  @Test
  public void testGetMax() {
    Fraction max = sof.getMax();
    Assert.assertTrue(max == f3);
  }

  @Test
  public void testGetMin() {
    Fraction max = sof.getMin();
    Assert.assertTrue(max == f4);
  }

  @Test
  public void testFractionCountLessThan() {
    Assert.assertTrue(sof.fractionCountLessThan(f1) == 1);
    Assert.assertTrue(sof.fractionCountLessThan(f2) == 2);
    Assert.assertTrue(sof.fractionCountLessThan(f3) == 3);
    Assert.assertTrue(sof.fractionCountLessThan(f4) == 0);
    Assert.assertTrue(sof.fractionCountLessThan(f5) == 2);
  }

  @Test
  public void testFractionCountGreaterThan() {
    Assert.assertTrue(sof.fractionCountGreaterThan(f1) == 2);
    Assert.assertTrue(sof.fractionCountGreaterThan(f2) == 1);
    Assert.assertTrue(sof.fractionCountGreaterThan(f3) == 0);
    Assert.assertTrue(sof.fractionCountGreaterThan(f4) == 3);
    Assert.assertTrue(sof.fractionCountGreaterThan(f5) == 2);
  }

  @Test
  public void testToArray() {
    ArrayList<Fraction> actual = sof.toArray();
    // [0.01, 0.02, 0.5, 150.0]
    ArrayList<Fraction> expected = new ArrayList<Fraction>(Arrays.asList(f4, f1, f2, f3));
    Assert.assertTrue(actual.equals(expected));
  }
}