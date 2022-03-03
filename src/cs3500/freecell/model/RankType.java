package cs3500.freecell.model;

/**
 * Enum to represent one of the ranks of cards in a standard deck.
 */
public enum RankType {
  ACE(0, "A"),
  TWO(1, "2"),
  THREE(2, "3"),
  FOUR(3, "4"),
  FIVE(4, "5"),
  SIX(5, "6"),
  SEVEN(6, "7"),
  EIGHT(7, "8"),
  NINE(8, "9"),
  TEN(9, "10"),
  JACK(10, "J"),
  QUEEN(11, "Q"),
  KING(12, "K");

  //since assignment 2: changed from final to private final.
  private final int index;
  private final String str;

  /**
   * Constructor for a RankType enum for a card.
   * @param index - index of this card relative to other cards' ranks.
   * @param str - String to represent how this card should be displayed.
   */
  RankType(int index, String str) {
    this.index = index;
    this.str = str;
  }

  /**
   * Returns the rank's index relative to other cards.
   * @return int of index
   */
  public int returnIndex() {
    return this.index;
  }

  @Override
  public String toString() {
    return this.str;
  }

}
