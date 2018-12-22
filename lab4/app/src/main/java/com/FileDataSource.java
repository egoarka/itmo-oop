package com;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FileDataSource {
  private static Path csvDir = Config.getResourceDir().resolve("csv-files");
  private static File shops = csvDir.resolve(Paths.get("shops.txt")).toFile();

  private FileDataSource() {
  }

  public static Stream<String> getShopsStream() {
    try {
      return Files.lines(shops.toPath());
    } catch (IOException e) {
      e.printStackTrace();
    }
    return Stream.empty();
  }
}