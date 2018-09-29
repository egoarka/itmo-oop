import java.io.*;

public class Fraction implements Comparable<Fraction> {
  private double numenator;
  private double denominator;

  public Fraction(double n, double d) {
    numenator = n;
    denominator = d;
  }

  public double getNumenator() {
    return numenator;
  }

  public double getDenominator() {
    return denominator;
  }

  public double setNumerator(double n) {
    return numenator = n;
  }

  public double setDenominator(double d) {
    return denominator = d;
  }

  public double value() {
    return (double) (numenator / denominator);
  }

  public int compareTo(Fraction fraction) {
    return value() - fraction.value() < 0 ? -1 : 1;
  }

  public String toString() {
    return Double.toString(value());
  }
}