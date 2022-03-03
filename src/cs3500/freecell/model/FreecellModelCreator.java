package cs3500.freecell.model;

import cs3500.freecell.model.multimove.MultiMoveFreecellModel;

/**
 * Class to represent a creator of a Freecell model. Contains an Enum to dictate the
 * type of freecell model to be created, and contains a factory method to
 * return either a simple freecell model (SINGLEMOVE)
 * or a multimove freecell model (MULTIMOVE).
 */
public class FreecellModelCreator {

  /**
   * Enum to define a GameType for a game of freecell.
   * Could be either SINGLEMOVE (SimpleFreecellModel game) or
   * MULTIMOVE (MultiMoveFreecellModel game).
   */
  public enum GameType {
    SINGLEMOVE, MULTIMOVE
  }

  /**
   * Method to return a Freecell model of either singlemove or multimove functionality.
   * If given argument is null, will throw an illegal argument exception.
   * @param type - enum GameType representing the type of model to be constructed.
   * @return - Freecell model to be used for a game of Freecell.
   */
  public static FreecellModel create(GameType type) {
    if (type == null) {
      throw new IllegalArgumentException("Please enter a valid Game Type.");
    }
    if (type == GameType.MULTIMOVE) {
      return new MultiMoveFreecellModel();
    }
    else {
      return new SimpleFreecellModel();
    }
  }
}
