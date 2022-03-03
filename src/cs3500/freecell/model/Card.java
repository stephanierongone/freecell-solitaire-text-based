package cs3500.freecell.model;

//changed since assignment 2: more descriptive JavaDoc description
/**
 * Class to represent a standard playing Card in the game of Freecell.
 */
public class Card implements ICard {
  //attributes of a Card
  //changed to private final access modifiers since assignment 1
  private final RankType rank;
  private final SuitType suit;

  /**
   * Constructor for a Card.
   * @param rank - rank of this card, as a String.
   * @param suit - suit of this card, as a String.
   */
  public Card(RankType rank, SuitType suit) {
    if (rank == null || suit == null) {
      throw new IllegalArgumentException("fields cannot be null");
    }
    this.rank = rank;
    this.suit = suit;
  }

  @Override
  public String toString() {
    return rank.toString() + suit.toString();
  }

  /**
   * Returns the rank of this card.
   * @return - rank of card.
   */
  public RankType getRank() {
    return this.rank;
  }

  /**
   * Returns the suitType of this card.
   * @return - suit type of card.
   */
  public SuitType getSuit() {
    return this.suit;
  }

  /**
   * Returns the color of this card.
   * @return - color as an int
   */
  public int getColor() {
    return this.suit.returnColor();
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Card)) {
      return false;
    }
    return ((this.rank.returnIndex() == ((Card) o).rank.returnIndex())
            && (this.suit.toString().equals(((Card) o).suit.toString())));
  }

  @Override
  public int hashCode() {
    return this.rank.returnIndex();
  }


}
