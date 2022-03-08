package cs3500.freecell.model.hw02;

import static org.junit.Assert.assertEquals;

import cs3500.freecell.model.hw02.Rank;
import cs3500.freecell.model.hw02.Suit;
import org.junit.Test;

/**
 * class to test the Card class.
 */
public class CardTest {

  Card aceSpades = new Card(Rank.Ace, Suit.Spade);

  @Test
  public void testToString() {
    assertEquals("A♠", aceSpades.toString());
  }

  @Test
  public void testGetRank() {
    assertEquals(Rank.Ace, aceSpades.getRank());
  }

  @Test
  public void testGetSuit() {
    assertEquals(Suit.Spade, aceSpades.getSuit());
  }
}