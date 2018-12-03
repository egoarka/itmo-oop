package com;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class IniParser {
  // @formatter:off
  // internal structure of ini parser 
  // consist of tuples (line's key, line's value) 
  // keyed by string (section key)
  
  // represtented in java like this
  private 
    HashMap<
      String,
      HashMap<String, String>
    > sections = new HashMap<>();
  // @formatter:on

  private static Logger logger = MyLogger.getLogger(IniParser.class.getName());
  private static Pattern sectionPattern = Pattern.compile("^\\[(.*)\\]$");
  private static Pattern linePattern = Pattern.compile("^([^;\\[\\r\\n]*)");

  private IniParser() {
  }

  public static IniParser fromFile(String filePath) {
    logger.info("--- Start loading file...");

    IniParser parser = new IniParser();

    File file = new File(filePath);
    Scanner scr = null;

    HashMap<String, String> currentSection = null;

    try {
      scr = new Scanner(file);
      scr.useDelimiter(System.lineSeparator());
      while (scr.hasNext()) {
        String l = scr.next();
        /*
         * section matcher
         */
        Matcher sectionMatcher = sectionPattern.matcher(l);
        if (sectionMatcher.find() && sectionMatcher.group(1).length() > 0) {
          String sectionName = sectionMatcher.group(1);
          currentSection = new HashMap<String, String>();

          parser.sections.put(sectionName, currentSection);

          logger.info(String.format("Section: %s", sectionMatcher.group(1)));
        } else {
          /*
           * line matcher
           */
          Matcher lineMatcher = linePattern.matcher(l);
          if (lineMatcher.find() && lineMatcher.group(1).length() > 0) {
            if (currentSection == null) {
              throw new Error("section not found");
            }

            String line = lineMatcher.group(1);
            List<String> pair = Arrays.asList(line.split("="))
                .stream()
                .map(String::trim)
                .collect(Collectors.toList());

            currentSection.put(pair.get(0), pair.get(1));

            logger.info(String.format("Line: %s", lineMatcher.group(1)));
          }
        }

      }
    } catch (FileNotFoundException ex) {
      logger.severe(ex.getMessage());
    } finally {
      scr.close();
    }

    logger.info("--- File has been loaded...");
    return parser;
  }

  public <T> T get(String section, String key) {
    return (T) sections.get(section).get(key);
  }
}