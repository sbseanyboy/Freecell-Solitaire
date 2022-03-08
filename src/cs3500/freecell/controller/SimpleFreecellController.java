package cs3500.freecell.controller;

import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.PileType;
import cs3500.freecell.model.hw02.Card;
import cs3500.freecell.view.FreecellTextView;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

/**
 * SimpleFreecellController class is the controller for a game of Simple Freecell.
 */
public class SimpleFreecellController implements FreecellController<Card> {

  private FreecellModel<Card> model;
  private Readable rd;
  private Boolean isGamePlaying;
  private FreecellTextView view;

  /**
   * SimpleFreecellController constructor.
   *
   * @param model the model that this controller is controlling
   * @param rd    the readable input from the view
   * @param ap    the appendable output for the view
   * @throws IllegalArgumentException if either rd or ap are null
   */
  public SimpleFreecellController(FreecellModel<Card> model, Readable rd, Appendable ap)
      throws IllegalArgumentException {
    try {
      Objects.requireNonNull(rd);
      Objects.requireNonNull(ap);
      Objects.requireNonNull(model);
    } catch (NullPointerException e) {
      throw new IllegalArgumentException("Cannot be Null\n");
    }
    this.model = model;
    this.rd = rd;
    this.isGamePlaying = false;
    this.view = new FreecellTextView(this.model, ap);
  }

  /**
   * playGame method for SimpleFreecellController implementation of FreecellController.
   *
   * @param deck        the deck to be used to play this game
   * @param numCascades the number of cascade piles
   * @param numOpens    the number of open piles
   * @param shuffle     shuffle the deck if true, false otherwise
   * @throws IllegalStateException    if the game cannot be played
   * @throws IllegalArgumentException if an argument is invalid
   */
  @Override
  public void playGame(List<Card> deck, int numCascades, int numOpens, boolean shuffle)
      throws IllegalStateException, IllegalArgumentException {
    try {
      Objects.requireNonNull(deck);
    } catch (NullPointerException npe) {
      throw new IllegalArgumentException("Deck is Null");
    }
    try {
      if (deck.isEmpty()) {
        throw new IllegalArgumentException("Deck is Empty");
      }
    } catch (NullPointerException np) {
      throw new IllegalArgumentException("Deck is null");
    }
    try {
      try {
        this.model.startGame(deck, numCascades, numOpens, shuffle);
      } catch (NullPointerException n) {
        throw new IllegalArgumentException("Deck is null");
      }
      this.isGamePlaying = true;
      try {
        this.view.renderBoard();
      } catch (IOException e) {
        throw new IllegalStateException("Could not write board to appendable.\n");
      }
    } catch (Exception e) {
      try {
        this.view.renderMessage("Could not start game.");
      } catch (IOException io) {
        throw new IllegalStateException("Could not write message to appendable\n");
      }
      return;
    }
    if (model.isGameOver()) {
      try {
        this.view.renderMessage("Game Over.\n");
        this.isGamePlaying = false;
      } catch (IOException e) {
        throw new IllegalStateException("Could not write message to appendable\n");
      }
      return;
    } else {
      controlGame();
    }
  }

