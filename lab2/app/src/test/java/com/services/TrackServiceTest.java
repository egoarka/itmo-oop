package com.services;

import static org.junit.Assert.assertTrue;
import java.util.Arrays;
import java.util.List;
import com.Genre;
import com.Track;
import org.junit.Before;
import org.junit.Test;

public class TrackServiceTest {
  List<Track> tracks;
  List expectedFindByGenre;

  @Before
  public void init() {
    Genre pop = new Genre("Pop");
    Genre rock = new Genre("Rock");
    Genre hardRock = new Genre("Hard Rock");
    Genre alternativeRock = new Genre("Alternative Rock");
    hardRock.addAlias(rock.getName());
    alternativeRock.addAlias(rock.getName());

    Track track1 = new Track("Burger", hardRock);
    Track track2 = new Track("Malo polovin", alternativeRock);
    Track track3 = new Track("Million Roses", pop);
    tracks = Arrays.asList(track1, track2, track3);
    expectedFindByGenre = Arrays.asList(track1, track2);
  }

  @Test
  public void testFindByGenre() {
    List matches = TrackService.findByGenre(tracks, "Rock");
    assertTrue(matches.equals(expectedFindByGenre));
  }

  @Test
  public void testFindByName() {
    Track match = TrackService.findByName(tracks, "Burger");
    assertTrue(match.getName().equals("Burger"));
  }
}
