package com;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Hello world!
 *
 */
public class App {
  private static final Logger logger = Logger.getLogger(App.class.getName());
  private static final Pattern sectionPattern = Pattern.compile("^\\[.*\\]$");
  private static final Pattern linePattern = Pattern.compile("^[^;\\[\\r\\n]*");

  public static void main(String[] args) {

    Arrays.asList("helllo = wrold".split("=")).stream().map(String::trim).collect(Collectors.toList())
        .forEach(System.out::println);

    // System.out.println("test");

    if (true)
      return;
    // throw new Error("1");
    // return 1;
    String projectDir = Paths.get("").toFile().getAbsolutePath();
    File file = new File(String.join("/", projectDir, "input.ini"));

    HashMap<String, HashMap<String, String>> sections = new HashMap<>();

    logger.info("Logging begins...");
    Scanner scr = null;

    HashMap<String, String> currentSection = null;
    try {
      scr = new Scanner(file);
      scr.useDelimiter(System.lineSeparator());
      while (scr.hasNext()) {
        String l = scr.next();

        Matcher lineMatcher = linePattern.matcher(l);
        if (lineMatcher.find() && lineMatcher.group(0).length() > 0) {
          if (currentSection != null) {
            throw new Error("section not found");
          }

          System.out.format("%s%s", lineMatcher.group(0), System.lineSeparator());
          continue;
        } else {
          currentSection = null;
        }

        Matcher sectionMatcher = sectionPattern.matcher(l);
        if (sectionMatcher.find() && sectionMatcher.group(0).length() > 0) {
          String sectionName = sectionMatcher.group(0);
          currentSection = new HashMap<>();

          sections.put(sectionName, currentSection);

          System.out.format("%s%s", sectionMatcher.group(0), System.lineSeparator());
          continue;
        }
      }
    } catch (FileNotFoundException ex) {
      logger.log(Level.SEVERE, ex.getMessage(), ex);
    } finally {
      scr.close();
    }

    logger.info("Done...");
  }
}
