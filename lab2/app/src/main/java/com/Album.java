package com;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Album {
  private String name;
  private List<Track> tracks;
  private Artist producer;
  private String about;
  private Integer releaseDate;

  public Album(String name, Integer releaseDate) {
    this.name = name;
    this.releaseDate = releaseDate;
    this.tracks = new ArrayList<Track>();
  }

  public void addTrack(Track... tracks) {
    this.tracks.addAll(Arrays.asList(tracks));
  }

  public String getName() {
    return name;
  }

  public List<Track> getTracks() {
    return tracks;
  }

  public Integer getReleaseDate() {
    return releaseDate;
  }
}
