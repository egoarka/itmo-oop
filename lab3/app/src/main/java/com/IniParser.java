package com;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.regex.Pattern;

import com.spencerwi.either.Either;

import io.vavr.Tuple;
import io.vavr.collection.HashMap;
import io.vavr.collection.List;
import io.vavr.control.Try;

public class IniParser {
  // @formatter:off
  // internal structure of ini parser 
  // consist of tuples (line's key, line's value) 
  // keyed by string (section key)
  
  // represtented in java like this
  private 
   HashMap<String, HashMap<String, String>> state;
  // @formatter:on

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
  private IniParser() {
  }

  @SuppressWarnings("unchecked")

  public static IniParser fromFile(String filePath) throws IOException {
    IniParser parser = new IniParser();
    File file = new File(filePath);

    // @formatter:off
    parser.state = Files.lines(file.toPath())
      .map(str -> 
        composedPattern.matcher(str)
          .results()
          .map(r ->
            // compute dumb null throwable regex >_>
            Try.of(() ->
              Either.either(
                () -> List.of(r.group(0).split("=")).map(String::trim),
                () -> r.group(3)
              )
            ).getOrNull()
          )
          .findAny()
          .orElse(null)
      )
      .filter(r -> r != null) 
      .reduce(
        // counter, map
        Tuple.of(
          "",
          HashMap.of("", HashMap.of("", ""))
        ),
        (state, item) -> {
          String parent = state._1;
          HashMap<String, HashMap<String, String>> sections = state._2;
          return item
            .fold(
              line -> Tuple.of(
                parent,
                sections.put(parent, 
                  sections.get(parent).getOrNull()
                  .put(line.get(0), line.get(1))
                )
              ),
              section -> Tuple.of(
                // make this section the current
                section,
                // and add this section to map
                sections.put(section,  
                  HashMap.empty()
                ) 
              )
            );
        },
        (a, b) -> a
      )._2;

    return parser;
  }

  public <T> T get(String section, String key) {
    return (T) state.getOrElse(section, null).getOrElse(key, null);
  }
}