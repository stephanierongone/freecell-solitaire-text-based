package cs3500.freecell.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import cs3500.freecell.view.FreecellTextView;


import static cs3500.freecell.model.RankType.ACE;
import static cs3500.freecell.model.RankType.TWO;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Class to represent a Test on a simple freecell model.
 */
public class FreecellModelTest {

  @Test
  public void testGetDeck() {
    SimpleFreecellModel model = new SimpleFreecellModel();
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
      }
      else if (i < 8) {
        assertEquals("Q", cards.get(i).getRank().toString());
      }
      else if (i < 12) {
        assertEquals("J", cards.get(i).getRank().toString());
      }
      else if (i < 16) {
        assertEquals("10", cards.get(i).getRank().toString());
      }
      else if (i < 20) {
        assertEquals("9", cards.get(i).getRank().toString());
      }
      else if (i < 24) {
        assertEquals("8", cards.get(i).getRank().toString());
      }
      else if (i < 28) {
        assertEquals("7", cards.get(i).getRank().toString());
      }
      else if (i < 32) {
        assertEquals("6", cards.get(i).getRank().toString());
      }
      else if (i < 36) {
        assertEquals("5", cards.get(i).getRank().toString());
      }
      else if (i < 40) {
        assertEquals("4", cards.get(i).getRank().toString());
      }
      else if (i < 44) {
        assertEquals("3", cards.get(i).getRank().toString());
      }
      else if (i < 48) {
        assertEquals("2", cards.get(i).getRank().toString());
      }
      else {
        assertEquals("A", cards.get(i).getRank().toString());
      }
    }
  }

  @Test
  public void testStartGame() {
    SimpleFreecellModel model = new SimpleFreecellModel();
    ArrayList<Card> deck = model.getDeck();
    model.startGame(deck, 8, 4, false);
    //open pile checks - should be none in any of the open piles
    assertEquals(4, model.getNumOpenPiles());
    assertEquals(0, model.getNumCardsInOpenPile(0));
    assertEquals(0, model.getNumCardsInOpenPile(1));
    assertEquals(0, model.getNumCardsInOpenPile(2));
    assertEquals(0, model.getNumCardsInOpenPile(3));

    //cascade pile checks, assumed 8 cascade piles by default in this constructor
    assertEquals(8, model.getNumCascadePiles());
    assertEquals(7, model.getNumCardsInCascadePile(0));
    assertEquals(7, model.getNumCardsInCascadePile(1));
    assertEquals(7, model.getNumCardsInCascadePile(2));
    assertEquals(7, model.getNumCardsInCascadePile(3));
    assertEquals(6, model.getNumCardsInCascadePile(4));
    assertEquals(6, model.getNumCardsInCascadePile(5));
    assertEquals(6, model.getNumCardsInCascadePile(6));
    assertEquals(6, model.getNumCardsInCascadePile(7));

    //foundation pile checks
    assertEquals(0, model.getNumCardsInFoundationPile(0));
    assertEquals(0, model.getNumCardsInFoundationPile(1));
    assertEquals(0, model.getNumCardsInFoundationPile(2));
    assertEquals(0, model.getNumCardsInFoundationPile(3));

  }

  //tests to make sure a game cannot be started if there aren't enough cascade piles
  @Test(expected = IllegalArgumentException.class)
  public void testStartGameNotEnoughCascadePiles() {
    SimpleFreecellModel model = new SimpleFreecellModel();
    model.startGame(model.getDeck(), 3, 2, true);
  }

  //tests to make sure a game cannot be started if there aren't enough open piles
  @Test(expected = IllegalArgumentException.class)
  public void testStartGameNotEnoughOpenPiles() {
    SimpleFreecellModel model = new SimpleFreecellModel();
    model.startGame(model.getDeck(), 5, 0, true);
  }

  //should fail with an insufficient amount of cards in the deck passed in
  @Test(expected = IllegalArgumentException.class)
  public void testEmptyDeck() {
    SimpleFreecellModel model = new SimpleFreecellModel();
    List<Card> c = new ArrayList<>();
    model.startGame(c, 4, 2, true);
  }

  //should fail if the deck has any duplicate cards
  @Test(expected = IllegalArgumentException.class)
  public void testDeckWithDuplicates() {
    SimpleFreecellModel model = new SimpleFreecellModel();
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
    SimpleFreecellModel model = new SimpleFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 4, 4, false);
    model.move(PileType.CASCADE, 0, 12, PileType.FOUNDATION, 5);
  }

  //test moving to an open pile that doesn't exist
  @Test(expected = IllegalArgumentException.class)
  public void testMoveToInvalidPile1() {
    SimpleFreecellModel model = new SimpleFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 4, 4, false);
    model.move(PileType.CASCADE, 0, 12, PileType.OPEN, 5);
  }

  //test moving to a cascade pile that doesn't exist
  @Test(expected = IllegalArgumentException.class)
  public void testMoveToInvalidPile2() {
    SimpleFreecellModel model = new SimpleFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 4, 4, false);
    model.move(PileType.CASCADE, 0, 12, PileType.CASCADE, 9);
  }


  //test if game is over
  @Test
  public void testGameContinuous() {
    SimpleFreecellModel model = new SimpleFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 4, 4, false);
    assertFalse(model.isGameOver());
  }

  @Test(expected = IllegalStateException.class)
  public void testMoveBeforeStarted() {
    SimpleFreecellModel model = new SimpleFreecellModel();
    model.move(PileType.CASCADE, 0, 12, PileType.FOUNDATION, 2);
  }

  //test that the game shuffles the deck when told to shuffle
  @Test
  public void testShuffle() {
    SimpleFreecellModel model1 = new SimpleFreecellModel();
    ArrayList<Card> cards = model1.getDeck();
    SimpleFreecellModel model2 = new SimpleFreecellModel();
    ArrayList<Card> cards2 = model2.getDeck();
    SimpleFreecellModel model3 = new SimpleFreecellModel();
    ArrayList<Card> cards3 = model3.getDeck();

    model1.startGame(cards, 4,3,false);
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
    SimpleFreecellModel model = new SimpleFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 4, 4, false);
    model.move(PileType.CASCADE, 2, 12, PileType.FOUNDATION, 1);
    model.move(PileType.FOUNDATION, 1, 0, PileType.FOUNDATION, 0);
  }

  //test for moving foundation to open, illegal
  @Test(expected = IllegalArgumentException.class)
  public void testFoundationToOpen() {
    SimpleFreecellModel model = new SimpleFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 4, 4, false);
    model.move(PileType.CASCADE, 2, 12, PileType.FOUNDATION, 1);
    model.move(PileType.FOUNDATION, 1, 0, PileType.OPEN, 0);
  }

  //test for moving foundation to cascade, illegal
  @Test(expected = IllegalArgumentException.class)
  public void testFoundationToCascade() {
    SimpleFreecellModel model = new SimpleFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 4, 4, false);
    model.move(PileType.CASCADE, 2, 12, PileType.FOUNDATION, 1);
    model.move(PileType.FOUNDATION, 1, 0, PileType.CASCADE, 2);
  }

  //tests moving from empty open to empty foundation, illegal
  @Test(expected = IllegalArgumentException.class)
  public void testMoveFromEmptyOpenToFoundation() {
    SimpleFreecellModel model = new SimpleFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 4, 4, false);
    assertEquals(4, model.getNumOpenPiles());
    model.move(PileType.OPEN, 0, 0, PileType.FOUNDATION, 1);
  }

  //tests for moving from empty open to empty open, illegal
  @Test(expected = IllegalArgumentException.class)
  public void testMoveFromEmptyOpenToEmptyOpen() {
    SimpleFreecellModel model = new SimpleFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 4, 4, false);
    assertEquals(4, model.getNumOpenPiles());
    model.move(PileType.OPEN, 0, 0, PileType.OPEN, 1);
  }

  //tests for moving from empty open to any cascade
  @Test(expected = IllegalArgumentException.class)
  public void testMoveEmptyOpenToCascade() {
    SimpleFreecellModel model = new SimpleFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 4, 4, false);
    assertEquals(4, model.getNumOpenPiles());
    model.move(PileType.OPEN, 1, 0, PileType.CASCADE, 2);
  }

  //test for trying to move anything but the top card from cascade pile
  @Test(expected = IllegalArgumentException.class)
  public void testMoveLowerCardFromCascade() {
    SimpleFreecellModel model = new SimpleFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 4, 4, false);
    model.move(PileType.CASCADE, 2, 4, PileType.OPEN, 2);
  }

  //test for trying to move to foundation when suits aren't right
  @Test(expected = IllegalArgumentException.class)
  public void testMoveIncorrectSuitToFoundation() {
    SimpleFreecellModel model = new SimpleFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 4, 4, false);
    model.move(PileType.CASCADE, 1, 12, PileType.FOUNDATION, 0);
    model.move(PileType.CASCADE, 2, 12, PileType.FOUNDATION, 1);
    model.move(PileType.CASCADE, 1, 11, PileType.FOUNDATION, 1);
  }

  //tests trying to move a card that isn't an ace to foundation
  @Test(expected = IllegalArgumentException.class)
  public void testMoveIncorrectRankToFoundation() {
    SimpleFreecellModel model = new SimpleFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 4, 4, false);
    model.move(PileType.CASCADE, 1, 12, PileType.FOUNDATION, 0);
    model.move(PileType.CASCADE, 1, 11, PileType.FOUNDATION, 1);
  }

  //test for moving an Ace into foundation
  @Test
  public void testMoveCorrectlyToFoundation() {
    SimpleFreecellModel model = new SimpleFreecellModel();
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
    SimpleFreecellModel model = new SimpleFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 4, 4, false);
    model.move(PileType.CASCADE, 0, 12, PileType.FOUNDATION, 0);
    model.move(PileType.CASCADE, 1, 12, PileType.FOUNDATION, 1);
    model.move(PileType.CASCADE, 1, 11, PileType.FOUNDATION, 0);
  }

  //tests moving a card to foundation to stack suits with correct ranks
  @Test
  public void testStackIntoFoundation() {
    SimpleFreecellModel model = new SimpleFreecellModel();
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
    SimpleFreecellModel model = new SimpleFreecellModel();
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
    SimpleFreecellModel model = new SimpleFreecellModel();
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
    SimpleFreecellModel model = new SimpleFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 8, 4, false);
    model.move(PileType.CASCADE, 0, 6, PileType.OPEN, 1);
    model.move(PileType.CASCADE, 0, 5, PileType.OPEN, 1);
  }

  //tests for moving from one open pile to another that is filled
  @Test(expected = IllegalArgumentException.class)
  public void testMoveOpenToOpenFilled() {
    SimpleFreecellModel model = new SimpleFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 8, 4, false);
    model.move(PileType.CASCADE, 0, 6, PileType.OPEN, 1);
    model.move(PileType.CASCADE, 0, 5, PileType.OPEN, 2);
    model.move(PileType.OPEN, 1, 0, PileType.OPEN, 2);
  }

  //tests for legally moving an open card to a different open slot
  @Test
  public void testMoveOpenToOpenValid() {
    SimpleFreecellModel model = new SimpleFreecellModel();
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
    SimpleFreecellModel model = new SimpleFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 4, 4, false);
    model.move(PileType.CASCADE, 0, 12, PileType.OPEN, 0);
    model.move(PileType.CASCADE, 2, 12, PileType.CASCADE, 0);
  }

  //tests for moving a card of incorrect rank to cascade
  @Test(expected = IllegalArgumentException.class)
  public void testIncorrectRankToFoundation() {
    SimpleFreecellModel model = new SimpleFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 4, 4, false);
    model.move(PileType.CASCADE, 0, 12, PileType.CASCADE, 1);
  }

  //tests for correctly moving card of correct rank and color to cascade pile
  @Test
  public void testValidCascadeMove() {
    SimpleFreecellModel model = new SimpleFreecellModel();
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
    SimpleFreecellModel model = new SimpleFreecellModel();
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
    SimpleFreecellModel model = new SimpleFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 4, 4, false);
    assertFalse(model.isGameOver());

    SimpleFreecellModel model1 = new SimpleFreecellModel();
    ArrayList<Card> cards2 = model.getDeck();
    model1.startGame(cards2, 4, 4, false);
    model1.move(PileType.CASCADE, 1, 12, PileType.FOUNDATION, 0);
    assertEquals(1, model1.getNumCardsInFoundationPile(0));
    assertFalse(model1.isGameOver());
  }

  //tests for getNumCardsInFoundationPile
  @Test(expected = IllegalArgumentException.class)
  public void testGetNumCardsInFoundationTooHighIndex() {
    SimpleFreecellModel model = new SimpleFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 4, 4, false);
    model.getNumCardsInFoundationPile(4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetNumCardsInFoundationTooLowIndex() {
    SimpleFreecellModel model = new SimpleFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 4, 4, false);
    model.getNumCardsInFoundationPile(-2);
  }

  @Test(expected = IllegalStateException.class)
  public void testGetNumCardsInFoundationGameNotStarted() {
    SimpleFreecellModel model = new SimpleFreecellModel();
    model.getNumCardsInFoundationPile(-2);
  }


  //tests for getNumCardsInCascadePile
  @Test(expected = IllegalArgumentException.class)
  public void testGetNumCardsInCascadeTooHighIndex() {
    SimpleFreecellModel model = new SimpleFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 4, 4, false);
    model.getNumCardsInCascadePile(4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetNumCardsInCascadeTooLowIndex() {
    SimpleFreecellModel model = new SimpleFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 4, 4, false);
    model.getNumCardsInCascadePile(-2);
  }

  @Test(expected = IllegalStateException.class)
  public void testGetNumCardsInCascadeGameNotStarted() {
    SimpleFreecellModel model = new SimpleFreecellModel();
    model.getNumCardsInCascadePile(-2);
  }


  //tests for getNumCardsInOpenPile
  @Test(expected = IllegalArgumentException.class)
  public void testGetNumCardsInOpenTooHighIndex() {
    SimpleFreecellModel model = new SimpleFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 4, 4, false);
    model.getNumCardsInOpenPile(4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetNumCardsInOpenTooLowIndex() {
    SimpleFreecellModel model = new SimpleFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 4, 4, false);
    model.getNumCardsInOpenPile(-2);
  }

  @Test(expected = IllegalStateException.class)
  public void testGetNumCardsInOpenGameNotStarted() {
    SimpleFreecellModel model = new SimpleFreecellModel();
    model.getNumCardsInOpenPile(-2);
  }

  //test for getNumOpenPiles
  @Test
  public void testGetNumOpenPiles() {
    SimpleFreecellModel model = new SimpleFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    assertEquals(-1, model.getNumOpenPiles());
    model.startGame(cards, 4, 4, false);
    assertEquals(4, model.getNumOpenPiles());
  }

  //test for getNumCascadePiles
  @Test
  public void testGetNumCascadePiles() {
    SimpleFreecellModel model = new SimpleFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    assertEquals(-1, model.getNumCascadePiles());
    model.startGame(cards, 4, 4, false);
    assertEquals(4, model.getNumCascadePiles());
  }

  //tests for getFoundationCardAt
  @Test(expected = IllegalStateException.class)
  public void testGetFoundationCardNotStarted() {
    SimpleFreecellModel model = new SimpleFreecellModel();
    model.getFoundationCardAt(0, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetFoundationCardTooLowPile() {
    SimpleFreecellModel model = new SimpleFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 4, 4, false);
    model.getFoundationCardAt(-1, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetFoundationCardTooHighPile() {
    SimpleFreecellModel model = new SimpleFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 4, 4, false);
    model.getFoundationCardAt(5, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetFoundationCardTooLowCard() {
    SimpleFreecellModel model = new SimpleFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 4, 4, false);
    model.move(PileType.CASCADE, 0, 12, PileType.FOUNDATION, 3);
    model.getFoundationCardAt(3, -3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetFoundationCardTooHighCard() {
    SimpleFreecellModel model = new SimpleFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 4, 4, false);
    model.move(PileType.CASCADE, 0, 12, PileType.FOUNDATION, 3);
    model.getFoundationCardAt(3, 2);
  }

  @Test
  public void testGetFoundationCardAt() {
    SimpleFreecellModel model = new SimpleFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 4, 4, false);
    model.move(PileType.CASCADE, 0, 12, PileType.FOUNDATION, 3);
    assertEquals(ACE, model.getFoundationCardAt(3, 0).getRank());
  }

  //tests for getCascadeCardAt
  @Test(expected = IllegalStateException.class)
  public void testGetCascadeCardNotStarted() {
    SimpleFreecellModel model = new SimpleFreecellModel();
    model.getCascadeCardAt(0, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCascadeCardTooLowPile() {
    SimpleFreecellModel model = new SimpleFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 4, 4, false);
    model.getCascadeCardAt(-1, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCascadeCardTooHighPile() {
    SimpleFreecellModel model = new SimpleFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 4, 4, false);
    model.getCascadeCardAt(5, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCascadeCardTooLowCard() {
    SimpleFreecellModel model = new SimpleFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 4, 4, false);
    model.move(PileType.CASCADE, 0, 12, PileType.CASCADE, 3);
    model.getCascadeCardAt(3, -3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCascadeCardTooHighCard() {
    SimpleFreecellModel model = new SimpleFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 4, 4, false);
    model.move(PileType.CASCADE, 0, 12, PileType.CASCADE, 3);
    model.getCascadeCardAt(3, 2);
  }

  @Test
  public void testGetCascadeCardAt() {
    SimpleFreecellModel model = new SimpleFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 4, 4, false);
    assertEquals(ACE, model.getCascadeCardAt(2, 12).getRank());
  }

  //tests for getOpenCardAt
  @Test(expected = IllegalStateException.class)
  public void testGetOpenCardNotStarted() {
    SimpleFreecellModel model = new SimpleFreecellModel();
    model.getOpenCardAt(0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetOpenCardTooLowPile() {
    SimpleFreecellModel model = new SimpleFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 4, 4, false);
    model.getOpenCardAt(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetOpenCardTooHighPile() {
    SimpleFreecellModel model = new SimpleFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 4, 4, false);
    model.getOpenCardAt(6);
  }

  @Test
  public void testGetOpenCardAt() {
    SimpleFreecellModel model = new SimpleFreecellModel();
    ArrayList<Card> cards = model.getDeck();
    model.startGame(cards, 4, 4, false);
    assertEquals(null, model.getOpenCardAt(2));
    model.move(PileType.CASCADE, 3, 12, PileType.OPEN, 2);
    assertEquals(ACE, model.getOpenCardAt(2).getRank());
  }
}
