package com;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

import org.javatuples.Pair;
import org.javatuples.Quartet;

/**
 * Hello world!
 *
 */
public class Domain {
  // @SuppressWarnings({ "unchecked", "rawtypes" })
  public static void main(String[] args) {
    // List<Product> products = Arrays.asList(new Product("milk"),
    // new Product("vilk"));
    // Optional<String> opt = Optional.ofNullable(null);
    // String name = opt.get();
    // System.out.println(name);
    // // assertEquals("Optional.empty", opt.toString());

    // String projectDir = Paths.get("").toFile().getAbsolutePath();

    // ProductDaoImpl d = new ProductDaoImpl();
    // Optional<String> name = d.insert(new Product("vilk"));
    // name.ifPresent(System.out::println);
    if (true)
      return;
    // System.out.println(String.format("hellao%nworld", ""));
    // try {
    // DataSource.getConnection();
    // } catch (SQLException e1) {
    // // TODO Auto-generated catch block
    // e1.printStackTrace();
    // }

    // @formatter:off
    List<Pair<String, Integer>> input = Arrays.asList(
      Pair.with("milk", 3),
      Pair.with("salt", 4)
    );


    Map<String, Integer> byProductName = input
      .stream()
      .collect(
        Collectors.toMap(
          pair -> pair.getValue0(),
          pair -> pair.getValue1()
        )
      );

    System.out.println("---");
    byProductName.keySet().forEach(System.out::println);
    byProductName.values().forEach(System.out::println);

    List<Quartet<Integer, String, Integer, Integer>> queryResult = Arrays.asList(
      Quartet.with(1, "milk", 15, 3),
      Quartet.with(2, "milk", 35, 15),
      Quartet.with(2, "salt", 2, 6)
    );

    Map<Integer, List<Quartet<Integer, String, Integer, Integer>>> byShopId = queryResult
      .stream()
      .collect(
        Collectors.groupingBy(
          quartet -> quartet.getValue0()
        )
      );
     

    System.out.println("---");
    byShopId.keySet().forEach(System.out::println);
    byShopId.values().forEach(System.out::println);

    Map<Integer, Integer> y =
    byShopId.entrySet().stream()
      // if query result product quantity ge than input product quantity
      // and if filtered size eq to input's one
      // then it satisfies our requirements
      .filter(entry ->
        entry.getValue().stream()
          .filter(product -> 
            product.getValue3() >= byProductName.get(product.getValue1())
          ).count() == input.size()
      )
      // in end we get amount of:
      // products multiplied by input quantity
      .collect(
        Collectors.toMap(
          e -> e.getKey(),
          e -> e.getValue().stream()
            .map(product -> 
              byProductName.get(product.getValue1()) * product.getValue3()
            ).reduce(0, Integer::sum)
        )
      );

    System.out.println("---");
    y.keySet().forEach(System.out::println);
    y.values().forEach(System.out::println);


    // look for min value among others
    Optional<Entry<Integer, Integer>> z = 
    y.entrySet().stream()
      .min((a, b) -> a.getValue().compareTo(b.getValue()));

    System.out.println("---");
    z.ifPresent(System.out::println);
    
    

    // ArrayList a;
    // a.addal
    
    // shop_id	product_name	price	quantity
    // 1	milk	15	3
    // 2	milk	35	7
    // 2	salt	2	1

    // System.out.println(1);
    // HashMap<String, List<Pair>> keyedInput = input.stream(
    // ).

    // Pair<Integer, Integer> a = new Pair(33, 2);
    // System.out.println(a.getValue0());
    // Config.getInstance();
    // System.out.println(s);
    // System.out.println("Hello World!");
  }
}
