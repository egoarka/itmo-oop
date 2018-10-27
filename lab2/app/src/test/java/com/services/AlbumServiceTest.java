package com.services;

import static org.junit.Assert.assertTrue;
import java.util.Arrays;
import java.util.List;
import com.Album;
import com.Genre;
import com.Track;
import org.junit.Before;
import org.junit.Test;

public class AlbumServiceTest {
  List<Album> albums;
  List expectedMatchesByReleaseDate;
  List expectedMatchesByTrackGenre;

  @Before
  public void init() {
    Genre pop = new Genre("Pop");
    Genre rock = new Genre("Rock");
    Genre hardRock = new Genre("Hard Rock");
    Genre alternativeRock = new Genre("Alternative Rock");
    hardRock.addAlias(rock.getName());
    alternativeRock.addAlias(rock.getName());

    Album album1 = new Album("Hate love", 2017);
    {
      Track track1 = new Track("Burger", hardRock);
      Track track2 = new Track("Life", pop);
      Track track3 = new Track("Liza", alternativeRock);

      album1.addTrack(track1, track2, track3);
    }

    Album album2 = new Album("Ah, kak hochetsya zhit'", 1985);
    {
      Track track1 = new Track("Million Roses", pop);
      album2.addTrack(track1);
    }

    albums = Arrays.asList(album1, album2);
    expectedMatchesByReleaseDate = Arrays.asList(album1);
    expectedMatchesByTrackGenre = Arrays.asList(album1);
  }

  @Test
  public void testFindByName() {
    Album match = AlbumService.findByName(albums, "Hate love");
    assertTrue(match.getName().equals("Hate love"));
  }

  @Test
  public void testFindAlbumsByReleaseDate() {
    List matches = AlbumService.findAlbumsByReleaseDate(albums, 2017);
    assertTrue(matches.equals(expectedMatchesByReleaseDate));
  }

  @Test
  public void testFindAlbumsByTrackGenre() {
    List matches = AlbumService.findAlbumsByTrackGenre(albums, "Rock");
    assertTrue(matches.equals(expectedMatchesByTrackGenre));
  }
}
