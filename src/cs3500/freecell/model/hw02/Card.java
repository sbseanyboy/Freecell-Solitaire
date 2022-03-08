package cs3500.freecell.model.hw02;

/**
 * represents a Card in a game of Freecell Solitaire.
 */
public class Card {

  private final Suit suit;
  private final Rank rank;

  /**
   * constructs a card with a {@code suit} and {@code rank} parameter.
   *
   * @param suit the suit of the card
   * @param rank the rank of the card
   */
  public Card(Rank rank, Suit suit) {
    this.suit = suit;
    this.rank = rank;
  }

  /**
   * return the card represented as a string, i.e. an Ace of Spades would return "A♠"
   *
   * @return the suit and rank of a card as a string
   */
  public String toString() {
    return this.rank.getSymbol() + this.suit.getSymbol();
  }

  /**
   * return the card's rank.
   *
   * @return the rank of this card
   */
  public Rank getRank() {
    return this.rank;
  }

  /**
   * return the card's suit.
   *
   * @return the suit of this card
   */
  public Suit getSuit() {
    return this.suit;
  }
}
