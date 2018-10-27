package com;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;

public class Polynom {
  private ArrayList<Fraction> coefficients;

  public static IntStream iterate(final int seed, final IntUnaryOperator f) {
    Objects.requireNonNull(f);
    Spliterator.OfInt spliterator = new Spliterators.AbstractIntSpliterator(Long.MAX_VALUE,
        Spliterator.ORDERED | Spliterator.IMMUTABLE | Spliterator.NONNULL)

    {
      int prev;
      boolean started;

      @Override
      public boolean tryAdvance(IntConsumer action) {
        Objects.requireNonNull(action);
        int t;
        if (started)
          t = f.applyAsInt(prev);
        else {
          t = seed;
          started = true;
        }
        action.accept(prev = t);
        return true;
      }
    };
    return StreamSupport.intStream(spliterator, false);
  }

  public Polynom(Fraction... fractions) {
    coefficients = new ArrayList<Fraction>(Arrays.asList(fractions));
  }

  public Polynom(ArrayList<Fraction> fractions) {
    coefficients = new ArrayList<Fraction>(fractions);
  }

  public ArrayList<Fraction> getСoefficients() {
    return coefficients;
  }

  public Polynom sumWith(Polynom polynom) throws CloneNotSupportedException {
    ArrayList<Fraction> coLeft = coefficients;
    ArrayList<Fraction> coRight = polynom.getСoefficients();
    int max = Math.max(coLeft.size(), coRight.size());
    int min = Math.min(coLeft.size(), coRight.size());
    ArrayList<Fraction> maxArr = max == coLeft.size() ? coLeft : coRight;

    IntStream

    ArrayList<Fraction> result = new ArrayList<Fraction>();
    for (int i = 0; i < max; i++) {
      if (i >= min) {
        result.add(maxArr.get(i).clone());
      } else {
        result.add(coLeft.get(i).addition(coRight.get(i)));
      }
    }

    return new Polynom(result);
  }
}