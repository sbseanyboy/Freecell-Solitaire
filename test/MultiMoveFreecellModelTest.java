import static org.junit.Assert.assertEquals;

import cs3500.freecell.controller.SimpleFreecellController;
import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.FreecellModelCreator;
import cs3500.freecell.model.FreecellModelCreator.GameType;
import cs3500.freecell.model.PileType;
import cs3500.freecell.model.hw02.Card;
import cs3500.freecell.model.hw02.Rank;
import cs3500.freecell.model.hw02.Suit;
import cs3500.freecell.view.FreecellTextView;
import java.io.Reader;
import java.io.StringReader;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests class for Multi Move Freecell Model.
 */
public class MultiMoveFreecellModelTest {

  private FreecellModel<Card> multi;
  private FreecellModel<Card> simple;

  @Before
  public void initData() {
    multi = new FreecellModelCreator().create(GameType.MULTIMOVE);
    simple = new FreecellModelCreator().create(GameType.SINGLEMOVE);
  }

  @Test
  public void testMoveOneCard() {
    multi.startGame(multi.getDeck(), 4, 4, false);
    this.multi.move(PileType.CASCADE, 0, 12, PileType.OPEN, 0);
    assertEquals(Suit.Diamond, multi.getOpenCardAt(0).getSuit());
    assertEquals(Rank.Ten, multi.getOpenCardAt(0).getRank());
  }

  @Test
  public void testMoveMultipleCards() {
    multi.startGame(multi.getDeck(), 8, 4, false);
    // move the 6 diamonds to open to reveal j of clubs
    this.multi.move(PileType.CASCADE, 4, 5, PileType.OPEN, 0);
    // move the 8 diamonds to open to reveal k of clubs
    this.multi.move(PileType.CASCADE, 6, 5, PileType.OPEN, 1);
    // move the j clubs on top of the q diamonds to create a valid stack
    this.multi.move(PileType.CASCADE, 4, 4, PileType.CASCADE, 2);
    // the multi move:
    // move the q diamonds and j clubs valid stack on top of the k clubs exposed before.
    this.multi.move(PileType.CASCADE, 2, 6, PileType.CASCADE, 6);
    assertEquals(Rank.Jack, multi.getCascadeCardAt(6, 6).getRank());
    assertEquals(Suit.Club, multi.getCascadeCardAt(6, 6).getSuit());
    assertEquals(Rank.Queen, multi.getCascadeCardAt(6, 5).getRank());
    assertEquals(Suit.Diamond, multi.getCascadeCardAt(6, 5).getSuit());
    assertEquals(Rank.King, multi.getCascadeCardAt(6, 4).getRank());
    assertEquals(Suit.Club, multi.getCascadeCardAt(6, 4).getSuit());
  }

