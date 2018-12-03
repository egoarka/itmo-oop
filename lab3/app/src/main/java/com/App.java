package com;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Hello world!
 *
 */
public class App {
  private static final Logger logger = Logger.getLogger(App.class.getName());
  private static final Pattern sectionPattern = Pattern.compile("^\\[.*\\]$");
  private static final Pattern linePattern = Pattern.compile("^[^;\\[\\r\\n]*");

  public static void main(String[] args) {
    String projectDir = Paths.get("").toFile().getAbsolutePath();
    File file = new File(String.join("/", projectDir, "input.ini"));

    logger.info("Logging begins...");
    Scanner scr = null;
    try {
      scr = new Scanner(file);
      scr.useDelimiter(System.lineSeparator());
      while (scr.hasNext()) {
        String l = scr.next();
        Matcher m = linePattern.matcher(l);
        if (m.find()) {
          System.out.format("line : %s -- %s %s", l, m.group(0), System.lineSeparator());
        }
      }
    } catch (FileNotFoundException ex) {
      logger.log(Level.SEVERE, ex.getMessage(), ex);
    }

    logger.info("Done...");
  }
}
