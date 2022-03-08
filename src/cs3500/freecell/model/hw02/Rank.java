package cs3500.freecell.model.hw02;

/**
 * represents  the Rank a card can be.
 */
public enum Rank {
  Ace(1, "A"),
  Two(2, "2"),
  Three(3, "3"),
  Four(4, "4"),
  Five(5, "5"),
  Six(6, "6"),
  Seven(7, "7"),
  Eight(8, "8"),
  Nine(9, "9"),
  Ten(10, "10"),
  Jack(11, "J"),
  Queen(12, "Q"),
  King(13, "K");

  private final int value;
  private final String symbol;

  /**
   * builds a Rank enum with the value and symbol for the rank.
   *
   * @param value  integer value of the card's rank
   * @param symbol string symbol of the card's rank
   */
  Rank(int value, String symbol) {
    this.value = value;
    this.symbol = symbol;
  }

  /**
   * getter for Rank values.
   *
   * @return this value
   */
  public int getValue() {
    return this.value;
  }

  /**
   * getter for Rank symbols.
   *
   * @return this symbol
   */
  public String getSymbol() {
    return this.symbol;
  }
}
