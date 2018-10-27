package com.services;

import java.util.List;
import java.util.stream.Collectors;
import com.Track;

public class TrackService {
  // @formatter:off
  public static Track findByName(List<Track> tracks, String name) {
    return tracks
      .stream()
      .filter(track -> name.equals(track.getName()))
      .findAny()
      .orElse(null);
  }

  public static List<Track> findByGenre(List<Track> tracks, String name) {
    return tracks
      .stream()
      .filter(track -> track.getGenre().isGenreOrAlias(name))
      .collect(Collectors.toList());
  }
}
