package com;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class GenreTest {
  @Test
  public void testIsGenreOrAlias() {
    Genre pop = new Genre("Pop");
    Genre rock = new Genre("Rock");
    Genre hardRock = new Genre("Hard Rock");
    Genre alternativeRock = new Genre("Alternative Rock");
    hardRock.addAlias(rock.getName());
    alternativeRock.addAlias(rock.getName());

    assertTrue(hardRock.isGenreOrAlias(rock.getName()));
    assertTrue(alternativeRock.isGenreOrAlias(rock.getName()));
    assertTrue(!alternativeRock.isGenreOrAlias("123"));
    assertTrue(!rock.isGenreOrAlias("123"));
    assertTrue(!pop.isGenreOrAlias("Not pop"));
  }
}
