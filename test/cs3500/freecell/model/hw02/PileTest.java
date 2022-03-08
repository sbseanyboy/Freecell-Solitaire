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
 * class for testing Pile Class.
 */
public class PileTest {

  SimpleFreecellModel fm1;
  ArrayList<Card> fullDeck;
  ArrayList<Card> emptyDeck;
  Pile fullCascade;
  Pile fullOpen;
  Pile fullFoundation;
  Pile emptyCascade;
  Pile emptyOpen;
  Pile emptyFoundation;
  Card aceSpades;
  Card twoSpades;
  Card queenClubs;
  Pile aceSpadesFoundation;

  /**
   * initData method.
   */
  @Before
  public void initData() {
    this.fm1 = new SimpleFreecellModel();
    this.fullDeck = fm1.getDeck();
    this.emptyDeck = new ArrayList<>();
    this.fullCascade = new Pile(PileType.CASCADE, fullDeck);
    this.fullOpen = new Pile(PileType.OPEN, new ArrayList<Card>(Arrays.asList(aceSpades)));
    this.fullFoundation = new Pile(PileType.FOUNDATION, fullDeck);
    this.emptyCascade = new Pile(PileType.CASCADE, emptyDeck);
    this.emptyOpen = new Pile(PileType.OPEN, emptyDeck);
    this.emptyFoundation = new Pile(PileType.FOUNDATION, emptyDeck);
    this.aceSpades = new Card(Rank.Ace, Suit.Spade);
    this.twoSpades = new Card(Rank.Two, Suit.Spade);
    this.aceSpadesFoundation = new Pile(PileType.FOUNDATION,
        new ArrayList<>(Arrays.asList(aceSpades)));
    this.queenClubs = new Card(Rank.Queen, Suit.Club);
  }

  @Test // tests the getCards() method
  public void testGetCards() {
    assertEquals(fullDeck, fullCascade.getCards());
  }

  @Test // tests the getType() method
  public void testGetType() {
    assertEquals(PileType.CASCADE, fullCascade.getType());
    assertEquals(PileType.OPEN, emptyOpen.getType());
    assertEquals(PileType.FOUNDATION, fullFoundation.getType());
  }

  @Test // tests the addCard() method
  public void testAddCard() {
    assertEquals(emptyDeck, emptyOpen.getCards());
    emptyOpen.addCard(aceSpades);
    assertEquals(new ArrayList<Card>(Arrays.asList(aceSpades)), emptyOpen.getCards());
  }

  @Test // tests the isValidMove() method
  public void testIsValidMove() {
    // moving an ace card to an empty foundation
    assertTrue(emptyFoundation.isValidMove(fullCascade, aceSpades));
    // moving a non ace to an empty foundation
    assertFalse(emptyFoundation.isValidMove(fullCascade, twoSpades));
    // moving a card to an empty cascade
    assertTrue(emptyCascade.isValidMove(fullOpen, aceSpades));
    // moving a card to an empty open
    assertTrue(emptyOpen.isValidMove(fullCascade, aceSpades));
    // moving a card to an open with a card already in it
    assertFalse(fullOpen.isValidMove(fullCascade, aceSpades));
    // moving a card from a foundation
    assertFalse(emptyCascade.isValidMove(fullFoundation, aceSpades));
    // validly moving a card to a foundation with a card in it
    assertTrue(aceSpadesFoundation.isValidMove(fullOpen, twoSpades));
    // invalidly moving a card to a foundation with a card in it
    assertFalse(aceSpadesFoundation.isValidMove(fullOpen, aceSpades));
    // validly moving a card to a cascade (correct color and rank)
    assertTrue(fullCascade.isValidMove(fullOpen, queenClubs));
    // invalidly moving a card to a cascade (incorrect color/value to move)
    assertFalse(fullCascade.isValidMove(fullOpen, aceSpades));
  }
}