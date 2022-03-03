package cs3500.freecell.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Represents a Simple Freecell Model: a model for the game of freecell.
 * contains a cascade, open, and foundation pile. cards are mainly held within
 * the cascade pile, with and end goal of moving all cards into foundation piles.
 */
public class SimpleFreecellModel implements FreecellModel<Card> {

  //attributes to a SimpleFreecellModel
  //changed since assignment 2: made protected, not private, so that
  //fields are accessible by subclass.
  protected ArrayList<ArrayList<Card>> cascadePile;
  protected ArrayList<ArrayList<Card>> openPile;
  protected ArrayList<ArrayList<Card>> foundationPile;
  protected Boolean started;

  /**
   * Constructor for a SimpleFreecellModel for convenience: takes in no parameters.
   */
  public SimpleFreecellModel() {
    this.started = false;
    this.cascadePile = new ArrayList<ArrayList<Card>>();
    this.openPile = new ArrayList<ArrayList<Card>>();
    this.foundationPile = new ArrayList<ArrayList<Card>>();
  }

  /**
   * method to return a List of 52 Cards to represent the Deck in this game
   * of freecell.
   *
   * @return List of Cards to represent this deck.
   */
  public ArrayList<Card> getDeck() {
    ArrayList<Card> cards = new ArrayList<>();
    ArrayList<RankType> ranks = new ArrayList<>(Arrays.asList(RankType.ACE, RankType.TWO,
            RankType.THREE, RankType.FOUR, RankType.FIVE, RankType.SIX, RankType.SEVEN,
            RankType.EIGHT, RankType.NINE, RankType.TEN, RankType.JACK, RankType.QUEEN,
            RankType.KING));
    ArrayList<SuitType> suits = new ArrayList<>(Arrays.asList(SuitType.CLUB, SuitType.DIAMOND,
            SuitType.SPADE, SuitType.HEART));
    for (int i = 12; i >= 0; i--) {
      for (int j = 0; j < 4; j++) {
        Card c = new Card(ranks.get(i), suits.get(j));
        cards.add(c);
      }
    }
    return cards;
  }

  /**
   * Deals a new game of freecell with the given deck, number of piles, and whether it
   * should be shuffled.
   *
   * @param numCascadePiles number of cascade piles
   * @param numOpenPiles    number of open piles
   * @param deck            the deck to be dealt
   * @param shuffle         if true, shuffle the deck else deal the deck as-is
   * @throws IllegalArgumentException if the deck is invalid
   * @throws IllegalArgumentException if there are not enough cascade or open piles.
   */
  public void startGame(List<Card> deck, int numCascadePiles, int numOpenPiles, boolean shuffle) {
    if (numCascadePiles < 4) {
      throw new IllegalArgumentException("Must have at least 4 Cascade Piles!");
    }
    if (numOpenPiles < 1) {
      throw new IllegalArgumentException("Must have at least one Open Pile!");
    }
    if (!deckIsValid(deck)) {
      throw new IllegalArgumentException("This deck is not valid!");
    } else {
      if (shuffle) {
        Collections.shuffle(deck);
      }
      ArrayList<ArrayList<Card>> cascadePile = new ArrayList<>();

      for (int j = 0; j < numCascadePiles; j++) {
        ArrayList<Card> cardPile = new ArrayList<>();
        cascadePile.add(cardPile);
      }
      this.cascadePile = cascadePile;
      //add cards into piles
      for (int i = 0; i < deck.size(); i++) {
        int pileNum = i % numCascadePiles;
        Card c = deck.get(i);
        this.cascadePile.get(pileNum).add(c);
      }

      this.openPile = new ArrayList<>();

      for (int i = 0; i < numOpenPiles; i++) {
        ArrayList<Card> cards = new ArrayList<>();
        openPile.add(cards);
      }


      this.foundationPile = new ArrayList<>();

      for (int i = 0; i < 4; i++) {
        ArrayList<Card> cards = new ArrayList<>();
        foundationPile.add(cards);
      }
      this.started = true;
    }

  }

  /**
   * Returns whether this deck is valid: 52 non-null, unique cards.
   *
   * @param deck - list of cards in this deck.
   * @return - boolean representing the validity of the given deck.
   */
  private boolean deckIsValid(List<Card> deck) {
    return (deck.size() == 52) && cardsAreUnique(deck) && noNullCards(deck);
  }

