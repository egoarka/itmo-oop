package com;

import java.time.LocalDate;

public class Track {
  private String name;
  private String about;
  private String lyrics;
  private Genre genre;
  private LocalDate releaseDate;

  public Track(String name, Genre genre) {
    this.name = name;
    this.genre = genre;
  }

  public String getName() {
    return name;
  }

  public Genre getGenre() {
    return genre;
  }
}
