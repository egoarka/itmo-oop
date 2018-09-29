package com;

public class Fraction implements Comparable<Fraction>, Cloneable {
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

  public double getValue() {
    return (double) (numenator / denominator);
  }

  public Fraction addition(Fraction fraction) throws CloneNotSupportedException {
    if (fraction == null) {
      return this.clone();
    }
    return new Fraction(getNumenator() + fraction.getNumenator(), getDenominator() + fraction.getDenominator());
  }

  @Override
  public int compareTo(Fraction fraction) {
    int result;
    result = Double.compare(this.getValue(), fraction.getValue());
    if (result != 0)
      return result;
    return 0;
  }

  @Override
  public boolean equals(Object o) {
    return (o instanceof Fraction) && (this.compareTo((Fraction) o) == 0);
  }

  @Override
  public String toString() {
    return Double.toString(getValue());
  }

  @Override
  protected Fraction clone() throws CloneNotSupportedException {
    return (Fraction) super.clone();
  }
}