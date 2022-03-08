package cs3500.freecell.model.hw02;

/**
 * represents the Suits a card can be.
 */
public enum Suit {
  Heart("red", "♥"),
  Spade("black", "♠"),
  Club("black", "♣"),
  Diamond("red", "♦");

  final String color;
  final String symbol;


  /**
   * builds a Suit enum with its color and symbol.
   *
   * @param color  the color of this suit
   * @param symbol string representation of the suit's symbol
   */
  Suit(String color, String symbol) {
    this.color = color;
    this.symbol = symbol;
  }

  /**
   * gets this suit's symbol.
   *
   * @return this symbol
   */
  public String getSymbol() {
    return this.symbol;
  }

  /**
   * gets this suit's color.
   *
   * @return this color
   */
  public String getColor() {
    return this.color;
  }
}
