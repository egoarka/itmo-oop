package com.services;

import java.util.List;
import java.util.stream.Collectors;
import com.Album;

public class AlbumService {
  // @formatter:off
  public static Album findByName(List<Album> albums, String name) {
    return albums
      .stream()
      .filter(album -> name.equals(album.getName()))
      .findAny()
      .orElse(null);
  }

  public static List<Album> findAlbumsByReleaseDate(List<Album> albums, Integer releaseDate) {
    return albums 
      .stream()
      .filter(album -> album.getReleaseDate().equals(releaseDate))
      .collect(Collectors.toList());
  }

  public static List<Album> findAlbumsByTrackGenre(List<Album> albums, String genreName) {
    return albums 
      .stream()
      .filter(album -> TrackService.findByGenre(album.getTracks(), genreName).size() > 0)
      .collect(Collectors.toList());
  }
}
