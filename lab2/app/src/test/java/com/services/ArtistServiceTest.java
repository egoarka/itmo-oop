package com.services;

import static org.junit.Assert.assertTrue;
import java.util.Arrays;
import java.util.List;
import com.Artist;
import org.junit.Before;
import org.junit.Test;

public class ArtistServiceTest {
  List<Artist> artists;

  @Before
  public void init() {
    Artist alla = new Artist("Alla", "");
    Artist baskov = new Artist("Baskov", "");
    Artist buzova = new Artist("Buzova", "");
    Artist dremin = new Artist("Dremin", "");
    artists = Arrays.asList(alla, buzova, baskov, dremin);
  }

  @Test
  public void testFindByName() {
    Artist match = ArtistService.findByName(artists, "Dremin");
    String expected = "Dremin";
    assertTrue(match.getName().equals(expected));
  }
}
