package cs3500.freecell.model.multimove;


import cs3500.freecell.model.Card;
import cs3500.freecell.model.PileType;
import cs3500.freecell.model.SimpleFreecellModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to represent a Multi-move Freecell Model.
 * Functions the same as a Simple Freecell Model,
 * but allows for the user to move more than one cards at a time
 * from any cascade pile, given that the
 * cards trying to be moved are a valid build and there are enough open piles to do this.
 * This multi-move addition mainly serves as convenience for the player.
 */
public class MultiMoveFreecellModel extends SimpleFreecellModel {

  /**
   * Constructor for a SimpleFreecellModel for convenience: takes in no parameters.
   */
  public MultiMoveFreecellModel() {
    super();
  }

  /**
   * Move a card from the given source pile to the given destination pile, if
   * the move is valid.
   *
   * @param source         the type of the source pile see @link{PileType}
   * @param pileNumber     the pile number of the given type, starting at 0
   * @param cardIndex      the index of the card to be moved from the source
   *                       pile, starting at 0
   * @param destination    the type of the destination pile (see
   * @param destPileNumber the pile number of the given type, starting at 0
   * @throws IllegalArgumentException if the move is not possible {@link
   *                                  PileType})
   * @throws IllegalStateException    if a move is attempted before the game has
   *                                  starts
   */
  public void move(PileType source, int pileNumber, int cardIndex,
                   PileType destination, int destPileNumber) {
    if (!started) {
      throw new IllegalStateException("The game has not begun!");
    }
    if (cardIndex < 0) {
      throw new IllegalArgumentException("Invalid card index.");
    }
    if (destPileNumber < 0) {
      throw new IllegalArgumentException("Invalid destination pile index.");
    }
    if (pileNumber < 0) {
      throw new IllegalArgumentException("Invalid source pile number.");
    }
    if (source == PileType.CASCADE && destination == PileType.CASCADE) {
      List<Card> stack = cascadePile.get(pileNumber)
              .subList(cardIndex, cascadePile.get(pileNumber).size());
      multiMoveCascadeToCascade(pileNumber, cardIndex, stack, destPileNumber);
    }
    else {
      super.move(source, pileNumber, cardIndex, destination, destPileNumber);
    }
  }


  /**
   * Method to move multiple cards from one cascade pile to another.
   * Checks to see if the user is able to move this many cards depending
   * on their number of available open piles.
   * Checks to see if the cards they are trying to move is a valid stack.
   * If the pile they're moving to is not empty, checks to see if the first card
   * in the stack they're moving can be placed on the top card of the dest. pile
   * @param pileNumber - cascade pile number to be moved from.
   * @param cardIndex - index of first card the user wants to move.
   * @param stack - stack of cards that the user is trying to move.
   * @param destPileNumber - cascade pile number to move to.
   */
  private void multiMoveCascadeToCascade(int pileNumber, int cardIndex,
                                         List<Card> stack, int destPileNumber) {
    if (movingTooManyCards(pileNumber, cardIndex)) {
      throw new IllegalArgumentException("Too many cards being moved!");
    }
    if (invalidStack(stack)) {
      throw new IllegalArgumentException("This is not a valid stack of cards!");
    }
    if (getNumCardsInCascadePile(destPileNumber) == 0) {
      stackIntoCascade(pileNumber, stack, destPileNumber);
      return;
    }
    else {
      //check if bottom card in the stack being moved can be placed onto the top card in dest pile
      Card c1 = cascadePile.get(pileNumber).get(cardIndex);
      Card c2 = getCascadeCardAt(destPileNumber, cascadePile.get(destPileNumber).size() - 1);
      if ((c1.getRank().returnIndex() != c2.getRank().returnIndex() - 1)
              || (c1.getSuit().returnColor() == c2.getSuit().returnColor())) {
        throw new IllegalArgumentException("These cards cannot be moved here!");
      }
      else {
        stackIntoCascade(pileNumber, stack, destPileNumber);
      }
    }
  }

  /**
   * Method to add the given cards from the "stack" argument to the cascade
   * pile at destPileNumber index, and remove from pileNumber index.
   * @param pileNumber - pile number that these cards are being taken from.
   * @param stack - stack of cards that are being moved.
   * @param destPileNumber - pile number that these cards are being moved to.
   */
  private void stackIntoCascade(int pileNumber, List<Card> stack, int destPileNumber) {

    //create copy of list to avoid mutation errors
    ArrayList<Card> cardList = new ArrayList<Card>();
    cardList.addAll(stack);

    for (Card c : cardList) {
      cascadePile.get(destPileNumber).add(c);
      cascadePile.get(pileNumber).remove(c);
    }

  }

  /**
   * Returns whether this list of cards is a "valid" stack.
   * For a stack to be valid, the cards' ranks must be in decreasing order,
   * and the colors must be alternating.
   * @param stack - list of cards that is attempting to be moved.
   * @return - whether this list of cards is a valid stack.
   */
  private boolean invalidStack(List<Card> stack) {
    for (int i = 0; i < stack.size() - 1; i++) {
      Card c1 = stack.get(i);
      Card c2 = stack.get(i + 1);
      if (!((c1.getRank().returnIndex() == c2.getRank().returnIndex() + 1)
              && (c1.getSuit().returnColor() != c2.getSuit().returnColor()))) {
        return true;
      }
    }
    return false;
  }

  /**
   * Returns whether the player is attempting to move too many cards. The player can only
   * move a number of cards up to (N+1)*2^K, with N being the number of empty open piles
   * and K being the number of empty cascade piles.
   * @param pileNumber - pile number the player is trying to move from
   * @param cardIndex - card index that the player is trying to move all cards below
   * @return - whether the player is moving more than (N+1)*2^K cards, or the card index
   *          is greater than the length of the list.
   */
  private boolean movingTooManyCards(int pileNumber, int cardIndex) {
    //calculates the number of cards trying to be moved
    int numCardsBeingMoved = getNumCardsInCascadePile(pileNumber) - cardIndex;
    return ((numCardsBeingMoved > maxNumMoves())
            || (cardIndex >= (getNumCardsInCascadePile(pileNumber))));
  }

  /**
   * Method to determine the maximum amount of cards that can be moved in this turn.
   * The player is allowed to move (N+1)*2^K cards, with N = number of empty open piles
   * and K = number of empty cascade piles.
   * @return - the max number of cards the player is able to move in this turn.
   */
  private int maxNumMoves() {
    int k = getNumEmptyPiles(this.cascadePile);
    int n = getNumEmptyPiles(this.openPile);
    return (int) Math.pow(2, k) * (n + 1);
  }

  /**
   * Method to return the number of empty piles in the given list of piles.
   * @param pile - list of lists of cards.
   * @return - the number of lists in this given list that are empty.
   */
  private int getNumEmptyPiles(ArrayList<ArrayList<Card>> pile) {
    int count = 0;
    for (int i = 0; i < pile.size(); i++) {
      if (pile.get(i).size() == 0) {
        count++;
      }
    }
    return count;
  }
}