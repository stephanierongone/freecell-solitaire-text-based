package cs3500.freecell.model;

/**
 * Type of suit in a standard deck of cards. Includes spade, club, heart, and diamond.
 */
public enum SuitType {
  SPADE("♠", 0), CLUB("♣", 0), HEART("♥", 1), DIAMOND("♦", 1);

  private final String str;
  private final int color;

  /**
   * Constructor for a suitType enum with a given String representation and color.
   * @param str - String to represent the Suit as a text display.
   * @param color - to represent the color of this card - 0 for black, 1 for red.
   */
  SuitType(String str, int color) {
    this.str = str;
    this.color = color;
  }

  @Override
  public String toString() {
    return this.str;
  }

  /**
   * Returns the color of this SuitType, with 0 representing black and 1 being red.
   * @return - color as an integer
   */
  public int returnColor() {
    return this.color;
  }
}
