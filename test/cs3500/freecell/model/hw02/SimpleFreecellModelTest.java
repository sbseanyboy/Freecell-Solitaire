package cs3500.freecell.model.hw02;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import cs3500.freecell.model.PileType;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;

/**
 * class to test the SimpleFreecellModel class.
 */
public class SimpleFreecellModelTest {

  // data declarations for tests
  private SimpleFreecellModel fm1;
  private ArrayList<Card> fullDeck;
  private ArrayList<String> fullDeckStrings;
  private Card aceSpades;

  /**
   * initializes the data.
   */
  @Before
  public void initData() {
    this.fm1 = new SimpleFreecellModel();
    this.fullDeck = fm1.getDeck();
    this.fullDeckStrings = new ArrayList<>(Arrays.asList(
        "A♥", "2♥", "3♥", "4♥", "5♥", "6♥", "7♥", "8♥", "9♥", "10♥", "J♥", "Q♥", "K♥",
        "A♠", "2♠", "3♠", "4♠", "5♠", "6♠", "7♠", "8♠", "9♠", "10♠", "J♠", "Q♠", "K♠",
        "A♣", "2♣", "3♣", "4♣", "5♣", "6♣", "7♣", "8♣", "9♣", "10♣", "J♣", "Q♣", "K♣",
        "A♦", "2♦", "3♦", "4♦", "5♦", "6♦", "7♦", "8♦", "9♦", "10♦", "J♦", "Q♦", "K♦"));
    this.aceSpades = new Card(Rank.Ace, Suit.Spade);
  }

  @Test // tests the getDeck() method
  public void testGetDeck() {
    assertEquals(52, fm1.getDeck().size());
  }