  /**
   * returns whether there are duplicate cards in this list of cards.
   *
   * @param deck - list of cards in this deck.
   * @return - boolean representing the uniqueness of this deck.
   */
  private boolean cardsAreUnique(List<Card> deck) {
    for (int i = 0; i < deck.size(); i++) {
      Card c1 = deck.get(i);
      for (int j = i + 1; j < deck.size(); j++) {
        Card c2 = deck.get(j);
        if (c1.equals(c2)) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * returns whether there are null cards in this list of cards.
   *
   * @param deck - list of cards in this deck.
   * @return - boolean representing whether there are null cards in this deck.
   */
  private boolean noNullCards(List<Card> deck) {
    for (Card card : deck) {
      if (card == null) {
        return false;
      }
    }
    return true;
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
    switch (source) {
      case OPEN:
        moveFromOpenPile(pileNumber, cardIndex, destination, destPileNumber);
        break;
      case CASCADE:
        moveFromCascadePile(pileNumber, cardIndex, destination, destPileNumber);
        break;
      case FOUNDATION:
        throw new IllegalArgumentException("Cards cannot be moved from Foundation piles!");
      default:
        break;
    }
  }

  /**
   * Moves a card from the desired cascade pile into the desired new pile.
   *
   * @param pileNumber     - pile number from cascade pile
   * @param cardIndex      - index of card to be moved
   * @param destination    - PileType to be moved to
   * @param destPileNumber - pile number to be moved to
   * @throws IllegalArgumentException - if the pile to take from does not exist or is empty,
   *                                  or if the card to move is not the last in its pile.
   */
  private void moveFromCascadePile(int pileNumber, int cardIndex,
                                   PileType destination, int destPileNumber) {
    if (pileNumber > getNumCascadePiles() - 1) {
      throw new IllegalArgumentException("This pile does not exist!");
    }
    if (cascadePile.get(pileNumber).isEmpty()) {
      throw new IllegalArgumentException("Cannot move from empty pile!");
    }
    if (cardIndex != cascadePile.get(pileNumber).size() - 1) {
      throw new IllegalArgumentException("Can only move last card from each cascade pile!");
    }
    Card c = cascadePile.get(pileNumber).get(cardIndex);
    switch (destination) { //for all of these methods, the card is able to be moved.
      case OPEN:
        moveToOpen(c, cascadePile.get(pileNumber), destPileNumber);
        break;
      case CASCADE:
        moveToCascade(c, cascadePile.get(pileNumber), destPileNumber);
        break;
      case FOUNDATION:
        moveToFoundation(c, cascadePile.get(pileNumber), destPileNumber);
        break;
      default:
        break;
    }
  }

  /**
   * moves a card from the desires open pile to the desired new pile.
   *
   * @param pileNumber     - open pile number to be moved from.
   * @param cardIndex      - index of this open pile to move.
   * @param destination    - PileType to move to.
   * @param destPileNumber - pile number to move to.
   * @throws IllegalArgumentException - if the open pile does not exist or is empty.
   */
  private void moveFromOpenPile(int pileNumber, int cardIndex,
                                PileType destination, int destPileNumber) {
    if (pileNumber > getNumOpenPiles() - 1) {
      throw new IllegalArgumentException("This pile does not exist!");
    }
    if (openPile.get(pileNumber).isEmpty()) {
      throw new IllegalArgumentException("There is not an open card here!");
    }
    //added 'if' statement to check for user trying to move the nth card, where n != 0.
    if (cardIndex != 0) {
      throw new IllegalArgumentException("There is not a card at this index!");
    }
    Card c = openPile.get(pileNumber).get(cardIndex);
    switch (destination) {
      case OPEN:
        moveToOpen(c, openPile.get(pileNumber), destPileNumber);
        break;
      case CASCADE:
        moveToCascade(c, openPile.get(pileNumber), destPileNumber);
        break;
      case FOUNDATION:
        moveToFoundation(c, openPile.get(pileNumber), destPileNumber);
        break;
      default:
        break;
    }
  }

  /**
   * moves the desired card to the desired foundation pile.
   *
   * @param c              - card to be moved.
   * @param cards          - list of cards to move to.
   * @param destPileNumber - number of foundation pile to move to.
   * @throws IllegalArgumentException - if the foundation pile does not exist or if it is
   *                                  not a valid move to the foundation pile.
   */
  private void moveToFoundation(Card c, List<Card> cards, int destPileNumber) {
    if (destPileNumber > 3) {
      throw new IllegalArgumentException("This foundation pile does not exist!");
    }
    if (foundationPile.get(destPileNumber).isEmpty()) {
      if (c.getRank().returnIndex() == 0) {
        cards.remove(c);
        foundationPile.get(destPileNumber).add(c);
      } else {
        throw new IllegalArgumentException("Invalid move to foundation pile!");
      }
    } else if (validMoveToFoundation(c, destPileNumber)) {
      cards.remove(c);
      foundationPile.get(destPileNumber).add(c);
    } else {
      throw new IllegalArgumentException("Invalid move to foundation pile!");
    }
  }

  /**
   * tests if the given card c can be moved to the given foundation pile number.
   *
   * @param c              - card to be moved
   * @param destPileNumber - foundation pile number to potentially move to.
   * @return - whether it is a valid move to move this card to this foundation pile.
   */
  private boolean validMoveToFoundation(Card c, int destPileNumber) {
    ArrayList<Card> cards = foundationPile.get(destPileNumber);
    Card compareCard = cards.get(cards.size() - 1);
    return ((c.getRank().returnIndex() == compareCard.getRank().returnIndex() + 1)
            && c.getSuit().toString().equals(compareCard.getSuit().toString()));
  }

  /**
   * moves the given card to the given cascade pile number.
   *
   * @param c              - card to be moved.
   * @param cards          - list of cards that the card was taken from.
   * @param destPileNumber - cascade pile to be moved to.
   * @throws IllegalArgumentException - if there is not a cascade pile here or if the
   *                                  move is invalid.
   */
  private void moveToCascade(Card c, List<Card> cards, int destPileNumber) {
    if (destPileNumber > getNumCascadePiles() - 1) {
      throw new IllegalArgumentException("This cascade pile does not exist!");
    }
    if (cascadePile.get(destPileNumber).isEmpty()) { //if trying to move to an empty spot
      cascadePile.get(destPileNumber).add(c);
      cards.remove(c);
    } else if (validMoveToCascade(c, destPileNumber)) {
      cascadePile.get(destPileNumber).add(c);
      cards.remove(c);
    } else {
      throw new IllegalArgumentException("Invalid move to cascade pile!");
    }
  }

  /**
   * returns whether the card c can be moved to the given cascade pile number.
   *
   * @param c              - card to be moved.
   * @param destPileNumber - cascade pile number that c should be moved to.
   * @return - boolean representation of whether moving this card here is valid.
   */
  private boolean validMoveToCascade(Card c, int destPileNumber) {
    ArrayList<Card> cards = cascadePile.get(destPileNumber);
    Card compareCard = cards.get(cards.size() - 1);
    return (c.getRank().returnIndex() == compareCard.getRank().returnIndex() - 1)
            && (c.getSuit().returnColor() != compareCard.getSuit().returnColor());
  }

  /**
   * moves the given card to the given open pile number.
   *
   * @param c              - card to be moved.
   * @param cards          - cards list from which c should be removed.
   * @param destPileNumber - open pile number to move this card to.
   * @throws IllegalArgumentException - if there is not an open pile here or if it is full.
   */
  private void moveToOpen(Card c, List<Card> cards, int destPileNumber) {
    if (destPileNumber > getNumOpenPiles() - 1) {
      throw new IllegalArgumentException(
              "This is an invalid move: there is not an open pile at this index!");
    } else if (openPile.get(destPileNumber).isEmpty()) {
      openPile.get(destPileNumber).add(c);
      cards.remove(c);
    } else {
      throw new IllegalArgumentException("There is already a card in this open pile.");
    }
  }

  /**
   * Returns whether the game is over - if all cards are in the foundation piles.
   *
   * @return - whether the game is over.
   */
  public boolean isGameOver() {
    return fullFoundationPiles();
  }

  /**
   * returns whether all foundation piles are full - 4 stacks of 13 cards.
   *
   * @return whether all foundation piles are full in this model.
   */
  private boolean fullFoundationPiles() {
    for (int i = 0; i < 4; i++) {
      if (this.foundationPile.get(i).size() != 13) {
        return false;
      }
    }
    return true;
  }


  /**
   * returns the number of cards in the given index of the given group of piles.
   *
   * @param index - index of pile to check
   * @param cards - list of cards to pull from
   * @return - number of cards in this list of list of cards at the given index.
   * @throws IllegalArgumentException - if the given index does not point to a list of cards.
   * @throws IllegalArgumentException - if the game has not begun.
   */
  private int validNumCardsAt(int index, ArrayList<ArrayList<Card>> cards) {
    if (!started) {
      throw new IllegalStateException("This game has not begun!");
    } else if (index > cards.size() - 1 || index < 0) {
      throw new IllegalArgumentException("Invalid index!");
    } else {
      return cards.get(index).size();
    }
  }

  /**
   * returns the number of cards in the foundation pile at the given index.
   *
   * @param index the index of the foundation pile, starting at 0
   * @return - number of cards in this foundation pile.
   */
  public int getNumCardsInFoundationPile(int index) {
    return validNumCardsAt(index, foundationPile);
  }

  /**
   * returns the number of cards in the cascade pile at the given index.
   *
   * @param index the index of the cascade pile, starting at 0
   * @return - number of cards in this cascade pile.
   */
  public int getNumCardsInCascadePile(int index) {
    return validNumCardsAt(index, cascadePile);
  }

  /**
   * returns the number of cards in the open pile at the given index.
   *
   * @param index the index of the open pile, starting at 0
   * @return - number of cards in this open pile.
   */
  public int getNumCardsInOpenPile(int index) {
    return validNumCardsAt(index, openPile);
  }


  /**
   * Helper method for getting numbers of different
   * types of piles. Returns -1 if the game has not started.
   *
   * @param cards - list of list of cards to look at
   * @return - number of piles that exist in the given list.
   */
  private int getNumPiles(ArrayList<ArrayList<Card>> cards) {
    if (!started) {
      return -1;
    } else {
      return cards.size();

    }
  }

  /**
   * Returns the number of open piles in the game.
   *
   * @return - number of open piles.
   */
  public int getNumOpenPiles() {
    return getNumPiles(openPile);
  }

  /**
   * Returns the number of cascade piles in the game.
   *
   * @return - number of casscade piles.
   */
  public int getNumCascadePiles() {
    return getNumPiles(cascadePile);
  }

  /**
   * gets the foundation card at the given index in the given pile.
   *
   * @param pileIndex the index of the foundation pile, starting at 0
   * @param cardIndex the index of the card in the above foundation pile, starting at 0
   * @return - card to be searched for.
   */
  public Card getFoundationCardAt(int pileIndex, int cardIndex) {
    return getCardAt(foundationPile, pileIndex, cardIndex, 'f');
  }

  /**
   * gets the cascade card at the given index in the given pile.
   *
   * @param pileIndex the index of the cascade pile, starting at 0
   * @param cardIndex the index of the card in the above cascade pile, starting at 0
   * @return - cascade card at given index of given pile.
   * @throws IllegalStateException - if the game has not begun.
   */
  public Card getCascadeCardAt(int pileIndex, int cardIndex) {
    return getCardAt(cascadePile, pileIndex, cardIndex, 'c');
  }

  //changed since assignment 2: combined the getCardAt methods into one larger method that is called
  //to decrease repetition

  /**
   * Method to get the card at a specific index in the given list of cards.
   * @param cards - list of cards to be referenced.
   * @param pileIndex - index of pile within the cards list to be looked at.
   * @param cardIndex - index of card within indexed pile.
   * @param type - character representing the type of pile, to help differentiate in
   *             return types between open and foundation/cascade. returns null if the index
   *             is -1 if the type is 'o' for open.
   * @return - the card at the given index.
   */
  private Card getCardAt(ArrayList<ArrayList<Card>> cards,
                         int pileIndex, int cardIndex, char type) {
    if (!started) {
      throw new IllegalStateException("The game has not started!");
    }
    else if (type == 'o') {
      if (pileIndex < 0 || pileIndex > cards.size() - 1) {
        throw new IllegalArgumentException("Invalid pile Index!");
      }
      else if (cards.get(pileIndex).isEmpty()) {
        return null;
      }
    }
    else {
      doesCardExist(cards, pileIndex, cardIndex);
    }
    return cards.get(pileIndex).get(cardIndex);
  }

  /**
   * Returns whether it is possible for a Card to exist at this pile index
   * and card index (ie. is it a negative card index or a card index that is
   * greater than the size of this pile? is the pile index negative or greater than
   * the number of this type of pile?
   * @param cards - list of lists of cards (cascade, foundation, or open piles)
   * @param pileIndex - index of pile being examined
   * @param cardIndex - index of card being examined
   */
  private void doesCardExist(ArrayList<ArrayList<Card>> cards,
                             int pileIndex, int cardIndex) {
    if (pileIndex < 0 || pileIndex > cards.size() - 1
            || cardIndex < 0 || cardIndex > cards.get(pileIndex).size() - 1) {
      throw new IllegalArgumentException("Card or pile does not exist!");
    }
  }

  /**
   * gets the open card at the given pile index.
   *
   * @param pileIndex the index of the open pile, starting at 0
   * @return - open card at given index.
   * @throws IllegalStateException - if the game has not begun.
   */
  public Card getOpenCardAt(int pileIndex) {
    return getCardAt(openPile, pileIndex, 0, 'o');
  }
}