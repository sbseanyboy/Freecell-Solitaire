package cs3500.freecell.model.hw04;

import cs3500.freecell.model.PileType;
import cs3500.freecell.model.hw02.Card;
import cs3500.freecell.model.hw02.Pile;
import cs3500.freecell.model.hw02.SimpleFreecellModel;
import java.util.ArrayList;

/**
 * Class representing a game of multimove freecell, where moving multiple cards in one move is valid
 * by specifying the index of the top card of the stack to be moved.
 */
public class MultiMoveFreecellModel extends SimpleFreecellModel {

  /**
   * moves the card or multiple cards so long as the move is valid.
   *
   * @param source         the type of the source pile see @link{PileType}
   * @param pileNumber     the pile number of the given type, starting at 0
   * @param cardIndex      the index of the card to be moved from the source pile, starting at 0
   * @param destination    the type of the destination pile (see
   * @param destPileNumber the pile number of the given type, starting at 0
   * @throws IllegalArgumentException if the move is illegal or impossible
   * @throws IllegalStateException    if the game hasn't started
   */
  @Override
  public void move(PileType source, int pileNumber, int cardIndex, PileType destination,
      int destPileNumber) throws IllegalArgumentException, IllegalStateException {

    // is the game started?
    if (!this.isStarted) {
      throw new IllegalStateException("Game has not started yet");
    }
    // is the pile valid? does it exist?
    if (!isValidPile(getPileArray(source), pileNumber) || !isValidPile(getPileArray(destination),
        destPileNumber)) {
      throw new IllegalArgumentException("Index invalid for selected pile types");
    }
    // is the card valid? does it exist?
    if (!isValidCard(getPileArray(source).get(pileNumber), cardIndex)) {
      throw new IllegalArgumentException("Card index invalid for source pile");
    } else {
      Pile sourcePile = getPileArray(source).get(pileNumber);
      Pile destinationPile = getPileArray(destination).get(destPileNumber);
      int maximum = this.maxMove();
      ArrayList<Card> moveStack = this.getStack(sourcePile, cardIndex);
      if (this.isValidStack(moveStack, maximum)) {
        this.moveMultiCards(moveStack, sourcePile, destinationPile);
      } else {
        throw new IllegalArgumentException("invalid stack move");
      }
    }
  }

  /**
   * a method to get all the empty piles in this game of Freecell Solitaire.
   *
   * @return
   */
  private int maxMove() {
    // initialize the number of empty piles to 0
    int numEmptyOpen = 0;
    int numEmptyCascade = 0;
    // iterate through cascade piles and adds empty ones
    for (int i = 0; i < getNumCascadePiles(); i++) {
      if (cascadePiles.get(i).getCards().isEmpty()) {
        numEmptyCascade += 1;
      }
    }
    // iterates through open piles and adds empty ones
    for (int i = 0; i < getNumOpenPiles(); i++) {
      if (openPiles.get(i).getCards().isEmpty()) {
        numEmptyOpen += 1;
      }
    }
    return (int) ((numEmptyOpen + 1) * Math.pow(2, numEmptyCascade));
  }

  /**
   * returns the stack of cards to be moved when specified a card index that has cards below it.
   *
   * @param source the pile to take the stack from
   * @param index  the index of the top card of the stack to be moved
   * @return
   */
  private ArrayList<Card> getStack(Pile source, int index) {
    // gets the stack of cards to be moved from the source pile starting at the index
    ArrayList<Card> returnStack = new ArrayList<Card>();
    int upperLim = source.getCards().size();
    for (int i = index; i < upperLim; i++) {
      returnStack.add(source.getCards().remove(index));
    }
    return returnStack;
  }

  /**
   * does the card at the supplied index exist in the supplied pile?.
   *
   * @param source    the pile from which the card is being taken
   * @param cardIndex the index of the card being taken
   * @return whether this card is value
   */
  protected boolean isValidCard(Pile source, int cardIndex) {
    // card value must be greater than 0 and less than the size of the pile.
    return (cardIndex >= 0 && cardIndex <= source.getCards().size() - 1);
  }

  /**
   * determines whether the stack can be moved, as there are enough intermediate piles and the cards
   * themselves alternate in color and increment in value.
   *
   * @param stack the stack that is being moved
   * @param max   the number of cards in a stack that can be moved
   * @return
   */
  private boolean isValidStack(ArrayList<Card> stack, int max) {
    // initialize return to true
    Boolean returnBool = true;
    // stack must be less than or equal to amount of empty slots available
    if (stack.size() > max) {
      returnBool = false;
    } else {
      // iterate through stack to determine if stack is valid
      for (int i = 0; i < stack.size() - 1; i++) {
        Card curCard = stack.get(i);
        Card nextCard = stack.get(i + 1);
        String curColor = curCard.getSuit().getColor();
        String nextColor = nextCard.getSuit().getColor();
        int curInt = curCard.getRank().getValue();
        int nextInt = nextCard.getRank().getValue();
        // is the next card one larger in value and a different color?
        returnBool = returnBool && (!curColor.equals(nextColor)) && (nextInt == curInt - 1);
      }
    }
    return returnBool;
  }

  /**
   * Helper method moving multiple cards to simplify the move method.
   *
   * @param moveStack the stack of cards to be moved
   * @param dest      the destination pile.
   */
  private void moveMultiCards(ArrayList<Card> moveStack, Pile source, Pile dest) {
    for (int i = 0; i <= moveStack.size(); i++) {
      if (!dest.getType().equals(PileType.CASCADE) && moveStack.size() > 1) {
        throw new IllegalArgumentException("cannot move multiple cards to non-cascade piles");
      } else if (moveStack.size() == 1 && !dest.isValidMove(source, moveStack.get(0))) {
        throw new IllegalArgumentException("move is invalid!");
      } else {
        dest.addCard(moveStack.remove(0));
      }
    }
  }
}
