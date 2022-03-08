package cs3500.freecell.model;

import cs3500.freecell.model.hw02.Card;
import cs3500.freecell.model.hw02.SimpleFreecellModel;
import cs3500.freecell.model.hw04.MultiMoveFreecellModel;

/**
 * Freecell Model Factory Class.
 */
public class FreecellModelCreator {

  /**
   * enumerator for GameTypes.
   */
  public enum GameType { SINGLEMOVE, MULTIMOVE }

  ;

  /**
   * Freecell model factory method returns a new Model based on the specified game type.
   *
   * @param type the gametype supplied by the user
   * @return a new specified game of the specified type.
   */
  public static FreecellModel<Card> create(GameType type) {
    FreecellModel<Card> returnModel = null;
    if (type.equals(GameType.SINGLEMOVE)) {
      returnModel = new SimpleFreecellModel();
    }
    else if (type.equals(GameType.MULTIMOVE)) {
      returnModel = new MultiMoveFreecellModel();
    } else {
      throw new IllegalArgumentException("Invalid game Type");
    }
    return returnModel;
  }
}