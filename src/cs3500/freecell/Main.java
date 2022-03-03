package cs3500.freecell;

import cs3500.freecell.controller.SimpleFreecellController;
import cs3500.freecell.model.Card;
import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.multimove.MultiMoveFreecellModel;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Main method to use for testing and debugging the program.
 * Writes to console and takes user input.
 */
public class Main {

  /**
   * Main method to run game of Freecell and take user input, write to console.
   * @param args - arguments taken in as user input from console.
   * @throws IOException - when user input or output fails.
   */
  public static void main(String[] args) throws IOException {
    FreecellModel model = new MultiMoveFreecellModel();
    List<Card> deck = model.getDeck();
    SimpleFreecellController ctrl = new SimpleFreecellController(model,
            new InputStreamReader(System.in), System.out);
    ctrl.playGame(deck, 4, 4, false);
  }
}