  @Test // tests the startGame() method
  public void testStartGame() {
    assertEquals(-1, fm1.getNumCascadePiles());
    assertEquals(-1, fm1.getNumOpenPiles());
    assertEquals(-1, fm1.getNumFoundationPiles());
    assertFalse(fm1.getStartedStatus());
    assertEquals(-1, fm1.getNumPiles());
    fm1.startGame(fm1.getDeck(), 8, 4, true);
    assertEquals(8, fm1.getNumCascadePiles());
    assertEquals(4, fm1.getNumOpenPiles());
    assertEquals(4, fm1.getNumFoundationPiles());
    assertTrue(fm1.getStartedStatus());
    assertEquals(16, fm1.getNumPiles());
    fm1.startGame(fm1.getDeck(), 4, 2, true);
    assertEquals(4, fm1.getNumCascadePiles());
    assertEquals(2, fm1.getNumOpenPiles());
    assertEquals(4, fm1.getNumFoundationPiles());
    assertTrue(fm1.getStartedStatus());
    assertEquals(10, fm1.getNumPiles());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullDeckStartGame() {
    fm1.startGame(null, 8, 4, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidCascade() {
    fm1.startGame(fm1.getDeck(), 3, 4, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidOpen() {
    fm1.startGame(fm1.getDeck(), 8, 0, false);
  }

  @Test
  public void testIsValidDeck() {
    assertTrue(this.fm1.isValidDeck(fullDeck));
    this.fullDeck.remove(1);
    this.fullDeck.add(aceSpades);
    assertFalse(this.fm1.isValidDeck(fullDeck));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidDeck() {
    this.fullDeck.remove(1);
    assertTrue(this.fm1.isValidDeck(fullDeck));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullDeck() {
    assertTrue(this.fm1.isValidDeck(null));
  }

  @Test
  public void testAllToString() {
    assertEquals(fullDeckStrings, this.fm1.allToString(fullDeck));
  }

  @Test
  public void testMove() {
    this.fm1.startGame(fm1.getDeck(), 8, 4, false);
    fm1.move(PileType.CASCADE, 1, 6, PileType.OPEN, 0);
    assertEquals(6, fm1.getNumCardsInCascadePile(1));
    assertEquals(1, fm1.getNumCardsInOpenPile(0));
    fm1.move(PileType.CASCADE, 1, 5, PileType.OPEN, 1);
    assertEquals(5, fm1.getNumCardsInCascadePile(1));
    assertEquals(1, fm1.getNumCardsInOpenPile(1));
  }

  @Test(expected = IllegalStateException.class)
  public void testMoveNotStarted() {
    fm1.move(PileType.CASCADE, 1, 3, PileType.OPEN, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidSource() {
    fm1.startGame(fm1.getDeck(), 8, 4, false);
    fm1.move(PileType.CASCADE, 8, 3, PileType.OPEN, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidDest() {
    fm1.startGame(fm1.getDeck(), 8, 4, false);
    fm1.move(PileType.CASCADE, 4, 3, PileType.OPEN, 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidCard() {
    fm1.startGame(fm1.getDeck(), 8, 4, false);
    fm1.move(PileType.CASCADE, 4, 13, PileType.OPEN, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidMove1() {
    fm1.startGame(fm1.getDeck(), 8, 4, false);
    fm1.move(PileType.FOUNDATION, 4, 3, PileType.OPEN, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidMove2() {
    fm1.startGame(fm1.getDeck(), 8, 4, false);
    fm1.move(PileType.CASCADE, 4, 2, PileType.OPEN, 0);
    fm1.move(PileType.CASCADE, 4, 3, PileType.OPEN, 0);
  }

  @Test
  public void testIsValidPile() {
    fm1.startGame(fm1.getDeck(), 8, 4, false);
    assertTrue(fm1.isValidPile(fm1.getCascadePiles(), 1));
    assertFalse(fm1.isValidPile(fm1.getCascadePiles(), 8));
    assertFalse(fm1.isValidPile(fm1.getCascadePiles(), -1));
  }

  @Test
  public void testGetPileArray() {
    fm1.startGame(fm1.getDeck(), 8, 4, false);
    assertEquals(8, fm1.getPileArray(PileType.CASCADE).size());
    assertEquals(4, fm1.getPileArray(PileType.OPEN).size());
    assertEquals(4, fm1.getPileArray(PileType.FOUNDATION).size());

  }

  @Test
  public void testIsGameOver() {
    this.fm1.startGame(fm1.getDeck(), 8, 4, false);
    assertFalse(this.fm1.isGameOver());
  }

  @Test
  public void testGetNumCardsInFoundationPile() {
    fm1.startGame(fm1.getDeck(), 8, 4, false);
    assertEquals(0, fm1.getNumCardsInFoundationPile(0));
    fm1.move(PileType.CASCADE, 7, 5, PileType.OPEN, 0);
    fm1.move(PileType.CASCADE, 7, 4, PileType.FOUNDATION, 0);
    assertEquals(1, fm1.getNumCardsInFoundationPile(0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeIndexFoundation() {
    this.fm1.startGame(fm1.getDeck(), 8, 4, false);
    fm1.getNumCardsInFoundationPile(-1);
  }

  @Test(expected = IllegalStateException.class)
  public void testNotStartedFoundation() {
    fm1.getNumCardsInFoundationPile(1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLargeIndexFoundation() {
    this.fm1.startGame(fm1.getDeck(), 8, 4, false);
    fm1.getNumCardsInFoundationPile(4);
  }

  @Test
  public void testGetNumCascadePiles() {
    assertEquals(-1, fm1.getNumCascadePiles());
    fm1.startGame(fm1.getDeck(), 8, 4, false);
    assertEquals(8, fm1.getNumCascadePiles());
  }

  @Test
  public void testGetNumCardsInCascadePile() {
    fm1.startGame(fm1.getDeck(), 8, 4, false);
    assertEquals(7, fm1.getNumCardsInCascadePile(0));
    fm1.getCascadePiles().get(0).addCard(aceSpades);
    assertEquals(8, fm1.getNumCardsInCascadePile(0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeIndexCascade() {
    this.fm1.startGame(fm1.getDeck(), 8, 4, false);
    fm1.getNumCardsInCascadePile(-1);
  }

  @Test(expected = IllegalStateException.class)
  public void testNotStartedCascade() {
    fm1.getNumCardsInCascadePile(0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLargeIndexCascade() {
    this.fm1.startGame(fm1.getDeck(), 8, 4, false);
    fm1.getNumCardsInCascadePile(8);
  }

  @Test
  public void testGetNumCardsInOpenPile() {
    fm1.startGame(fm1.getDeck(), 8, 4, false);
    assertEquals(0, fm1.getNumCardsInOpenPile(0));
    fm1.move(PileType.CASCADE, 0, 6, PileType.OPEN, 0);
    assertEquals(1, fm1.getNumCardsInOpenPile(0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeIndexOpen() {
    this.fm1.startGame(fm1.getDeck(), 8, 4, false);
    fm1.getNumCardsInOpenPile(-1);
  }

  @Test(expected = IllegalStateException.class)
  public void testNotStartedOpen() {
    fm1.getNumCardsInOpenPile(0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLargeIndexOpen() {
    this.fm1.startGame(fm1.getDeck(), 8, 4, false);
    fm1.getNumCardsInOpenPile(4);
  }

  @Test
  public void testGetNumOpenPiles() {
    assertEquals(-1, fm1.getNumOpenPiles());
    fm1.startGame(fm1.getDeck(), 8, 4, false);
    assertEquals(4, fm1.getNumOpenPiles());
  }

  @Test
  public void testGetFoundationCardAt() {
    this.fm1.startGame(fm1.getDeck(), 8, 4, false);
    fm1.move(PileType.CASCADE, 7, 5, PileType.OPEN, 0);
    fm1.move(PileType.CASCADE, 7, 4, PileType.FOUNDATION, 0);
    assertEquals(Rank.Ace, fm1.getFoundationCardAt(0, 0).getRank());
    assertEquals(Suit.Diamond, fm1.getFoundationCardAt(0, 0).getSuit());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeIndexFoundationPile() {
    this.fm1.startGame(fm1.getDeck(), 8, 4, false);
    fm1.getFoundationCardAt(-1, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLargeIndexFoundationPile() {
    this.fm1.startGame(fm1.getDeck(), 8, 4, false);
    fm1.getFoundationCardAt(4, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeIndexFoundationCard() {
    this.fm1.startGame(fm1.getDeck(), 8, 4, false);
    fm1.getFoundationCardAt(0, -1);
  }

  @Test(expected = IllegalStateException.class)
  public void testNotStartedFoundationCard() {
    fm1.getFoundationCardAt(0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLargeIndexFoundationCard() {
    this.fm1.startGame(fm1.getDeck(), 8, 4, false);
    fm1.getFoundationCardAt(0, 1);
  }

  @Test
  public void testGetCascadeCardAt() {
    this.fm1.startGame(fm1.getDeck(), 8, 4, false);
    assertEquals(Rank.King, fm1.getCascadeCardAt(1, 3).getRank());
    assertEquals(Suit.Spade, fm1.getCascadeCardAt(1, 3).getSuit());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeIndexCascadePile() {
    this.fm1.startGame(fm1.getDeck(), 8, 4, false);
    fm1.getCascadeCardAt(-1, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLargeIndexCascadePile() {
    this.fm1.startGame(fm1.getDeck(), 8, 4, false);
    fm1.getCascadeCardAt(8, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeIndexCascadeCard() {
    this.fm1.startGame(fm1.getDeck(), 8, 4, false);
    fm1.getCascadeCardAt(0, -1);
  }

  @Test(expected = IllegalStateException.class)
  public void testNotStartedCascadeCard() {
    fm1.getCascadeCardAt(0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLargeIndexCascadeCard() {
    this.fm1.startGame(fm1.getDeck(), 8, 4, false);
    fm1.getCascadeCardAt(0, 13);
  }

  @Test
  public void testGetOpenCardAt() {
    this.fm1.startGame(fm1.getDeck(), 8, 4, false);
    assertEquals(null, this.fm1.getOpenCardAt(0));
    fm1.move(PileType.CASCADE, 1, 6, PileType.OPEN, 0);
    assertEquals(Rank.Jack, fm1.getOpenCardAt(0).getRank());
    assertEquals(Suit.Diamond, fm1.getOpenCardAt(0).getSuit());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeIndexOpenCard() {
    this.fm1.startGame(fm1.getDeck(), 8, 4, false);
    fm1.getOpenCardAt(-1);
  }

  @Test(expected = IllegalStateException.class)
  public void testNotStartedOpenCard() {
    fm1.getOpenCardAt(0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLargeIndexOpenCard() {
    this.fm1.startGame(fm1.getDeck(), 8, 4, false);
    fm1.getOpenCardAt(4);
  }
}