package cs3500.freecell.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import cs3500.freecell.model.PileType;
import cs3500.freecell.model.hw02.Card;
import cs3500.freecell.model.hw02.Rank;
import cs3500.freecell.model.hw02.SimpleFreecellModel;
import cs3500.freecell.model.hw02.Suit;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the SimpleFreecellController class.
 */
public class SimpleFreecellControllerTest {

  private MockModel model1;
  private SimpleFreecellModel modelReal;
  private SimpleFreecellController controller1;
  private ArrayList<Card> emptyDeck;
  private Readable rd1;
  private Card aceSpades;
  private String startBoard;

  @Before
  public void initData() {
    this.model1 = new MockModel();
    this.model1.startGame(model1.getDeck(), 4, 4, false);
    this.modelReal = new SimpleFreecellModel();
    this.rd1 = new InputStreamReader(System.in);
    this.controller1 = new SimpleFreecellController(model1, rd1, System.out);
    this.emptyDeck = new ArrayList<>();
    this.aceSpades = new Card(Rank.Ace, Suit.Spade);
    this.startBoard = ("F1:" + "\n" +
        "F2:" + "\n" +
        "F3:" + "\n" +
        "F4:" + "\n" +
        "O1:" + "\n" +
        "O2:" + "\n" +
        "O3:" + "\n" +
        "O4:" + "\n" +
        "C1: A♥, 9♥, 4♠, Q♠, 7♣, 2♦, 10♦" + "\n" +
        "C2: 2♥, 10♥, 5♠, K♠, 8♣, 3♦, J♦" + "\n" +
        "C3: 3♥, J♥, 6♠, A♣, 9♣, 4♦, Q♦" + "\n" +
        "C4: 4♥, Q♥, 7♠, 2♣, 10♣, 5♦, K♦" + "\n" +
        "C5: 5♥, K♥, 8♠, 3♣, J♣, 6♦" + "\n" +
        "C6: 6♥, A♠, 9♠, 4♣, Q♣, 7♦" + "\n" +
        "C7: 7♥, 2♠, 10♠, 5♣, K♣, 8♦" + "\n" +
        "C8: 8♥, 3♠, J♠, 6♣, A♦, 9♦");
  }

  /*@Test(expected = IOException.class)
  public void testRenderMessageException() throws IOException {
    controller1.getView().renderBoard();
  }*/

  /*@Test(expected = IOException.class)
  public void testRenderMessageException2() throws IOException {
    controller1.getView().renderMessage("は");
  }*/

  @Test
  public void testValidPile() {
    assertTrue(controller1.validPile("C1"));
    assertTrue(controller1.validPile("F1"));
    assertTrue(controller1.validPile("O1"));
    assertFalse(controller1.validPile("Z"));
    assertFalse(controller1.validPile("Q"));
    assertFalse(controller1.validPile("1"));
  }

  @Test
  public void testValidCardIdx() {
    assertTrue(controller1.validCardIdx("1"));
    assertFalse(controller1.validCardIdx("0"));
  }

  @Test
  public void testQuit() {
    assertFalse(controller1.quitGame("f"));
    assertTrue(controller1.quitGame("Q"));
    assertFalse(controller1.getGameState());
  }

  @Test
  public void testquit() {
    assertFalse(controller1.quitGame("f"));
    assertTrue(controller1.quitGame("Q"));
    assertFalse(controller1.getGameState());
  }

  @Test
  public void testQuitSource() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("Q1 -1 O1");
    SimpleFreecellController s = new SimpleFreecellController(modelReal, in, out);
    s.playGame(modelReal.getDeck(), 8, 4, false);
    assertEquals(startBoard
        + "\nGame quit prematurely.\n", out.toString());
    assertFalse(s.getGameState());
  }

  @Test
  public void testQuitIdx() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("C1 Q O1");
    SimpleFreecellController s = new SimpleFreecellController(modelReal, in, out);
    s.playGame(modelReal.getDeck(), 8, 4, false);
    assertEquals(startBoard
        + "\nGame quit prematurely.\n", out.toString());
    assertFalse(s.getGameState());
  }

  @Test
  public void testQuitDest() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("C1 1 Q");
    SimpleFreecellController s = new SimpleFreecellController(modelReal, in, out);
    s.playGame(modelReal.getDeck(), 8, 4, false);
    assertEquals(startBoard
        + "\nGame quit prematurely.\n", out.toString());
    assertFalse(s.getGameState());
  }

  @Test
  public void testGetPileType() {
    assertEquals(PileType.CASCADE, controller1.getPileType("C"));
    assertEquals(PileType.FOUNDATION, controller1.getPileType("F"));
    assertEquals(PileType.OPEN, controller1.getPileType("O"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPileTypeException() {
    assertEquals(PileType.CASCADE, controller1.getPileType("Z"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlayGameNullDeck() {
    controller1.playGame(null, 4, 4, true);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlayGameEmptyDeck() {
    controller1.playGame(emptyDeck, 4, 4, true);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullModelConstructor() {
    new SimpleFreecellController(null, rd1, System.out);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullReadableConstructor() {
    new SimpleFreecellController(model1, null, System.out);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullAppendableConstructor() {
    new SimpleFreecellController(model1, rd1, null);
  }

  @Test
  public void testQInString() {
    assertTrue(controller1.quitGame("CQ"));
  }

  @Test
  public void testQuitMessage() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("C1 1 Q1");
    SimpleFreecellController s = new SimpleFreecellController(modelReal, in, out);
    s.quitGame("Q1");
    assertEquals("Game quit prematurely.\n", out.toString());
  }

  @Test
  public void testQAfterCharacter() {
    assertTrue(controller1.quitGame("Fq"));
    assertFalse(controller1.getGameState());
  }

  @Test
  public void testFailStartGame() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("C1 1 Q1");
    emptyDeck.add(aceSpades);
    SimpleFreecellController s = new SimpleFreecellController(modelReal, in, out);
    s.playGame(emptyDeck, 4, 4, false);
    assertEquals("Could not start game.", out.toString());
  }

  @Test
  public void testInvalidMove() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("C1 1 O1");
    SimpleFreecellController s = new SimpleFreecellController(modelReal, in, out);
    s.playGame(modelReal.getDeck(), 8, 4, false);
    assertEquals(startBoard + "\nInvalid move, try again."
        + "\nInvalid Pile Type\n\n", out.toString());
  }

  @Test
  public void testMove() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("C1 12 O1");
    SimpleFreecellController s = new SimpleFreecellController(modelReal, in, out);
    s.playGame(modelReal.getDeck(), 8, 4, false);
    assertEquals(0, modelReal.getNumCardsInOpenPile(0));
  }

  @Test
  public void testInvalidCardIdx() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("C1 -1 O1");
    SimpleFreecellController s = new SimpleFreecellController(modelReal, in, out);
    s.playGame(modelReal.getDeck(), 8, 4, false);
    assertEquals(startBoard
        + "\nRe Input your Card Index and Destination Pile parameters!\n"
        + "Re Input your Card Index and Destination Pile parameters!\n", out.toString());
  }

  @Test
  public void testRunGame() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("C1 -1 O1");
    SimpleFreecellController s = new SimpleFreecellController(modelReal, in, out);
    s.playGame(modelReal.getDeck(), 8, 4, false);
    assertEquals(startBoard
            + "\n Re Input your Card Index and Destination Pile parameters!\n"
            + "Re Input your Card Index and Destination Pile parameters!\n", out.toString(),
        out.toString());
  }
}