  @Test
  public void testMoveMultipleCardsWithCascade() {
    multi.startGame(multi.getDeck(), 8, 5, false);
    // move the 6 diamonds to open to reveal j of clubs
    this.multi.move(PileType.CASCADE, 4, 5, PileType.OPEN, 0);
    // move the 8 diamonds to open to reveal k of clubs
    this.multi.move(PileType.CASCADE, 6, 5, PileType.OPEN, 1);
    // move the j clubs on top of the q diamonds to create a valid stack
    this.multi.move(PileType.CASCADE, 4, 4, PileType.CASCADE, 2);
    // moves to fill open piles and empty a cascade
    this.multi.move(PileType.CASCADE, 4, 3, PileType.OPEN, 2);
    this.multi.move(PileType.CASCADE, 4, 2, PileType.CASCADE, 7);
    this.multi.move(PileType.CASCADE, 4, 1, PileType.OPEN, 3);
    this.multi.move(PileType.CASCADE, 4, 0, PileType.OPEN, 4);
    // the multi move:
    // move the q diamonds and j clubs valid stack on top of the k clubs exposed before.
    this.multi.move(PileType.CASCADE, 2, 6, PileType.CASCADE, 6);
    assertEquals(Rank.Jack, multi.getCascadeCardAt(6, 6).getRank());
    assertEquals(Suit.Club, multi.getCascadeCardAt(6, 6).getSuit());
    assertEquals(Rank.Queen, multi.getCascadeCardAt(6, 5).getRank());
    assertEquals(Suit.Diamond, multi.getCascadeCardAt(6, 5).getSuit());
    assertEquals(Rank.King, multi.getCascadeCardAt(6, 4).getRank());
    assertEquals(Suit.Club, multi.getCascadeCardAt(6, 4).getSuit());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidStackMove() {
    multi.startGame(multi.getDeck(), 4, 4, false);
    this.multi.move(PileType.CASCADE, 0, 9, PileType.OPEN, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStackLargerThanOpenings() {
    multi.startGame(multi.getDeck(), 4, 4, false);
    // move the 6 diamonds to open to reveal j of clubs
    this.multi.move(PileType.CASCADE, 4, 5, PileType.OPEN, 0);
    // move the 8 diamonds to open to reveal k of clubs
    this.multi.move(PileType.CASCADE, 6, 5, PileType.OPEN, 1);
    // move the j clubs on top of the q diamonds to create a valid stack
    this.multi.move(PileType.CASCADE, 4, 4, PileType.CASCADE, 2);
    // move to fill the open piles and leaving no inter piles
    this.multi.move(PileType.CASCADE, 0, 4, PileType.OPEN, 2);
    this.multi.move(PileType.CASCADE, 1, 4, PileType.OPEN, 3);
    // the multi move:
    // move the q diamonds and j clubs valid stack on top of the k clubs exposed before.
    // should fail because there are not enough empty piles.
    this.multi.move(PileType.CASCADE, 2, 6, PileType.CASCADE, 6);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMultimoveOnSingle() {
    simple.startGame(simple.getDeck(), 4, 4, false);
    this.simple.move(PileType.CASCADE, 0, 9, PileType.CASCADE, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidSourceIndex() {
    multi.startGame(multi.getDeck(), 4, 4, false);
    this.multi.move(PileType.CASCADE, 4, 9, PileType.CASCADE, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidDestIndex() {
    multi.startGame(multi.getDeck(), 4, 4, false);
    this.multi.move(PileType.CASCADE, 0, 9, PileType.CASCADE, 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidCardIndex() {
    multi.startGame(multi.getDeck(), 4, 4, false);
    this.multi.move(PileType.CASCADE, 0, 13, PileType.CASCADE, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void moveToFullOpen() {
    multi.startGame(multi.getDeck(), 4, 4, false);
    this.multi.move(PileType.CASCADE, 0, 12, PileType.OPEN, 0);
    this.multi.move(PileType.CASCADE, 0, 11, PileType.OPEN, 0);
  }

  @Test
  public void testController() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("C1 13 O1");
    SimpleFreecellController s = new SimpleFreecellController(multi, in, out);
    s.playGame(multi.getDeck(), 4, 4, false);
    assertEquals(1, multi.getNumCardsInOpenPile(0));
    assertEquals(
        "F1:\n"
            + "F2:\n"
            + "F3:\n"
            + "F4:\n"
            + "O1:\n"
            + "O2:\n"
            + "O3:\n"
            + "O4:\n"
            + "C1: A♥, 5♥, 9♥, K♥, 4♠, 8♠, Q♠, 3♣, 7♣, J♣, 2♦, 6♦, 10♦\n"
            + "C2: 2♥, 6♥, 10♥, A♠, 5♠, 9♠, K♠, 4♣, 8♣, Q♣, 3♦, 7♦, J♦\n"
            + "C3: 3♥, 7♥, J♥, 2♠, 6♠, 10♠, A♣, 5♣, 9♣, K♣, 4♦, 8♦, Q♦\n"
            + "C4: 4♥, 8♥, Q♥, 3♠, 7♠, J♠, 2♣, 6♣, 10♣, A♦, 5♦, 9♦, K♦\n"
            + "F1:\n"
            + "F2:\n"
            + "F3:\n"
            + "F4:\n"
            + "O1: 10♦\n"
            + "O2:\n"
            + "O3:\n"
            + "O4:\n"
            + "C1: A♥, 5♥, 9♥, K♥, 4♠, 8♠, Q♠, 3♣, 7♣, J♣, 2♦, 6♦\n"
            + "C2: 2♥, 6♥, 10♥, A♠, 5♠, 9♠, K♠, 4♣, 8♣, Q♣, 3♦, 7♦, J♦\n"
            + "C3: 3♥, 7♥, J♥, 2♠, 6♠, 10♠, A♣, 5♣, 9♣, K♣, 4♦, 8♦, Q♦\n"
            + "C4: 4♥, 8♥, Q♥, 3♠, 7♠, J♠, 2♣, 6♣, 10♣, A♦, 5♦, 9♦, K♦\n", out.toString());
  }

  @Test(expected = Exception.class)
  public void testControllerInvalid() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("C1 15 O1");
    SimpleFreecellController s = new SimpleFreecellController(multi, in, out);
    s.playGame(multi.getDeck(), 4, 4, false);
  }

  @Test
  public void testControllerQuit() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("q");
    SimpleFreecellController s = new SimpleFreecellController(multi, in, out);
    s.playGame(multi.getDeck(), 4, 4, false);
    assertEquals("F1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: A♥, 5♥, 9♥, K♥, 4♠, 8♠, Q♠, 3♣, 7♣, J♣, 2♦, 6♦, 10♦\n"
        + "C2: 2♥, 6♥, 10♥, A♠, 5♠, 9♠, K♠, 4♣, 8♣, Q♣, 3♦, 7♦, J♦\n"
        + "C3: 3♥, 7♥, J♥, 2♠, 6♠, 10♠, A♣, 5♣, 9♣, K♣, 4♦, 8♦, Q♦\n"
        + "C4: 4♥, 8♥, Q♥, 3♠, 7♠, J♠, 2♣, 6♣, 10♣, A♦, 5♦, 9♦, K♦\n"
        + "Game quit prematurely.\n", out.toString());
  }

  @Test(expected = Exception.class)
  public void moveToFullOpen2() {
    for (int cascades = 4; cascades < 10; cascades++) {
      for (int open = 4; open < 10; open++) {
        for (int i = 0; i < open; i++) {
          for (int j = 0; j < cascades; j++) {
            multi.startGame(multi.getDeck(), cascades, open, false);
            int[] numCardsInCascadePiles = new int[cascades];
            String output = new FreecellTextView(multi).toString();
            String[] lines = output.split("\n");
            for (int k = 0; k < cascades; k++) {
              String[] commas = lines[lines.length - 1 - k].split(",");
              numCardsInCascadePiles[cascades - 1 - k] = commas.length;
            }
            this.multi.move(PileType.CASCADE, j, numCardsInCascadePiles[j] - 1, PileType.OPEN, i);
            this.multi.move(PileType.CASCADE, j, numCardsInCascadePiles[j] - 2, PileType.OPEN, i);
          }
        }
      }
    }
  }
}