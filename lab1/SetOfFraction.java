import java.util.TreeSet;
import java.util.Comparator;

public class SetOfFraction {
  private TreeSet<Fraction> set;

  public SetOfFraction() {
    set = new TreeSet<Fraction>();
  }

  public boolean add(Fraction fraction) {
    return set.add(fraction);
  }

  public Fraction getMax() {
    if (set.size() == 0)
      return null;
    return set.last();
  }

  public Fraction getMin() {
    if (set.size() == 0)
      return null;
    return set.first();
  }

  public int fractionCountLessThan(Fraction fraction) {
    int result = set.headSet(fraction).size();
    if (result != 0) {
      return result - 1;
    } else {
      return getMin() == fraction ? 0 : -1;
    }
  }

  public int fractionCountGreaterThan(Fraction fraction) {
    int result = set.tailSet(fraction).size();
    if (result != 0) {
      return result;
    } else {
      return getMax() == fraction ? 0 : -1;
    }
  }

  public Object[] toArray() {
    // new Double[set.size()]
    return set.toArray(new Object[set.size()]);
  }

}