package com.services;

import java.util.List;
import com.Artist;

public class ArtistService {
  // @formatter:off
  public static Artist findByName(List<Artist> artists, String name) {
    return artists
      .stream()
      .filter(artist -> name.equals(artist.getName()))
      .findAny()
      .orElse(null);
  }
}
