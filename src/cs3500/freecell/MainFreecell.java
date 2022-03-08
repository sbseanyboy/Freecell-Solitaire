package cs3500.freecell;

import cs3500.freecell.controller.FreecellController;
import cs3500.freecell.controller.SimpleFreecellController;
import cs3500.freecell.model.hw02.Card;
import cs3500.freecell.model.hw02.SimpleFreecellModel;
import java.io.InputStreamReader;

/**
 * Main class for Freecell Solitaire.
 */
public class MainFreecell {

  /**
   * main method for Freecell Solitaire.
   *
   * @param args default parameter for main method
   */
  public static void main(String[] args) {
    SimpleFreecellModel model = new SimpleFreecellModel();
    Readable rd = new InputStreamReader(System.in);
    Appendable ap = System.out;
    FreecellController<Card> controller = new SimpleFreecellController(model, rd, ap);
    controller.playGame(model.getDeck(), 4, 4, true);
  }
}
