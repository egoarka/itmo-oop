package com;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.control.Try;

public class IniParserTest {
  String projectDir = Paths.get("").toFile().getAbsolutePath();
  String filePath = String.join("/", projectDir, "input.ini");
  private static String section = "[COMMON]";
  private static String line = "Driver = libusb ; cypress / libusb / random / fileIQS";

  private static String sectionPattern = "[\\[](.+)[\\]]";
  private static String linePattern = "[^;\\[\\r\\n]+";
  //@formatter:off
  private static Pattern composedPattern = Pattern.compile(
    String.format("^(%s)|(%s)$", 
      linePattern,
      sectionPattern
    )
  );
  //@formatter:on  
  @Test
  public void streamNull() {
    Stream.of(1).collect(Collectors.toList());
  }

  @Test
  public void tryTest() {
    List<String> aaa = Arrays.asList("1", "21", "3");
    Try.of(() -> aaa.get(4).split("=")).getOrNull();
  }

  @Test
  public void simpleTest() throws IOException {
    IniParser p = IniParser.fromFile(filePath);
    assertTrue(p.get("COMMON", "DiskCachePath").equals("/sata/panorama"));
    assertTrue(p.get("LEGACY_XML", "ListenTcpPort").equals("1976"));
    assertTrue(p.get("DEBUG", "PlentySockMaxQSize").equals("126"));
  }

  // @formatter:off
  public Map<String, String> transform(List<Tuple2<String, String>> pairs) {
    return pairs
      .stream()
      .collect(
        Collectors.toMap(
          k -> k._1(),
          v -> v._2() 
        )
      );
  }

  @Test
  public void pureMap() {
    // Map a = Map.of();
    List<Tuple2<String, String>> pairs = 
      Arrays.asList(
        Tuple.of("key1", "value1"),
        Tuple.of("key2", "value2")
      );
  }
 
  @Test
  public void listPairsToMapTest() {
    List<Tuple2<String, String>> pairs = 
      Arrays.asList(
        Tuple.of("key1", "value1"),
        Tuple.of("key2", "value2")
      );

    Map<String, String> mapped = transform(pairs);
    List<String> keys = mapped.keySet().stream().collect(Collectors.toList());
    List<String> values = mapped.values().stream().collect(Collectors.toList());

    assertThat(Arrays.asList("key1", "key2"), CoreMatchers.is(keys));
    assertThat(Arrays.asList("value1", "value2"), CoreMatchers.is(values));
    // @formatter:on
  }

  @Test
  public void regexSectionMatchTest() {
    Optional<String> s = composedPattern.matcher(section)
        .results()
        .map(r -> r.group(3))
        .findAny();

    assertEquals("COMMON", s.get());
  }

  @Test
  public void regexLineMatchTest() {
    Optional<String> s = composedPattern.matcher(line)
        .results()
        .map(r -> r.group(0))
        .findAny();

    assertEquals("Driver = libusb".trim(), s.get().trim());
  }
}
