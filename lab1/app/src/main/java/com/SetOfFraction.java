package com;

import java.util.TreeSet;
import java.util.ArrayList;
import java.util.Arrays;

public class SetOfFraction {
  private TreeSet<Fraction> set;

  public SetOfFraction() {
    set = new TreeSet<Fraction>();
  }

  public boolean add(Fraction... fraction) {
    return set.addAll(Arrays.asList(fraction));
  }

  public Fraction getMax() {
    return set.last();
  }

  public Fraction getMin() {
    return set.first();
  }

  public int fractionCountLessThan(Fraction fraction) {
    return set.headSet(fraction).size();
  }

  public int fractionCountGreaterThan(Fraction fraction) {
    return set.tailSet(fraction, false).size();
  }

  public ArrayList<Fraction> toArray() {
    return new ArrayList<Fraction>(set);
  }
}