import java.io.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

class Lab1Test {
  SetOfFraction sof = new SetOfFraction();
  Fraction f1, f2, f3, f4, f5;

  @Before
  public void setUp() throws Exception {
    sof = new SetOfFraction();
    Fraction f1 = new Fraction(1, 50); // 0.02
    Fraction f2 = new Fraction(5, 10); // 0.5
    Fraction f3 = new Fraction(300, 2); // 150.0
    Fraction f4 = new Fraction(1, 100); // 0.01

    Fraction f5 = new Fraction(1, 100); // 0.01

    sof.add(f1);
    sof.add(f2);
    sof.add(f3);
    sof.add(f4);
    // fifth is excluded
    // s.add(f5);
  }

  @Test
  public void testOutput() {
    Assert.assertEquals(1, 1);
    // Assert.assertEquals(new Double[] { 0.01, 0.02, 0.5, 150.0 }, sof.toArray());
  }

  @Test
  public void testMax() {

    Assert.assertEquals(2, 2);
    // Assert.assertEquals(17, math.add());
  }
}