  /**
   * Controls the game after the game is determined to be valid and playable.
   */
  private void controlGame() {
    Scanner scan = new Scanner(this.rd);
    String sourceName = null;
    Integer sourceIdx = null;
    Integer cardIdx = null;
    String destName = null;
    Integer destIdx = null;

    while ((this.isGamePlaying)) {
      // initializes the 1st input provided by the user
      while (scan.hasNext() && sourceName == null && sourceIdx == null) {
        String string1 = scan.next();
        if (quitGame(string1)) {
          return;
        } else if (validPile(string1)) {
          // EDIT: changed substring values to make method function properly
          sourceName = string1.substring(0, 1);
          sourceIdx = Integer.parseInt(string1.substring(1)) - 1;
        } else {
          try {
            view.renderMessage("Re Input your parameters!\n");
          } catch (IOException e) {
            throw new IllegalStateException("Could not write message to appendable\n");
          }
        }
      }

      // initializes the 2nd input provided by the user
      while (scan.hasNext() && cardIdx == null) {
        String string2 = scan.next();
        if (quitGame(string2)) {
          return;
        } else if (validCardIdx(string2)) {
          cardIdx = Integer.parseInt(string2) - 1;
        } else {
          try {
            view.renderMessage("Re Input your Card Index and Destination Pile parameters!\n");
          } catch (IOException e) {
            throw new IllegalStateException("Could not write message to appendable\n");
          }
        }
      }

      // initializes the 3rd input provided by the user
      while (scan.hasNext() && destName == null && destIdx == null) {
        String string3 = scan.next();
        if (quitGame(string3)) {
          return;
        } else if (validPile(string3)) {
          // EDIT: changed substring values to make method function properly
          destName = string3.substring(0, 1);
          destIdx = Integer.parseInt(string3.substring(1)) - 1;
        } else {
          try {
            view.renderMessage("Re Input your Destination Pile parameter!\n");
          } catch (IOException e) {
            throw new IllegalStateException("Could not write message to appendable\n");
          }
        }
      }

      // moves according to the inputs
      if (!(sourceName == null || sourceIdx == null || cardIdx == null || destName == null
          || destIdx == null)) {
        try {
          this.model
              .move(getPileType(sourceName), sourceIdx, cardIdx, getPileType(destName), destIdx);
          this.view.renderBoard();
        } catch (Exception e) {
          try {
            this.view.renderMessage("Invalid move, try again.\n");
            this.view.renderMessage(e.getMessage() + "\n");
          } catch (IOException io) {
            throw new IllegalStateException("Could not write message to appendable\n");
          }
        }
        sourceName = null;
        sourceIdx = null;
        cardIdx = null;
        destName = null;
        destIdx = null;
      } else if (this.model.isGameOver()) {
        try {
          this.view.renderBoard();
          this.view.renderMessage("Game over.\n");
        } catch (IOException e) {
          throw new IllegalStateException("Could not write board to appendable\n");
        }
        this.isGamePlaying = false;
        return;
        // EDIT: removed extra conditional that was here before preventing access to
        // IllegalStateException below
      } else {
        throw new IllegalStateException("Out of Input");
      }
    }
  }

  /**
   * gets the PileType based on the user input.
   *
   * @param s User inputted String
   * @return Corresponding piletype
   */
  protected PileType getPileType(String s) {
    PileType returnType = null;
    switch (s) {
      case "C":
        returnType = PileType.CASCADE;
        break;
      case "F":
        returnType = PileType.FOUNDATION;
        break;
      case "O":
        returnType = PileType.OPEN;
        break;
      default:
        throw new IllegalArgumentException("Invalid Pile Type\n");
    }
    return returnType;
  }

  /**
   * Determines if the user's inputted pile is valid.
   *
   * @param s String the user inputted
   * @return whether the pile is valid
   */
  protected boolean validPile(String s) {
    String validPileLetters = "CFO";
    String type = s.substring(0, 1);
    String idx = s.substring(1);
    try {
      return validPileLetters.contains(type) && Integer.parseInt(idx) > 0;
    } catch (NumberFormatException n) {
      return false;
    }
  }

  /**
   * Determines if the user's inputted card index is valid.
   *
   * @param s String the user inputted
   * @return whether the card index is valid
   */
  protected boolean validCardIdx(String s) {
    try {
      return Integer.parseInt(s) > 0;
    } catch (NumberFormatException n) {
      return false;
    }
  }

  /**
   * Determines whether the user quit the game.
   *
   * @param s String the user inputted
   * @return whether the user wants to quit the game
   */
  protected boolean quitGame(String s) {
    if (s.contains("q") || s.contains("Q")) {
      this.isGamePlaying = false;
      try {
        this.view.renderMessage("Game quit prematurely.\n");
      } catch (IOException e) {
        throw new IllegalArgumentException("Could not write message to appendable.\n");
      }
      return true;
    } else {
      return false;
    }
  }

  /**
   * returns whether the game is playing or not.
   *
   * @return the game state
   */
  protected boolean getGameState() {
    return this.isGamePlaying;
  }
}


