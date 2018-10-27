package com;

public class Artist {
  private String name;
  private String bio;
  // private List<Album> albums;

  public Artist(String name, String bio) {
    this.name = name;
    this.bio = bio;
  }

  public String getName() {
    return name;
  }
}
