package cs3500.freecell.model;

/**
 * Interface to represent an ICard, types of playing cards in the game of Freecell.
 */
public interface ICard {

  /**
   * method that returns the rank of this ICard.
   * @return - RankType of this card.
   */
  RankType getRank();

  /**
   * method that returns the suit of this ICard.
   * @return - SuitType of this card.
   */
  SuitType getSuit();

  /**
   * method that returns the color of this ICard.
   * @return - color of this card as an int.
   */
  int getColor();

}
