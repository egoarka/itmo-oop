package com;

import java.util.Arrays;
import java.util.HashSet;

public class Genre {
  private String name;
  private HashSet<String> aliases;

  public Genre(String name) {
    this.name = name;
    aliases = new HashSet<>();
  }

  public String getName() {
    return name;
  }

  public void addAlias(String... aliases) {
    this.aliases.addAll(Arrays.asList(aliases));
  }

  public boolean isGenreOrAlias(String name) {
    return this.name == name || aliases.contains(name);
  }
}
