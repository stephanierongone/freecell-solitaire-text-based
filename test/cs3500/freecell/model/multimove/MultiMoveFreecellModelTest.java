package cs3500.freecell.model.multimove;

import cs3500.freecell.model.Card;
import cs3500.freecell.model.PileType;
import cs3500.freecell.model.SuitType;
import cs3500.freecell.view.FreecellTextView;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static cs3500.freecell.model.RankType.ACE;
import static cs3500.freecell.model.RankType.TWO;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * Class to hold tests for a MultiMove freecell model.
 */
public class MultiMoveFreecellModelTest {

  //test to see if the game is restarted when startGame() is called on a
  //previously started game
  @Test
  public void testRestartGame() {
    MultiMoveFreecellModel model = new MultiMoveFreecellModel();
    List<Card> cards = model.getDeck();
    model.startGame(cards, 4, 4, false);
    FreecellTextView view = new FreecellTextView(model);
    String start = view.toString();
    model.move(PileType.CASCADE, 3, 12, PileType.OPEN, 2);
    model.move(PileType.CASCADE, 2, 12, PileType.FOUNDATION, 0);
    String middle = view.toString();
    assertNotEquals(start, middle);
    model.startGame(cards, 4, 4, false);
    String end = view.toString();
    assertEquals(start, end);
  }

  @Test
  public void testGetDeck() {
    MultiMoveFreecellModel model = new MultiMoveFreecellModel();
    List<Card> cards = model.getDeck();
    assertEquals(52, cards.size());
    for (int i = 0; i < 52; i++) {
      //check color
      assertEquals(i % 2, cards.get(i).getSuit().returnColor());
      //check suit
      switch (i % 4) {
        case 0:
          assertEquals("♣", cards.get(i).getSuit().toString());
          break;
        case 1:
          assertEquals("♦", cards.get(i).getSuit().toString());
          break;
        case 2:
          assertEquals("♠", cards.get(i).getSuit().toString());
          break;
        case 3:
          assertEquals("♥", cards.get(i).getSuit().toString());
          break;
        default:
          break;
      }
      if (i < 4) {
        assertEquals("K", cards.get(i).getRank().toString());
      } else if (i < 8) {
        assertEquals("Q", cards.get(i).getRank().toString());
      } else if (i < 12) {
        assertEquals("J", cards.get(i).getRank().toString());
      } else if (i < 16) {
        assertEquals("10", cards.get(i).getRank().toString());
      } else if (i < 20) {
        assertEquals("9", cards.get(i).getRank().toString());
      } else if (i < 24) {
        assertEquals("8", cards.get(i).getRank().toString());
      } else if (i < 28) {
        assertEquals("7", cards.get(i).getRank().toString());
      } else if (i < 32) {
        assertEquals("6", cards.get(i).getRank().toString());
      } else if (i < 36) {
        assertEquals("5", cards.get(i).getRank().toString());
      } else if (i < 40) {
        assertEquals("4", cards.get(i).getRank().toString());
      } else if (i < 44) {
        assertEquals("3", cards.get(i).getRank().toString());
      } else if (i < 48) {
        assertEquals("2", cards.get(i).getRank().toString());
      } else {
        assertEquals("A", cards.get(i).getRank().toString());
      }
    }
  }

  @Test
  public void testStartGameNumOpenPiles() {
    MultiMoveFreecellModel model = new MultiMoveFreecellModel();
    ArrayList<Card> deck = model.getDeck();
    model.startGame(deck, 8, 4, false);
    assertEquals(4, model.getNumOpenPiles());
  }

  @Test
  public void testStartGameNumCascadePiles() {
    MultiMoveFreecellModel model = new MultiMoveFreecellModel();
    ArrayList<Card> deck = model.getDeck();
    model.startGame(deck, 8, 4, false);
    assertEquals(8, model.getNumCascadePiles());
  }

  @Test
  public void testStartGameNumCardsInEachOpenPile() {
    MultiMoveFreecellModel model = new MultiMoveFreecellModel();
    ArrayList<Card> deck = model.getDeck();
    model.startGame(deck, 8, 4, false);
    assertEquals(0, model.getNumCardsInOpenPile(0));
    assertEquals(0, model.getNumCardsInOpenPile(1));
    assertEquals(0, model.getNumCardsInOpenPile(2));
    assertEquals(0, model.getNumCardsInOpenPile(3));
  }

  @Test
  public void testStartGameNumCardsInEachCascadePile() {
    MultiMoveFreecellModel model = new MultiMoveFreecellModel();
    ArrayList<Card> deck = model.getDeck();
    model.startGame(deck, 8, 4, false);
    assertEquals(7, model.getNumCardsInCascadePile(0));
    assertEquals(7, model.getNumCardsInCascadePile(1));
    assertEquals(7, model.getNumCardsInCascadePile(2));
    assertEquals(7, model.getNumCardsInCascadePile(3));
    assertEquals(6, model.getNumCardsInCascadePile(4));
    assertEquals(6, model.getNumCardsInCascadePile(5));
    assertEquals(6, model.getNumCardsInCascadePile(6));
    assertEquals(6, model.getNumCardsInCascadePile(7));
  }

