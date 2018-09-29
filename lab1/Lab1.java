import java.io.*;

public class Lab1 {

  public static void main(String args[]) {
    // System.out.println("Hello world 3");
    SetOfFraction s = new SetOfFraction();
    Fraction f1 = new Fraction(1, 50); // 0.02
    Fraction f2 = new Fraction(5, 10); // 0.5
    Fraction f3 = new Fraction(300, 2); // 150.0
    Fraction f4 = new Fraction(1, 100); // 0.01

    Fraction f5 = new Fraction(1, 100); // 0.01

    s.add(f1);
    s.add(f2);
    s.add(f3);
    s.add(f4);

    System.out.println("TreeSet: " + s.toArray());

    System.out.println(s.getMax() == f3);
    System.out.println(s.getMin() == f4);
    System.out.println(s.fractionCountLessThan(f1) == 1);
    System.out.println(s.fractionCountLessThan(f2) == 2);
    System.out.println(s.fractionCountLessThan(f3) == 3);
    System.out.println(s.fractionCountLessThan(f4) == 0);
    System.out.println(s.fractionCountLessThan(f5) == -1);

    System.out.println(s.fractionCountGreaterThan(f1) == 2);
    System.out.println(s.fractionCountGreaterThan(f2) == 1);
    System.out.println(s.fractionCountGreaterThan(f3) == 0);
    System.out.println(s.fractionCountGreaterThan(f4) == 3);
    System.out.println(s.fractionCountGreaterThan(f5) == -1);
  }
}
