package cs3500.freecell.model.hw02;

import static org.junit.Assert.assertEquals;

import cs3500.freecell.model.hw02.Suit;
import org.junit.Test;

/**
 * class to test the Suit Class.
 */
public class SuitTest {
  @Test // tests the getSymbol() method
  public void testGetSymbol() {
    assertEquals("♠", Suit.Spade.getSymbol());
    assertEquals("♥", Suit.Heart.getSymbol());
    assertEquals("♣", Suit.Club.getSymbol());
    assertEquals("♦", Suit.Diamond.getSymbol());
  }

  @Test // tests the getColor() method
  public void testGetColor() {
    assertEquals("black", Suit.Spade.getColor());
    assertEquals("red", Suit.Heart.getColor());
    assertEquals("black", Suit.Club.getColor());
    assertEquals("red", Suit.Diamond.getColor());
  }
}