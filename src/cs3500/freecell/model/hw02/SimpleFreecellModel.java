package cs3500.freecell.model.hw02;

import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.PileType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Collections;

/**
 * represents a the Model of a game of Freecell Solitaire.
 */
public class SimpleFreecellModel implements FreecellModel<Card> {

  // changed fields to protected so the extending class can access
  protected ArrayList<Pile> foundationPiles;
  protected ArrayList<Pile> cascadePiles;
  protected ArrayList<Pile> openPiles;
  protected int numPiles;
  protected boolean isStarted;

  /**
   * constructs a FreecellModel with default values.
   */
  public SimpleFreecellModel() {
    this.foundationPiles = new ArrayList<>();
    this.cascadePiles = new ArrayList<>();
    this.openPiles = new ArrayList<>();
    this.numPiles = 0;
    this.isStarted = false;
  }

  /**
   * creates and returns a new deck of cards, unshuffled.
   *
   * @return a new deck of cards
   */
  @Override
  public ArrayList<Card> getDeck() {
    Suit[] suits = Suit.values();
    Rank[] ranks = Rank.values();

    ArrayList<Card> deck = new ArrayList<>();
    for (Suit suit : suits) {
      for (Rank rank : ranks) {
        deck.add(new Card(rank, suit));
      }
    }
    return deck;
  }

  /**
   * startGame method that shuffles as specified, initializes all piles, distributes cards to
   * cascade pile (round-robin style), and counts the number of piles.
   *
   * @param deck            the deck to be dealt
   * @param numCascadePiles number of cascade piles
   * @param numOpenPiles    number of open piles
   * @param shuffle         if true, shuffle the deck else deal the deck as-is
   * @throws IllegalArgumentException if the specified parameters are illegal
   */
  @Override
  public void startGame(List<Card> deck, int numCascadePiles, int numOpenPiles, boolean shuffle)
      throws IllegalArgumentException {

    // restarts new game if game is called again
    if (this.isStarted) {
      this.foundationPiles = new ArrayList<>();
      this.cascadePiles = new ArrayList<>();
      this.openPiles = new ArrayList<>();
      this.numPiles = 0;
      this.isStarted = false;
    }
    // is this deck valid?
    if (!isValidDeck(deck)) {
      throw new IllegalArgumentException("Invalid deck");
      // is everything valid?
    } else if (isValidDeck(deck) && numCascadePiles >= 4 && numOpenPiles > 0) {
      if (shuffle) {
        // shuffles the deck
        Collections.shuffle(deck);
      }
      // initializes 4 foundation piles
      for (int i = 0; i < 4; i++) {
        this.foundationPiles.add(new Pile(PileType.FOUNDATION, new ArrayList<>()));
      }

      // initializes the specified amount of cascade piles
      for (int i = 0; i < numCascadePiles; i++) {
        this.cascadePiles.add(new Pile(PileType.CASCADE, new ArrayList<>()));
      }

      // distributes cards in round-robin style
      for (int i = 0; i < 52; i++) {
        this.cascadePiles.get(i % this.cascadePiles.size()).addCard(deck.get(i));
      }

      // initializes the specified amount of open piles
      for (int i = 0; i < numOpenPiles; i++) {
        this.openPiles.add(new Pile(PileType.OPEN, new ArrayList<>()));
      }

      // counts the number of piles
      this.numPiles =
          this.foundationPiles.size() + this.cascadePiles.size() + this.openPiles.size();
      this.isStarted = true;
    } else {
      throw new IllegalArgumentException("Invalid Parameters");
    }
  }

  /**
   * determines whether the supplied deck of cards is valid, meaning it contains all cards, is 52
   * cards in size, and has no repeating cards.
   *
   * @param deck the deck of cards being cheked for validity
   * @return whether the specified deck is valid
   */
  protected boolean isValidDeck(List<Card> deck) {
    // is the deck null?
    if (deck == null) {
      throw new IllegalArgumentException("Deck is null");
      // does the deck have exactly 52 cards?
    } else if (deck.size() != 52) {
      throw new IllegalArgumentException("Deck size is invalid");
    } else {
      // does the deck contain every card type?
      ArrayList<String> allCards = new ArrayList<>(Arrays.asList(
          "A♥", "2♥", "3♥", "4♥", "5♥", "6♥", "7♥", "8♥", "9♥", "10♥", "J♥", "Q♥", "K♥",
          "A♠", "2♠", "3♠", "4♠", "5♠", "6♠", "7♠", "8♠", "9♠", "10♠", "J♠", "Q♠", "K♠",
          "A♣", "2♣", "3♣", "4♣", "5♣", "6♣", "7♣", "8♣", "9♣", "10♣", "J♣", "Q♣", "K♣",
          "A♦", "2♦", "3♦", "4♦", "5♦", "6♦", "7♦", "8♦", "9♦", "10♦", "J♦", "Q♦", "K♦"
      ));
      ArrayList<String> deckStrings = allToString(deck);
      return deckStrings.containsAll(allCards);
    }
  }