  @Test
  public void testStartGameNumCardsInEachFoundationPile() {
    MultiMoveFreecellModel model = new MultiMoveFreecellModel();
    ArrayList<Card> deck = model.getDeck();
    model.startGame(deck, 8, 4, false);
    assertEquals(0, model.getNumCardsInFoundationPile(0));
    assertEquals(0, model.getNumCardsInFoundationPile(1));
    assertEquals(0, model.getNumCardsInFoundationPile(2));
    assertEquals(0, model.getNumCardsInFoundationPile(3));
  }

  //tests to make sure a game cannot be started if there aren't enough cascade piles
  @Test(expected = IllegalArgumentException.class)
  public void testStartGameNotEnoughCascadePiles() {
    MultiMoveFreecellModel model = new MultiMoveFreecellModel();
    model.startGame(model.getDeck(), 3, 2, true);
  }

  //tests to make sure a game cannot be started if there aren't enough open piles
  @Test(expected = IllegalArgumentException.class)
  public void testStartGameNotEnoughOpenPiles() {
    MultiMoveFreecellModel model = new MultiMoveFreecellModel();
    model.startGame(model.getDeck(), 5, 0, true);
  }

  //should fail with an insufficient amount of cards in the deck passed in
  @Test(expected = IllegalArgumentException.class)
  public void testEmptyDeck() {
    MultiMoveFreecellModel model = new MultiMoveFreecellModel();
    List<Card> c = new ArrayList<>();
    model.startGame(c, 4, 2, true);
  }

  //should fail if the deck has any duplicate cards
  @Test(expected = IllegalArgumentException.class)
  public void testDeckWithDuplicates() {
    MultiMoveFreecellModel model = new MultiMoveFreecellModel();
    List<Card> c = new ArrayList<>();
    Card c1 = new Card(ACE, SuitType.CLUB);
    Card c2 = new Card(ACE, SuitType.CLUB);
    c.add(c1);
    c.add(c2);
    model.startGame(c, 4, 2, true);
  }

