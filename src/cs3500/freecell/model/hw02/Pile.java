package cs3500.freecell.model.hw02;

import cs3500.freecell.model.PileType;
import java.util.ArrayList;

/**
 * represents a pile of Cards in a game of Freecell Solitaire.
 */
public class Pile {

  private final PileType type;
  private final ArrayList<Card> cards;

  /**
   * constructor for a pile which has a type and a list of cards.
   *
   * @param type  the type of pile
   * @param cards the list of cards held by the pile
   */
  public Pile(PileType type, ArrayList<Card> cards) {
    this.type = type;
    this.cards = cards;
  }

  /**
   * gets this list of cards.
   *
   * @return ArrayList
   */
  public ArrayList<Card> getCards() {
    return this.cards;
  }

  /**
   * gets this PileType.
   *
   * @return PileType
   */
  public PileType getType() {
    return this.type;
  }

  /**
   * Adds the supplied card to this list of cards.
   *
   * @param c for Card
   */
  public void addCard(Card c) {
    this.cards.add(c);
  }

  /**
   * Removes the supplied card from this list of cards.
   *
   * @param c for Card
   */
  public void removeCard(Card c) {
    this.cards.remove(c);
  }

  /**
   * determines whether this move is valid (see rules).
   *
   * @param source the source pile from which the card is taken
   * @param card   the card being moved
   * @return whether this move is valid
   */
  public boolean isValidMove(Pile source, Card card) {

    PileType sourceType = source.getType();
    PileType destType = this.getType();
    Boolean returnBool = false;

    // cannot move card from foundation pile
    if (!sourceType.equals(PileType.FOUNDATION)) {
      // if the destination pile is empty, the move is always legal
      if (this.getCards().isEmpty() && !destType.equals(PileType.FOUNDATION)) {
        returnBool = true;
      } else if (this.getCards().isEmpty() && destType.equals(PileType.FOUNDATION)) {
        returnBool = (card.getRank().getValue() == 1);
      } else {
        switch (destType) {

          case OPEN:
            // always valid as long as the pile is empty
            if (!this.getCards().isEmpty()) {
              returnBool = false;
            }
            break;
          case FOUNDATION:
            Card prevCard = this.getCards().get(this.getCards().size() - 1);
            // is the previous card the same suit and 1 less in value?
            returnBool = (prevCard.getSuit().equals(card.getSuit())) && (
                card.getRank().getValue() - prevCard.getRank().getValue() == 1);
            break;

          case CASCADE:
            Card previousCard = this.getCards().get(this.getCards().size() - 1);
            // is the previous card a different color and 1 more in value?
            returnBool = (previousCard.getSuit().getColor() != card.getSuit().getColor()) && (
                previousCard.getRank().getValue() - card.getRank().getValue() == 1);
            break;

          default:
            throw new IllegalArgumentException("Invalid destination pile type");
        }
      }
    }
    return returnBool;
  }
}
