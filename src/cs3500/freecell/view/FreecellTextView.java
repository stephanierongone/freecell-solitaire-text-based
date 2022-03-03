package cs3500.freecell.view;

import cs3500.freecell.model.FreecellModelState;

import java.io.IOException;

/**
 * Class to represent a Text View of a Freecell model.
 * may contain an appendable to be written to.
 * Displays the state of the game or a particular message to the user
 * for easy functionality of the game of freecell.
 */
public class FreecellTextView implements FreecellView {

  private FreecellModelState<?> model;
  private Appendable app;

  /**
   * Constructor for a Freecell Text View.
   *
   * @param model - model to be used for this text view.
   */
  public FreecellTextView(FreecellModelState<?> model) {
    if (model == null) {
      throw new IllegalArgumentException("Model can't be null");
    }
    this.model = model;
  }

  /**
   * Constructs a FreecellTextView with an Appendable that this
   * view will use as its destination.
   *
   * @param a - Appendable to write to/
   */
  public FreecellTextView(Appendable a, FreecellModelState<?> model) {
    if (model == null) {
      throw new IllegalArgumentException("Model can't be null");
    }
    this.app = a;
    this.model = model;
  }

  @Override
  public String toString() {
    try {
      model.getNumCardsInOpenPile(0);
    } catch (IllegalStateException e) {
      return "";
    }
    String returnString = "";
    //add foundation cards
    for (int i = 0; i < 4; i++) {
      returnString += "F" + (i + 1) + ":";
      for (int j = 0; j < model.getNumCardsInFoundationPile(i); j++) {
        returnString += " " + model.getFoundationCardAt(i, j).toString();
        if (j < model.getNumCardsInFoundationPile(i) - 1) {
          returnString += ",";
        }
      }
      returnString += "\n";
    }

    //add open cards
    for (int i = 0; i < model.getNumOpenPiles(); i++) {
      returnString += "O" + (i + 1) + ":";
      //only one card can be in each open pile so no need for another for-loop
      if (model.getNumCardsInOpenPile(i) != 0) {
        returnString += " " + model.getOpenCardAt(i).toString();
      }
      returnString += "\n";
    }

    //add cascade cards
    for (int i = 0; i < model.getNumCascadePiles(); i++) {
      returnString += "C" + (i + 1) + ": ";
      for (int j = 0; j < model.getNumCardsInCascadePile(i); j++) {
        returnString += model.getCascadeCardAt(i, j).toString();
        if (j < model.getNumCardsInCascadePile(i) - 1) {
          returnString += ", ";
        }
      }
      if (i < model.getNumCascadePiles() - 1) {
        returnString += "\n";
      }
    }
    return returnString;
  }

  /**
   * Render the board to the view's appendable. The board should be rendered exactly
   * in the format produced by the toString method above
   *
   * @throws IOException if transmission of the board to the provided data destination fails
   */
  public void renderBoard() throws IOException {
    if (app == null) {
      System.out.println(this + "\n");
      throw new IllegalArgumentException("this view does not have a valid appendable to write to.");
    } else {
      app.append(this.toString());
      app.append("\n");
    }
  }

  /**
   * Render a specific message to the view's appendable.
   *
   * @param message the message to be transmitted
   * @throws IOException if transmission of the board to the provided data destination fails
   */
  public void renderMessage(String message) throws IOException {
    if (app == null) {
      System.out.println(message);
      throw new IllegalArgumentException("this view does not have a " +
              "valid appendable to write to.");
    } else {
      app.append(message + "\n");
    }
  }

}