  /**
   * converts every card in a supplied list into a string.
   *
   * @param cards the list of cards being converted to strings
   * @return all cards as strings
   */
  protected ArrayList<String> allToString(List<Card> cards) {
    ArrayList<String> result = new ArrayList<>();
    for (Card c : cards) {
      result.add(c.toString());
    }
    return result;
  }

  /**
   * moves the card so long as the game has started, the move is valid (see rules), the pile exists,
   * and the card is the top card in the pile.
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
      Card moveCard = sourcePile.getCards().get(cardIndex);
      // is the move legal?
      if (!destinationPile.isValidMove(sourcePile, moveCard)) {
        throw new IllegalArgumentException("Move is illegal");
      } else {
        destinationPile.getCards().add(sourcePile.getCards().remove(cardIndex));
      }
    }
  }

  /**
   * does the pile at the supplied index exist in the supplied list of Piles?.
   *
   * @param source     the pile array from which the pile is being selected
   * @param pileNumber the index of the pile being selected
   * @return whether this pile is valid
   */
  protected boolean isValidPile(ArrayList<Pile> source, int pileNumber) {
    return (pileNumber >= 0 && pileNumber <= source.size() - 1);
  }

  /**
   * does the card at the supplied index exist in the supplied pile?.
   *
   * @param source    the pile from which the card is being taken
   * @param cardIndex the index of the card being taken
   * @return whether this card is value
   */
  protected boolean isValidCard(Pile source, int cardIndex) {
    // changed the cardIndex <= source.getCards().size to == as only the top
    // card should be able to be accessed to move in a game of simple freecell.
    return (cardIndex >= 0 && cardIndex == source.getCards().size() - 1);
  }

  /**
   * returns a list of all piles with the specified {@code PileType}.
   *
   * @param type the type of pile of which all piles will be returned
   * @return the list of piles in this game of the specified {@code PileType}
   */
  protected ArrayList<Pile> getPileArray(PileType type) {
    ArrayList<Pile> result = null;
    switch (type) {
      // returns all foundation piles
      case FOUNDATION:
        result = this.foundationPiles;
        break;
      // returns all cascade piles
      case CASCADE:
        result = this.cascadePiles;
        break;
      // returns all open piles
      case OPEN:
        result = this.openPiles;
        break;

      default:
        throw new IllegalArgumentException("invalid pile Type");
    }
    return result;
  }

  /**
   * determines whether this game is over based on if the foundation piles are complete.
   *
   * @return whether this game is over or not
   */
  @Override
  public boolean isGameOver() {
    boolean base = true;
    for (int i = 0; i <= 3; i++) {
      base = base && getNumCardsInFoundationPile(i) == 13;
    }
    return base;
  }

  /**
   * counts the number of cards in a foundation pile.
   *
   * @param index the index of the foundation pile, starting at 0
   * @return the number of cards in the foundation pile at the supplied index
   * @throws IllegalArgumentException if the index is invalid
   * @throws IllegalStateException    if the game hasn't started
   */
  @Override
  public int getNumCardsInFoundationPile(int index)
      throws IllegalArgumentException, IllegalStateException {
    return getNumCardsInXPile(PileType.FOUNDATION, index, 3);
  }

  /**
   * counts the number of cascade piles.
   *
   * @return the number of cascade piles
   */
  @Override
  public int getNumCascadePiles() {
    if (!this.isStarted) {
      return -1;
    } else {
      return this.cascadePiles.size();
    }
  }

  /**
   * counts the number of cascade piles.
   *
   * @return the number of cascade piles
   */
  protected ArrayList<Pile> getCascadePiles() {
    return this.cascadePiles;
  }

  /**
   * counts the number of cards in a cascade pile.
   *
   * @param index the index of the cascade pile, starting at 0
   * @return the number of cards in the casade pile at the supplied index
   * @throws IllegalArgumentException if the index is invalid
   * @throws IllegalStateException    if the game hasn't started
   */
  @Override
  public int getNumCardsInCascadePile(int index)
      throws IllegalArgumentException, IllegalStateException {
    return getNumCardsInXPile(PileType.CASCADE, index, this.getNumCascadePiles() - 1);
  }

  /**
   * counts the number of cards in an open pile.
   *
   * @param index the index of the open pile, starting at 0
   * @return the number of cards in the open pile at the supplied index
   * @throws IllegalArgumentException if the index is invalid
   * @throws IllegalStateException    if the game hasn't started
   */
  @Override
  public int getNumCardsInOpenPile(int index)
      throws IllegalArgumentException, IllegalStateException {
    return getNumCardsInXPile(PileType.OPEN, index, this.getNumOpenPiles() - 1);
  }

