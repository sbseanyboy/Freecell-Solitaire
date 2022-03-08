package cs3500.freecell.view;

import static org.junit.Assert.assertEquals;

import cs3500.freecell.model.PileType;
import cs3500.freecell.model.hw02.SimpleFreecellModel;
import org.junit.Test;

/**
 * class to test the FreecellTextView class.
 */
public class FreecellTextViewTest {

  SimpleFreecellModel fm1 = new SimpleFreecellModel();

  @Test
  public void testToString() {
    fm1.startGame(fm1.getDeck(), 8, 4, false);
    assertEquals(
        ("F1:" + "\n" +
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
            "C8: 8♥, 3♠, J♠, 6♣, A♦, 9♦"), new FreecellTextView(fm1).toString());

    fm1.move(PileType.CASCADE, 0, 0, PileType.FOUNDATION, 0);
    assertEquals(
        ("F1: A♥" + "\n" +
            "F2:" + "\n" +
            "F3:" + "\n" +
            "F4:" + "\n" +
            "O1:" + "\n" +
            "O2:" + "\n" +
            "O3:" + "\n" +
            "O4:" + "\n" +
            "C1: 9♥, 4♠, Q♠, 7♣, 2♦, 10♦" + "\n" +
            "C2: 2♥, 10♥, 5♠, K♠, 8♣, 3♦, J♦" + "\n" +
            "C3: 3♥, J♥, 6♠, A♣, 9♣, 4♦, Q♦" + "\n" +
            "C4: 4♥, Q♥, 7♠, 2♣, 10♣, 5♦, K♦" + "\n" +
            "C5: 5♥, K♥, 8♠, 3♣, J♣, 6♦" + "\n" +
            "C6: 6♥, A♠, 9♠, 4♣, Q♣, 7♦" + "\n" +
            "C7: 7♥, 2♠, 10♠, 5♣, K♣, 8♦" + "\n" +
            "C8: 8♥, 3♠, J♠, 6♣, A♦, 9♦"), new FreecellTextView(fm1).toString());

    fm1.move(PileType.CASCADE, 1, 0, PileType.FOUNDATION, 0);
    assertEquals(
        ("F1: A♥, 2♥" + "\n" +
            "F2:" + "\n" +
            "F3:" + "\n" +
            "F4:" + "\n" +
            "O1:" + "\n" +
            "O2:" + "\n" +
            "O3:" + "\n" +
            "O4:" + "\n" +
            "C1: 9♥, 4♠, Q♠, 7♣, 2♦, 10♦" + "\n" +
            "C2: 10♥, 5♠, K♠, 8♣, 3♦, J♦" + "\n" +
            "C3: 3♥, J♥, 6♠, A♣, 9♣, 4♦, Q♦" + "\n" +
            "C4: 4♥, Q♥, 7♠, 2♣, 10♣, 5♦, K♦" + "\n" +
            "C5: 5♥, K♥, 8♠, 3♣, J♣, 6♦" + "\n" +
            "C6: 6♥, A♠, 9♠, 4♣, Q♣, 7♦" + "\n" +
            "C7: 7♥, 2♠, 10♠, 5♣, K♣, 8♦" + "\n" +
            "C8: 8♥, 3♠, J♠, 6♣, A♦, 9♦"), new FreecellTextView(fm1).toString());

    fm1.move(PileType.CASCADE, 2, 0, PileType.OPEN, 0);
    assertEquals(
        ("F1: A♥, 2♥" + "\n" +
            "F2:" + "\n" +
            "F3:" + "\n" +
            "F4:" + "\n" +
            "O1: 3♥" + "\n" +
            "O2:" + "\n" +
            "O3:" + "\n" +
            "O4:" + "\n" +
            "C1: 9♥, 4♠, Q♠, 7♣, 2♦, 10♦" + "\n" +
            "C2: 10♥, 5♠, K♠, 8♣, 3♦, J♦" + "\n" +
            "C3: J♥, 6♠, A♣, 9♣, 4♦, Q♦" + "\n" +
            "C4: 4♥, Q♥, 7♠, 2♣, 10♣, 5♦, K♦" + "\n" +
            "C5: 5♥, K♥, 8♠, 3♣, J♣, 6♦" + "\n" +
            "C6: 6♥, A♠, 9♠, 4♣, Q♣, 7♦" + "\n" +
            "C7: 7♥, 2♠, 10♠, 5♣, K♣, 8♦" + "\n" +
            "C8: 8♥, 3♠, J♠, 6♣, A♦, 9♦"), new FreecellTextView(fm1).toString());

    fm1.move(PileType.OPEN, 0, 0, PileType.FOUNDATION, 0);
    assertEquals(
        ("F1: A♥, 2♥, 3♥" + "\n" +
            "F2:" + "\n" +
            "F3:" + "\n" +
            "F4:" + "\n" +
            "O1:" + "\n" +
            "O2:" + "\n" +
            "O3:" + "\n" +
            "O4:" + "\n" +
            "C1: 9♥, 4♠, Q♠, 7♣, 2♦, 10♦" + "\n" +
            "C2: 10♥, 5♠, K♠, 8♣, 3♦, J♦" + "\n" +
            "C3: J♥, 6♠, A♣, 9♣, 4♦, Q♦" + "\n" +
            "C4: 4♥, Q♥, 7♠, 2♣, 10♣, 5♦, K♦" + "\n" +
            "C5: 5♥, K♥, 8♠, 3♣, J♣, 6♦" + "\n" +
            "C6: 6♥, A♠, 9♠, 4♣, Q♣, 7♦" + "\n" +
            "C7: 7♥, 2♠, 10♠, 5♣, K♣, 8♦" + "\n" +
            "C8: 8♥, 3♠, J♠, 6♣, A♦, 9♦"), new FreecellTextView(fm1).toString());
  }
}