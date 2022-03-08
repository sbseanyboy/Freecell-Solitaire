package cs3500.freecell.view;

import cs3500.freecell.model.FreecellModelState;
import java.io.IOException;

/**
 * represents a text view of a game of Freecell solitaire.
 */
public class FreecellTextView implements FreecellView {

  FreecellModelState<?> model;
  Appendable ap;

  /**
   * constructor for a FreecellTextView.
   *
   * @param model the supplied model to make a textview of
   */
  public FreecellTextView(FreecellModelState<?> model) {
    this.model = model;
    this.ap = System.out;
  }

  /**
   * constructor for a FreecellTextView with an Appendable parameter.
   *
   * @param model the supplied model to make a textview of
   * @param ap    the appendable that this view should use as its destination
   */
  public FreecellTextView(FreecellModelState<?> model, Appendable ap) {
    this.model = model;
    this.ap = ap;
  }

  /**
   * converts the supplied model into a text-view representation.
   *
   * @return this game of Freecell in text view
   */
  public String toString() {
    StringBuilder base = new StringBuilder();
    // EDIT: Added conditional so that invalid decks
    // return an empty string instead of an exception.
    if (model.getNumOpenPiles() == -1 || model.getNumCascadePiles() == -1) {
      return "";
    }
    for (int i = 0; i < 4; i++) {
      base.append("F" + (i + 1) + ":");
      for (int j = 0; j < model.getNumCardsInFoundationPile(i); j++) {
        base.append(" " + model.getFoundationCardAt(i, j).toString());
        if (j < model.getNumCardsInFoundationPile(i) - 1) {
          base.append(",");
        }
      }
      base.append("\n");
    }
    for (int i = 0; i < model.getNumOpenPiles(); i++) {
      base.append("O" + (i + 1) + ":");
      if (model.getNumCardsInOpenPile(i) > 0) {
        base.append(" " + model.getOpenCardAt(i).toString());
      }
      base.append("\n");
    }
    for (int i = 0; i < model.getNumCascadePiles(); i++) {
      base.append("C" + (i + 1) + ":");
      for (int j = 0; j < model.getNumCardsInCascadePile(i); j++) {
        base.append(" " + model.getCascadeCardAt(i, j));
        if (j < model.getNumCardsInCascadePile(i) - 1) {
          base.append(",");
        }
      }
      if (i < model.getNumCascadePiles() - 1) {
        base.append("\n");
      }
    }
    return base.toString();
  }

  /**
   * Renders the board to the TextView.
   *
   * @throws IOException if the transmission fails
   */
  @Override
  public void renderBoard() throws IOException {
    if (ap == null) {
      System.out.println(this.toString());
    }
    else {
      try {
        ap.append(new FreecellTextView(model, ap).toString() + "\n");
      } catch (Exception e) {
        throw new IOException("Could not write board to appendable");
      }
    }
  }

  /**
   * Renders a specific message to the TextView.
   *
   * @param message the message to be transmitted
   * @throws IOException if the transmission fails
   */
  @Override
  public void renderMessage(String message) throws IOException {
    if (message == null) {
      return;
    } else if (ap == null) {
      System.out.println(message);
    } else {
      try {
        ap.append(message);
      } catch (Exception e) {
        throw new IOException("could not write message to appendable");
      }
    }
  }
}