  /**
   * counts the number of cards in the specified pile.
   *
   * @param index the index of the open pile, starting at 0
   * @return the number of cards in the open pile at the supplied index
   * @throws IllegalArgumentException if the index is invalid
   * @throws IllegalStateException    if the game hasn't started
   */
  protected int getNumCardsInXPile(PileType type, int index, int upperBound)
      throws IllegalArgumentException, IllegalStateException {
    // has this game started?
    if (!this.isStarted) {
      throw new IllegalStateException("Game has not started yet!");
    }
    // is the index valid?
    if (index > upperBound || index < 0) {
      throw new IllegalArgumentException("index is invalid");
    } else {
      return this.getPileArray(type).get(index).getCards().size();
    }
  }

  /**
   * counts the number of open piles.
   *
   * @return the number of open piles
   */
  @Override
  public int getNumOpenPiles() {
    if (!this.isStarted) {
      return -1;
    } else {
      return this.openPiles.size();
    }
  }

  /**
   * counts the number of foundation piles.
   *
   * @return the number of foundation piles
   */
  protected int getNumFoundationPiles() {
    if (!this.isStarted) {
      return -1;
    } else {
      return this.foundationPiles.size();
    }
  }

  /**
   * counts total the number of piles.
   *
   * @return the total number of piles
   */
  protected int getNumPiles() {
    if (!this.isStarted) {
      return -1;
    } else {
      return this.getNumFoundationPiles() + this.getNumOpenPiles() + this.getNumCascadePiles();
    }
  }

  /**
   * counts total the number of piles.
   *
   * @return the total number of piles
   */
  protected boolean getStartedStatus() {
    return this.isStarted;
  }

  /**
   * gets the card at the specified index from the specified foundation pile.
   *
   * @param pileIndex the index of the foundation pile, starting at 0
   * @param cardIndex the index of the card in the above foundation pile, starting at 0
   * @return the card at the specified location in the foundation piles
   * @throws IllegalArgumentException if the indexes are invalid
   * @throws IllegalStateException    if the game hasn't started
   */
  @Override
  public Card getFoundationCardAt(int pileIndex, int cardIndex)
      throws IllegalArgumentException, IllegalStateException {
    // is the game started?
    if (!this.isStarted) {
      throw new IllegalStateException("Game has not started yet!");
    }
    // is the pile index valid?
    if (pileIndex > this.foundationPiles.size() - 1 || pileIndex < 0) {
      throw new IllegalArgumentException("pile index is invalid");
    } else {
      // is the card index valid?
      if (cardIndex > getNumCardsInFoundationPile(pileIndex) - 1 || cardIndex < 0) {
        throw new IllegalArgumentException("Card index is invalid");
      } else {
        return this.foundationPiles.get(pileIndex).getCards().get(cardIndex);
      }
    }
  }

  /**
   * gets the card at the specified index from the specified cascade pile.
   *
   * @param pileIndex the index of the cascade pile, starting at 0
   * @param cardIndex the index of the card in the above cascade pile, starting at 0
   * @return the card at the specified location in the cascade piles
   * @throws IllegalArgumentException if the indexes are invalid
   * @throws IllegalStateException    if the game hasn't started
   */
  @Override
  public Card getCascadeCardAt(int pileIndex, int cardIndex)
      throws IllegalArgumentException, IllegalStateException {
    // has the game started?
    if (!this.isStarted) {
      throw new IllegalStateException("Game has not started yet!");
    }
    // is the pile index valid?
    if (pileIndex > this.cascadePiles.size() - 1 || pileIndex < 0) {
      throw new IllegalArgumentException("pile index is invalid");
    } else {
      // is the card index valid?
      if (cardIndex > getNumCardsInCascadePile(pileIndex) - 1 || cardIndex < 0) {
        throw new IllegalArgumentException("Card index is invalid");
      } else {
        return this.cascadePiles.get(pileIndex).getCards().get(cardIndex);
      }
    }
  }

  /**
   * gets the card at the specified index from the specified open pile.
   *
   * @param pileIndex the index of the open pile, starting at 0
   * @return the card at the specified location in the open piles
   * @throws IllegalArgumentException if the index is invalid
   * @throws IllegalStateException    if the game hasn't started
   */
  @Override
  public Card getOpenCardAt(int pileIndex)
      throws IllegalArgumentException, IllegalStateException {
    // has the game started?
    if (!this.isStarted) {
      throw new IllegalStateException("Game has not started yet!");
    }
    // is the pile index valid?
    if (pileIndex > this.openPiles.size() - 1 || pileIndex < 0) {
      throw new IllegalArgumentException("pile index is invalid");
    } else if (this.openPiles.get(pileIndex).getCards().isEmpty()) {
      return null;
    } else {
      return this.openPiles.get(pileIndex).getCards().get(0);
    }
  }
}