  //test moving to a foundation pile that doesn't exist
  @Test(expected = IllegalArgumentException.class)
  public void testMoveToInvalidPile0() {
    MultiMoveFreecellModel model = new MultiMoveFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 4, 4, false);
    model.move(PileType.CASCADE, 0, 12, PileType.FOUNDATION, 5);
  }

  //test moving to an open pile that doesn't exist
  @Test(expected = IllegalArgumentException.class)
  public void testMoveToInvalidPile1() {
    MultiMoveFreecellModel model = new MultiMoveFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 4, 4, false);
    model.move(PileType.CASCADE, 0, 12, PileType.OPEN, 5);
  }

  //test moving to a cascade pile that doesn't exist
  @Test(expected = IllegalArgumentException.class)
  public void testMoveToInvalidPile2() {
    MultiMoveFreecellModel model = new MultiMoveFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 4, 4, false);
    model.move(PileType.CASCADE, 0, 12, PileType.CASCADE, 9);
  }

  //test if game is over
  @Test
  public void testGameContinuous() {
    MultiMoveFreecellModel model = new MultiMoveFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 4, 4, false);
    assertFalse(model.isGameOver());
  }

  @Test(expected = IllegalStateException.class)
  public void testMoveBeforeStarted() {
    MultiMoveFreecellModel model = new MultiMoveFreecellModel();
    model.move(PileType.CASCADE, 0, 12, PileType.FOUNDATION, 2);
  }

  //test that the game shuffles the deck when told to shuffle
  @Test
  public void testShuffle() {
    MultiMoveFreecellModel model1 = new MultiMoveFreecellModel();
    ArrayList<Card> cards = model1.getDeck();
    MultiMoveFreecellModel model2 = new MultiMoveFreecellModel();
    ArrayList<Card> cards2 = model2.getDeck();
    MultiMoveFreecellModel model3 = new MultiMoveFreecellModel();
    ArrayList<Card> cards3 = model3.getDeck();

    model1.startGame(cards, 4, 3, false);
    model2.startGame(cards2, 4, 3, true);
    model3.startGame(cards2, 4, 3, true);
    model3.startGame(cards2, 4, 3, true);
    FreecellTextView v1 = new FreecellTextView(model1);
    FreecellTextView v2 = new FreecellTextView(model2);
    FreecellTextView v3 = new FreecellTextView(model3);
    assertNotEquals(v1.toString(), v2.toString());
    assertNotEquals(v3.toString(), v2.toString());
    assertNotEquals(v3.toString(), v1.toString());
  }

  //test for moving a foundation card to foundation, illegal
  @Test(expected = IllegalArgumentException.class)
  public void testFoundationToFoundation() {
    MultiMoveFreecellModel model = new MultiMoveFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 4, 4, false);
    model.move(PileType.CASCADE, 2, 12, PileType.FOUNDATION, 1);
    model.move(PileType.FOUNDATION, 1, 0, PileType.FOUNDATION, 0);
  }

  //test for moving foundation to open, illegal
  @Test(expected = IllegalArgumentException.class)
  public void testFoundationToOpen() {
    MultiMoveFreecellModel model = new MultiMoveFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 4, 4, false);
    model.move(PileType.CASCADE, 2, 12, PileType.FOUNDATION, 1);
    model.move(PileType.FOUNDATION, 1, 0, PileType.OPEN, 0);
  }

  //test for moving foundation to cascade, illegal
  @Test(expected = IllegalArgumentException.class)
  public void testFoundationToCascade() {
    MultiMoveFreecellModel model = new MultiMoveFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 4, 4, false);
    model.move(PileType.CASCADE, 2, 12, PileType.FOUNDATION, 1);
    model.move(PileType.FOUNDATION, 1, 0, PileType.CASCADE, 2);
  }

  //tests moving from empty open to empty foundation, illegal
  @Test(expected = IllegalArgumentException.class)
  public void testMoveFromEmptyOpenToFoundation() {
    MultiMoveFreecellModel model = new MultiMoveFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 4, 4, false);
    assertEquals(4, model.getNumOpenPiles());
    model.move(PileType.OPEN, 0, 0, PileType.FOUNDATION, 1);
  }

  //tests for moving from empty open to empty open, illegal
  @Test(expected = IllegalArgumentException.class)
  public void testMoveFromEmptyOpenToEmptyOpen() {
    MultiMoveFreecellModel model = new MultiMoveFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 4, 4, false);
    assertEquals(4, model.getNumOpenPiles());
    model.move(PileType.OPEN, 0, 0, PileType.OPEN, 1);
  }

  //tests for moving from empty open to any cascade
  @Test(expected = IllegalArgumentException.class)
  public void testMoveEmptyOpenToCascade() {
    MultiMoveFreecellModel model = new MultiMoveFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 4, 4, false);
    assertEquals(4, model.getNumOpenPiles());
    model.move(PileType.OPEN, 1, 0, PileType.CASCADE, 2);
  }

  //test for trying to move to foundation when suits aren't right
  @Test(expected = IllegalArgumentException.class)
  public void testMoveIncorrectSuitToFoundation() {
    MultiMoveFreecellModel model = new MultiMoveFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 4, 4, false);
    model.move(PileType.CASCADE, 1, 12, PileType.FOUNDATION, 0);
    model.move(PileType.CASCADE, 2, 12, PileType.FOUNDATION, 1);
    model.move(PileType.CASCADE, 1, 11, PileType.FOUNDATION, 1);
  }

  //tests trying to move a card that isn't an ace to foundation
  @Test(expected = IllegalArgumentException.class)
  public void testMoveIncorrectRankToFoundation() {
    MultiMoveFreecellModel model = new MultiMoveFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 4, 4, false);
    model.move(PileType.CASCADE, 1, 12, PileType.FOUNDATION, 0);
    model.move(PileType.CASCADE, 1, 11, PileType.FOUNDATION, 1);
  }

  //test for moving an Ace into foundation
  @Test
  public void testMoveCorrectlyToFoundation() {
    MultiMoveFreecellModel model = new MultiMoveFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 4, 4, false);
    assertEquals(0, model.getNumCardsInFoundationPile(0));
    assertEquals(13, model.getNumCardsInCascadePile(1));
    model.move(PileType.CASCADE, 1, 12, PileType.FOUNDATION, 0);
    assertEquals(1, model.getNumCardsInFoundationPile(0));
    assertEquals(12, model.getNumCardsInCascadePile(1));
    model.move(PileType.CASCADE, 1, 11, PileType.FOUNDATION, 0);
    assertEquals(2, model.getNumCardsInFoundationPile(0));
    assertEquals(11, model.getNumCardsInCascadePile(1));
    assertEquals(ACE, model.getFoundationCardAt(0, 0).getRank());
    assertEquals(TWO, model.getFoundationCardAt(0, 1).getRank());
  }

  //test for moving card into wrong foundation
  @Test(expected = IllegalArgumentException.class)
  public void testMoveToWrongFoundation() {
    MultiMoveFreecellModel model = new MultiMoveFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 4, 4, false);
    model.move(PileType.CASCADE, 0, 12, PileType.FOUNDATION, 0);
    model.move(PileType.CASCADE, 1, 12, PileType.FOUNDATION, 1);
    model.move(PileType.CASCADE, 1, 11, PileType.FOUNDATION, 0);
  }

  //tests moving a card to foundation to stack suits with correct ranks
  @Test
  public void testStackIntoFoundation() {
    MultiMoveFreecellModel model = new MultiMoveFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 4, 4, false);
    assertEquals(13, model.getNumCardsInCascadePile(1));
    model.move(PileType.CASCADE, 1, 12, PileType.FOUNDATION, 0);
    model.move(PileType.CASCADE, 1, 11, PileType.FOUNDATION, 0);
    assertEquals(2, model.getNumCardsInFoundationPile(0));
    assertEquals(11, model.getNumCardsInCascadePile(1));
  }

  //tests for moving from empty cascade
  @Test(expected = IllegalArgumentException.class)
  public void testMoveFromEmptyCascade() {
    MultiMoveFreecellModel model = new MultiMoveFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 13, 5, false);
    model.move(PileType.CASCADE, 0, 3, PileType.OPEN, 0);
    model.move(PileType.CASCADE, 0, 2, PileType.OPEN, 1);
    model.move(PileType.CASCADE, 0, 1, PileType.OPEN, 2);
    model.move(PileType.CASCADE, 0, 0, PileType.OPEN, 3);
    model.move(PileType.CASCADE, 0, 0, PileType.OPEN, 4);
  }

  //tests for moving from Cascade to open
  @Test
  public void testMoveCascadeToOpenValid() {
    MultiMoveFreecellModel model = new MultiMoveFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 8, 4, false);
    assertEquals(7, model.getNumCardsInCascadePile(0));
    assertEquals(0, model.getNumCardsInOpenPile(0));
    model.move(PileType.CASCADE, 0, 6, PileType.OPEN, 0);
    assertEquals(6, model.getNumCardsInCascadePile(0));
    assertEquals(1, model.getNumCardsInOpenPile(0));
  }

  //tests for moving from cascade pile to open pile that has already been filled
  @Test(expected = IllegalArgumentException.class)
  public void testMoveToFilledOpen() {
    MultiMoveFreecellModel model = new MultiMoveFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 8, 4, false);
    model.move(PileType.CASCADE, 0, 6, PileType.OPEN, 1);
    model.move(PileType.CASCADE, 0, 5, PileType.OPEN, 1);
  }

  //tests for moving from one open pile to another that is filled
  @Test(expected = IllegalArgumentException.class)
  public void testMoveOpenToOpenFilled() {
    MultiMoveFreecellModel model = new MultiMoveFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 8, 4, false);
    model.move(PileType.CASCADE, 0, 6, PileType.OPEN, 1);
    model.move(PileType.CASCADE, 0, 5, PileType.OPEN, 2);
    model.move(PileType.OPEN, 1, 0, PileType.OPEN, 2);
  }

  //tests for legally moving an open card to a different open slot
  @Test
  public void testMoveOpenToOpenValid() {
    MultiMoveFreecellModel model = new MultiMoveFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 8, 4, false);
    model.move(PileType.CASCADE, 0, 6, PileType.OPEN, 1);
    assertEquals(1, model.getNumCardsInOpenPile(1));
    assertEquals(0, model.getNumCardsInOpenPile(3));
    model.move(PileType.OPEN, 1, 0, PileType.OPEN, 3);
    assertEquals(1, model.getNumCardsInOpenPile(3));
    assertEquals(0, model.getNumCardsInOpenPile(1));
  }

  //tests for moving one cascade card to another, but colors are the same
  @Test(expected = IllegalArgumentException.class)
  public void testSameColorToFoundation() {
    MultiMoveFreecellModel model = new MultiMoveFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 4, 4, false);
    model.move(PileType.CASCADE, 0, 12, PileType.OPEN, 0);
    model.move(PileType.CASCADE, 2, 12, PileType.CASCADE, 0);
  }

  //tests for moving a card of incorrect rank to cascade
  @Test(expected = IllegalArgumentException.class)
  public void testIncorrectRankToFoundation() {
    MultiMoveFreecellModel model = new MultiMoveFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 4, 4, false);
    model.move(PileType.CASCADE, 0, 12, PileType.CASCADE, 1);
  }

  //tests for correctly moving card of correct rank and color to cascade pile
  @Test
  public void testValidCascadeMove() {
    MultiMoveFreecellModel model = new MultiMoveFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 4, 4, false);
    model.move(PileType.CASCADE, 0, 12, PileType.FOUNDATION, 0);
    assertEquals(0, model.getCascadeCardAt(0, 11).getColor());
    assertEquals(TWO, model.getCascadeCardAt(0, 11).getRank());
    assertEquals(1, model.getCascadeCardAt(1, 12).getColor());
    assertEquals(ACE, model.getCascadeCardAt(1, 12).getRank());
  }

  //tests for gameOver method, should return true
  @Test
  public void testGameOverTrue() {
    MultiMoveFreecellModel model = new MultiMoveFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 4, 4, false);
    for (int i = 12; i >= 0; i--) {
      for (int j = 0; j < 4; j++) {
        model.move(PileType.CASCADE, j, i, PileType.FOUNDATION, j);
      }
    }
    assertTrue(model.isGameOver());
  }

  //tests for gameOver method, should return false if game has just started
  @Test
  public void testGameOverFalse() {
    MultiMoveFreecellModel model = new MultiMoveFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 4, 4, false);
    assertFalse(model.isGameOver());

    MultiMoveFreecellModel model1 = new MultiMoveFreecellModel();
    ArrayList<Card> cards2 = model.getDeck();
    model1.startGame(cards2, 4, 4, false);
    model1.move(PileType.CASCADE, 1, 12, PileType.FOUNDATION, 0);
    assertEquals(1, model1.getNumCardsInFoundationPile(0));
    assertFalse(model1.isGameOver());
  }

  //tests for getNumCardsInFoundationPile
  @Test(expected = IllegalArgumentException.class)
  public void testGetNumCardsInFoundationTooHighIndex() {
    MultiMoveFreecellModel model = new MultiMoveFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 4, 4, false);
    model.getNumCardsInFoundationPile(4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetNumCardsInFoundationTooLowIndex() {
    MultiMoveFreecellModel model = new MultiMoveFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 4, 4, false);
    model.getNumCardsInFoundationPile(-2);
  }

  @Test(expected = IllegalStateException.class)
  public void testGetNumCardsInFoundationGameNotStarted() {
    MultiMoveFreecellModel model = new MultiMoveFreecellModel();
    model.getNumCardsInFoundationPile(-2);
  }

  //tests for getNumCardsInCascadePile
  @Test(expected = IllegalArgumentException.class)
  public void testGetNumCardsInCascadeTooHighIndex() {
    MultiMoveFreecellModel model = new MultiMoveFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 4, 4, false);
    model.getNumCardsInCascadePile(4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetNumCardsInCascadeTooLowIndex() {
    MultiMoveFreecellModel model = new MultiMoveFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 4, 4, false);
    model.getNumCardsInCascadePile(-2);
  }

  @Test(expected = IllegalStateException.class)
  public void testGetNumCardsInCascadeGameNotStarted() {
    MultiMoveFreecellModel model = new MultiMoveFreecellModel();
    model.getNumCardsInCascadePile(-2);
  }


  //tests for getNumCardsInOpenPile
  @Test(expected = IllegalArgumentException.class)
  public void testGetNumCardsInOpenTooHighIndex() {
    MultiMoveFreecellModel model = new MultiMoveFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 4, 4, false);
    model.getNumCardsInOpenPile(4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetNumCardsInOpenTooLowIndex() {
    MultiMoveFreecellModel model = new MultiMoveFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 4, 4, false);
    model.getNumCardsInOpenPile(-2);
  }

  @Test(expected = IllegalStateException.class)
  public void testGetNumCardsInOpenGameNotStarted() {
    MultiMoveFreecellModel model = new MultiMoveFreecellModel();
    model.getNumCardsInOpenPile(-2);
  }

  //test for getNumOpenPiles
  @Test
  public void testGetNumOpenPiles() {
    MultiMoveFreecellModel model = new MultiMoveFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    assertEquals(-1, model.getNumOpenPiles());
    model.startGame(cards, 4, 4, false);
    assertEquals(4, model.getNumOpenPiles());
  }

  //test for getNumCascadePiles
  @Test
  public void testGetNumCascadePiles() {
    MultiMoveFreecellModel model = new MultiMoveFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    assertEquals(-1, model.getNumCascadePiles());
    model.startGame(cards, 4, 4, false);
    assertEquals(4, model.getNumCascadePiles());
  }

  //tests for getFoundationCardAt
  @Test(expected = IllegalStateException.class)
  public void testGetFoundationCardNotStarted() {
    MultiMoveFreecellModel model = new MultiMoveFreecellModel();
    model.getFoundationCardAt(0, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetFoundationCardTooLowPile() {
    MultiMoveFreecellModel model = new MultiMoveFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 4, 4, false);
    model.getFoundationCardAt(-1, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetFoundationCardTooHighPile() {
    MultiMoveFreecellModel model = new MultiMoveFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 4, 4, false);
    model.getFoundationCardAt(5, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetFoundationCardTooLowCard() {
    MultiMoveFreecellModel model = new MultiMoveFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 4, 4, false);
    model.move(PileType.CASCADE, 0, 12, PileType.FOUNDATION, 3);
    model.getFoundationCardAt(3, -3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetFoundationCardTooHighCard() {
    MultiMoveFreecellModel model = new MultiMoveFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 4, 4, false);
    model.move(PileType.CASCADE, 0, 12, PileType.FOUNDATION, 3);
    model.getFoundationCardAt(3, 2);
  }

  @Test
  public void testGetFoundationCardAt() {
    MultiMoveFreecellModel model = new MultiMoveFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 4, 4, false);
    model.move(PileType.CASCADE, 0, 12, PileType.FOUNDATION, 3);
    assertEquals(ACE, model.getFoundationCardAt(3, 0).getRank());
  }

  //tests for getCascadeCardAt
  @Test(expected = IllegalStateException.class)
  public void testGetCascadeCardNotStarted() {
    MultiMoveFreecellModel model = new MultiMoveFreecellModel();
    model.getCascadeCardAt(0, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCascadeCardTooLowPile() {
    MultiMoveFreecellModel model = new MultiMoveFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 4, 4, false);
    model.getCascadeCardAt(-1, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCascadeCardTooHighPile() {
    MultiMoveFreecellModel model = new MultiMoveFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 4, 4, false);
    model.getCascadeCardAt(5, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCascadeCardTooLowCard() {
    MultiMoveFreecellModel model = new MultiMoveFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 4, 4, false);
    model.move(PileType.CASCADE, 0, 12, PileType.CASCADE, 3);
    model.getCascadeCardAt(3, -3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCascadeCardTooHighCard() {
    MultiMoveFreecellModel model = new MultiMoveFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 4, 4, false);
    model.move(PileType.CASCADE, 0, 12, PileType.CASCADE, 3);
    model.getCascadeCardAt(3, 2);
  }

  @Test
  public void testGetCascadeCardAt() {
    MultiMoveFreecellModel model = new MultiMoveFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 4, 4, false);
    assertEquals(ACE, model.getCascadeCardAt(2, 12).getRank());
  }

  //tests for getOpenCardAt
  @Test(expected = IllegalStateException.class)
  public void testGetOpenCardNotStarted() {
    MultiMoveFreecellModel model = new MultiMoveFreecellModel();
    model.getOpenCardAt(0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetOpenCardTooLowPile() {
    MultiMoveFreecellModel model = new MultiMoveFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 4, 4, false);
    model.getOpenCardAt(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetOpenCardTooHighPile() {
    MultiMoveFreecellModel model = new MultiMoveFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 4, 4, false);
    model.getOpenCardAt(6);
  }

  @Test
  public void testGetOpenCardAt() {
    MultiMoveFreecellModel model = new MultiMoveFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 4, 4, false);
    model.move(PileType.CASCADE, 3, 12, PileType.OPEN, 2);
    assertEquals(ACE, model.getOpenCardAt(2).getRank());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveTooManyCardsToOpen() {
    MultiMoveFreecellModel model = new MultiMoveFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 4, 4, false);
    model.move(PileType.CASCADE, 1, 3, PileType.OPEN, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveTooManyCardsToFoundation() {
    MultiMoveFreecellModel model = new MultiMoveFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 4, 4, false);
    model.move(PileType.CASCADE, 1, 3, PileType.FOUNDATION, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveTooManyCardsToEmptyCascade() {
    MultiMoveFreecellModel model = new MultiMoveFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 4, 13, false);
    //empty out a cascade pile
    assertEquals(13, model.getNumCardsInCascadePile(1));
    model.move(PileType.CASCADE, 1, 12, PileType.OPEN, 0);
    model.move(PileType.CASCADE, 1, 11, PileType.OPEN, 1);
    model.move(PileType.CASCADE, 1, 10, PileType.OPEN, 2);
    model.move(PileType.CASCADE, 1, 9, PileType.OPEN, 3);
    model.move(PileType.CASCADE, 1, 8, PileType.OPEN, 4);
    model.move(PileType.CASCADE, 1, 7, PileType.OPEN, 5);
    model.move(PileType.CASCADE, 1, 6, PileType.OPEN, 6);
    model.move(PileType.CASCADE, 1, 5, PileType.OPEN, 7);
    model.move(PileType.CASCADE, 1, 4, PileType.OPEN, 8);
    model.move(PileType.CASCADE, 1, 3, PileType.OPEN, 9);
    model.move(PileType.CASCADE, 1, 2, PileType.OPEN, 10);
    model.move(PileType.CASCADE, 1, 1, PileType.OPEN, 11);
    model.move(PileType.CASCADE, 1, 0, PileType.OPEN, 12);
    //check that this pile is now empty
    assertEquals(0, model.getNumCardsInCascadePile(1));
    assertEquals(13, model.getNumCardsInCascadePile(2));
    model.move(PileType.CASCADE, 2, 3, PileType.CASCADE, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMultiMoveInvalidBuild() {
    MultiMoveFreecellModel model = new MultiMoveFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 4, 13, false);
    //move all cards out of one of the cascade piles to make room for what should be a valid move
    model.move(PileType.CASCADE, 2, 12, PileType.OPEN, 0);
    model.move(PileType.CASCADE, 2, 11, PileType.OPEN, 1);
    model.move(PileType.CASCADE, 2, 10, PileType.OPEN, 2);
    model.move(PileType.CASCADE, 2, 9, PileType.OPEN, 3);
    model.move(PileType.CASCADE, 2, 8, PileType.OPEN, 4);
    model.move(PileType.CASCADE, 2, 7, PileType.OPEN, 5);
    model.move(PileType.CASCADE, 2, 6, PileType.OPEN, 6);
    model.move(PileType.CASCADE, 2, 5, PileType.OPEN, 7);
    model.move(PileType.CASCADE, 2, 4, PileType.OPEN, 8);
    model.move(PileType.CASCADE, 2, 3, PileType.OPEN, 9);
    model.move(PileType.CASCADE, 2, 2, PileType.OPEN, 10);
    model.move(PileType.CASCADE, 2, 1, PileType.OPEN, 11);
    model.move(PileType.CASCADE, 2, 0, PileType.OPEN, 12);
    assertEquals(1, model.getCascadeCardAt(1, 12).getColor());
    assertEquals(1, model.getCascadeCardAt(1, 11).getColor());
    assertEquals(SuitType.DIAMOND, model.getCascadeCardAt(1, 12).getSuit());
    assertEquals(SuitType.DIAMOND, model.getCascadeCardAt(1, 11).getSuit());
    //this is an invalid build
    model.move(PileType.CASCADE, 1, 11, PileType.CASCADE, 2);
  }

  @Test
  public void testMultiMoveValidBuildToFilled() {
    MultiMoveFreecellModel model = new MultiMoveFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 4, 13, false);
    model.move(PileType.CASCADE, 1, 12, PileType.OPEN, 0);
    model.move(PileType.CASCADE, 0, 12, PileType.CASCADE, 1);
    model.move(PileType.CASCADE, 0, 11, PileType.OPEN, 1);
    assertEquals(11, model.getNumCardsInCascadePile(0));
    assertEquals(13, model.getNumCardsInCascadePile(1));
    model.move(PileType.CASCADE, 1, 11, PileType.CASCADE, 0);
    assertEquals(13, model.getNumCardsInCascadePile(0));
    assertEquals(11, model.getNumCardsInCascadePile(1));
  }

  @Test
  public void testMultiMoveValidBuildToEmpty() {
    MultiMoveFreecellModel model = new MultiMoveFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 4, 30, false);
    //move all cards out of one of the cascade piles to make room for what should be a valid move
    model.move(PileType.CASCADE, 2, 12, PileType.OPEN, 0);
    model.move(PileType.CASCADE, 2, 11, PileType.OPEN, 1);
    model.move(PileType.CASCADE, 2, 10, PileType.OPEN, 2);
    model.move(PileType.CASCADE, 2, 9, PileType.OPEN, 3);
    model.move(PileType.CASCADE, 2, 8, PileType.OPEN, 4);
    model.move(PileType.CASCADE, 2, 7, PileType.OPEN, 5);
    model.move(PileType.CASCADE, 2, 6, PileType.OPEN, 6);
    model.move(PileType.CASCADE, 2, 5, PileType.OPEN, 7);
    model.move(PileType.CASCADE, 2, 4, PileType.OPEN, 8);
    model.move(PileType.CASCADE, 2, 3, PileType.OPEN, 9);
    model.move(PileType.CASCADE, 2, 2, PileType.OPEN, 10);
    model.move(PileType.CASCADE, 2, 1, PileType.OPEN, 11);
    model.move(PileType.CASCADE, 2, 0, PileType.OPEN, 12);
    //pile two is empty
    //get ready for valid build
    model.move(PileType.CASCADE, 0, 12, PileType.OPEN, 13);
    model.move(PileType.CASCADE, 1, 12, PileType.CASCADE, 0);
    model.move(PileType.CASCADE, 1, 11, PileType.OPEN, 14);

    //check pile lengths
    assertEquals(0, model.getNumCardsInCascadePile(2));
    assertEquals(11, model.getNumCardsInCascadePile(1));
    assertEquals(13, model.getNumCardsInCascadePile(0));
    //make multimove
    model.move(PileType.CASCADE, 0, 11, PileType.CASCADE, 2);
    //verify that there are now correct amounts of cards
    assertEquals(2, model.getNumCardsInCascadePile(2));
    assertEquals(11, model.getNumCardsInCascadePile(1));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testValidBuildValidMoveNotEnoughOpenPiles() {
    MultiMoveFreecellModel model = new MultiMoveFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 4, 2, false);
    model.move(PileType.CASCADE, 1, 12, PileType.OPEN, 0);
    model.move(PileType.CASCADE, 0, 12, PileType.CASCADE, 1);
    model.move(PileType.CASCADE, 0, 11, PileType.OPEN, 1);
    assertEquals(11, model.getNumCardsInCascadePile(0));
    assertEquals(13, model.getNumCardsInCascadePile(1));
    model.move(PileType.CASCADE, 1, 11, PileType.CASCADE, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidBuildSameColorMulti() {
    MultiMoveFreecellModel model = new MultiMoveFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 4, 2, false);
    model.move(PileType.CASCADE, 1, 12, PileType.OPEN, 0);
    model.move(PileType.CASCADE, 1, 11, PileType.OPEN, 0);
    //the top card in cascade pile 1 is now a 3, so a 2 can be placed on top of it.
    //this is not a valid build that is attempting to be moved,
    // because both cards are the same color.
    model.move(PileType.CASCADE, 0, 11, PileType.CASCADE, 1);

  }

  //tests if the model will throw an exception if you try to place a valid build onto the same color
  @Test(expected = IllegalArgumentException.class)
  public void testValidBuildToSameColorMulti() {
    MultiMoveFreecellModel model = new MultiMoveFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 4, 4, false);
    //get ready for valid build
    model.move(PileType.CASCADE, 1, 12, PileType.OPEN, 0);
    model.move(PileType.CASCADE, 0, 12, PileType.CASCADE, 1);
    model.move(PileType.CASCADE, 0, 11, PileType.OPEN, 1);
    //this is now a valid build at cascade pile 1, cards 11-12
    //going to move to a three (correct by rank) but the wrong color
    model.move(PileType.CASCADE, 3, 12, PileType.OPEN, 2);
    model.move(PileType.CASCADE, 3, 11, PileType.OPEN, 3);
    //move to this pile
    model.move(PileType.CASCADE, 1, 11, PileType.CASCADE, 3);
  }

  //tests if the model will throw an exception if you try to place a valid build onto the wrong rank
  @Test(expected = IllegalArgumentException.class)
  public void testValidBuildToWrongRankMulti() {
    MultiMoveFreecellModel model = new MultiMoveFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 4, 4, false);
    //get ready for valid build
    model.move(PileType.CASCADE, 1, 12, PileType.OPEN, 0);
    model.move(PileType.CASCADE, 0, 12, PileType.CASCADE, 1);
    model.move(PileType.CASCADE, 0, 11, PileType.OPEN, 1);
    //this is now a valid build at cascade pile 1, cards 11-12
    //going to move to an ace card
    model.move(PileType.CASCADE, 1, 11, PileType.CASCADE, 3);
  }

  //tests to see that the move method throws an error if the game has not been started
  @Test(expected = IllegalStateException.class)
  public void testMoveGameNotStarted() {
    MultiMoveFreecellModel model = new MultiMoveFreecellModel();
    model.move(PileType.CASCADE, 2, 12, PileType.OPEN, 3);
  }

  //tests to see that the move method throws an illegalargumentexception
  //when the card index is negative
  @Test(expected = IllegalArgumentException.class)
  public void testMoveNegativeCardIndex() {
    MultiMoveFreecellModel model = new MultiMoveFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 4, 4, false);
    model.move(PileType.CASCADE, 2, -1, PileType.OPEN, 0);
  }

  //tests to see that the move method throws an illegalargumentexception
  //when the destination pile index is negative
  @Test(expected = IllegalArgumentException.class)
  public void testMoveNegativeDestPileIndex() {
    MultiMoveFreecellModel model = new MultiMoveFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 4, 4, false);
    model.move(PileType.CASCADE, 2, 12, PileType.OPEN, -2);
  }

  //tests to see that the move method throws an illegalargumentexception
  //when the source pile index is negative
  @Test(expected = IllegalArgumentException.class)
  public void testMoveNegativeSourcePileIndex() {
    MultiMoveFreecellModel model = new MultiMoveFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 4, 4, false);
    model.move(PileType.CASCADE, -1, 12, PileType.OPEN, 0);
  }

  //tests to see that the model throws an error when the pile is greater
  //than the number of piles that exist for cascades
  @Test(expected = IllegalArgumentException.class)
  public void testCascadePileIndexTooHigh() {
    MultiMoveFreecellModel model = new MultiMoveFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 4, 4, false);
    model.move(PileType.CASCADE, 5, 12, PileType.OPEN, 2);
  }

  //tests to see that the model throws an error when the pile is greater
  //than the number of piles that exist for opens
  @Test(expected = IllegalArgumentException.class)
  public void testOpenPileIndexTooHigh() {
    MultiMoveFreecellModel model = new MultiMoveFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 4, 4, false);
    model.move(PileType.OPEN, 5, 12, PileType.OPEN, 2);
  }

  //tests to see that the model throws an error when the pile is greater
  //than the number of piles that exist for foundations
  @Test(expected = IllegalArgumentException.class)
  public void testFoundationPileIndexTooHigh() {
    MultiMoveFreecellModel model = new MultiMoveFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 4, 4, false);
    model.move(PileType.FOUNDATION, 5, 12, PileType.OPEN, 2);
  }

  //tests to see that the model throws an error when the cardIndex is
  //greater than the number of cards in that cascade pile
  @Test(expected = IllegalArgumentException.class)
  public void testCascadeCardIndexTooHigh() {
    MultiMoveFreecellModel model = new MultiMoveFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 4, 4, false);
    model.move(PileType.CASCADE, 2, 14, PileType.OPEN, 2);
  }

  //tests to see that the model throws an error when the cardIndex is
  //greater than the number of cards in that open pile
  @Test(expected = IllegalArgumentException.class)
  public void testOpenCardIndexTooHigh() {
    MultiMoveFreecellModel model = new MultiMoveFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 4, 4, false);
    model.move(PileType.CASCADE, 2, 12, PileType.OPEN, 2);
    model.move(PileType.OPEN, 2, 1, PileType.OPEN, 1);
  }





}