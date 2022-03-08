package cs3500.freecell.model.hw02;

import static org.junit.Assert.assertEquals;

import cs3500.freecell.model.hw02.Rank;
import org.junit.Test;

/**
 * class to test the Rank Class.
 */
public class RankTest {

  @Test // tests the getValue() method
  public void testGetValue() {
    assertEquals(1, Rank.Ace.getValue());
    assertEquals(2, Rank.Two.getValue());
    assertEquals(3, Rank.Three.getValue());
    assertEquals(4, Rank.Four.getValue());
    assertEquals(5, Rank.Five.getValue());
    assertEquals(6, Rank.Six.getValue());
    assertEquals(7, Rank.Seven.getValue());
    assertEquals(8, Rank.Eight.getValue());
    assertEquals(9, Rank.Nine.getValue());
    assertEquals(10, Rank.Ten.getValue());
    assertEquals(11, Rank.Jack.getValue());
    assertEquals(12, Rank.Queen.getValue());
    assertEquals(13, Rank.King.getValue());
  }

  @Test // tests the getSymbol() method
  public void testGetSymbol() {
    assertEquals("A", Rank.Ace.getSymbol());
    assertEquals("2", Rank.Two.getSymbol());
    assertEquals("3", Rank.Three.getSymbol());
    assertEquals("4", Rank.Four.getSymbol());
    assertEquals("5", Rank.Five.getSymbol());
    assertEquals("6", Rank.Six.getSymbol());
    assertEquals("7", Rank.Seven.getSymbol());
    assertEquals("8", Rank.Eight.getSymbol());
    assertEquals("9", Rank.Nine.getSymbol());
    assertEquals("10", Rank.Ten.getSymbol());
    assertEquals("J", Rank.Jack.getSymbol());
    assertEquals("Q", Rank.Queen.getSymbol());
    assertEquals("K", Rank.King.getSymbol());
  }
}