## МИНИСТЕРСТВО ОБРАЗОВАНИЯ И НАУКИ РОССИЙСКОЙ ФЕДЕРАЦИИФЕДЕРАЛЬНОЕ ГОСУДАРСТВЕННОЕ АВТОНОМНОЕ ОБРАЗОВАТЕЛЬНОЕ УЧРЕЖДЕНИЕ ВЫСШЕГО ОБРАЗОВАНИЯ

### «Санкт–Петербургский национальный исследовательский университет
информационных технологий, механики и оптики»

### Факультет информационных технологий и программирования
Кафедра информационных систем
Проектирование баз данных

### Объектно ориентированное программирование #1

**Выполнил студент группы M3208:** Терлецкий Егор

**Проверил:** Повышев Владислав Вячеславович

---

## Первая задача ООП
- Определить класс "Рациональная дробь" в виде пары чисел m и n
- Определить класс "Набор дробей". Должна быть возможность добавлять дробь в
набор, и набор должен уметь выдавать следующую статистику:
 -- максимальную дробь в наборе
 -- минимальную дробь в наборе
 -- количество дробей в наборе больше заданной
 -- количество дробей в наборе меньше заданной
    Плюсом будут следующие возможности:
 -- кеширование максимальной/минимальной дроби до изменения набора. Ответ на
количество дробей больше/меньше заданной может быть закеширован по нескольким
последним запросам (то есть предполагаем, что если мы спросили, сколько дробей
больше 1, то и в следующий раз нас снова заинтересует именно сравнение с
единицей)
 -- загрузка набора дробей из файла (формат файла задаёте вы сами)
- Определить класс "Полином" с коэффициентами в виде дроби. Полином должен иметь
возможность задава


# Fraction.java

```java
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
```

# SetOfFraction.java

```java
package com;

import java.util.TreeSet;
import java.util.ArrayList;
import java.util.Arrays;

public class SetOfFraction {
  private TreeSet<Fraction> set;

  public SetOfFraction() {
    set = new TreeSet<Fraction>();
  }

  public boolean add(Fraction... fractions) {
    return set.addAll(Arrays.asList(fractions));
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
```

# Polynom.java

```java
package com;

import java.util.ArrayList;
import java.util.Arrays;

public class Polynom {
  private ArrayList<Fraction> coefficients;

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
```

# PolynomTest.java

```java
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
```

# SetOfFraction.java

```java
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
```