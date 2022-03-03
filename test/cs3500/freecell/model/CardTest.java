package cs3500.freecell.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * Class to hold tests on the Card class.
 */
public class CardTest {

  //tests for toString() method of cards.
  @Test
  public void testToString() {
    Card c1 = new Card(RankType.ACE, SuitType.DIAMOND);
    Card c2 = new Card(RankType.TWO, SuitType.HEART);
    Card c3 = new Card(RankType.JACK, SuitType.CLUB);
    Card c4 = new Card(RankType.FOUR, SuitType.SPADE);
    assertEquals("A♦", c1.toString());
    assertEquals("2♥", c2.toString());
    assertEquals("J♣", c3.toString());
    assertEquals("4♠", c4.toString());
  }

  //tests for getRank, getSuit, getColor
  @Test
  public void testGetRank() {
    Card c1 = new Card(RankType.ACE, SuitType.DIAMOND);
    Card c2 = new Card(RankType.TWO, SuitType.HEART);
    Card c3 = new Card(RankType.JACK, SuitType.CLUB);
    Card c4 = new Card(RankType.FOUR, SuitType.SPADE);
    assertEquals(RankType.ACE, c1.getRank());
    assertEquals(RankType.TWO, c2.getRank());
    assertEquals(RankType.JACK, c3.getRank());
    assertEquals(RankType.FOUR, c4.getRank());
  }

  @Test
  public void testGetSuit() {
    Card c1 = new Card(RankType.ACE, SuitType.DIAMOND);
    Card c2 = new Card(RankType.TWO, SuitType.HEART);
    Card c3 = new Card(RankType.JACK, SuitType.CLUB);
    Card c4 = new Card(RankType.FOUR, SuitType.SPADE);
    assertEquals(SuitType.DIAMOND, c1.getSuit());
    assertEquals(SuitType.HEART, c2.getSuit());
    assertEquals(SuitType.CLUB, c3.getSuit());
    assertEquals(SuitType.SPADE, c4.getSuit());
  }

  @Test
  public void testGetColor() {
    Card c1 = new Card(RankType.ACE, SuitType.DIAMOND);
    Card c2 = new Card(RankType.TWO, SuitType.HEART);
    Card c3 = new Card(RankType.JACK, SuitType.CLUB);
    Card c4 = new Card(RankType.FOUR, SuitType.SPADE);
    assertEquals(1, c1.getColor());
    assertEquals(1, c2.getColor());
    assertEquals(0, c3.getColor());
    assertEquals(0, c4.getColor());
  }

  @Test
  public void testEquals() {
    Card c1 = new Card(RankType.ACE, SuitType.DIAMOND);
    Card c2 = new Card(RankType.TWO, SuitType.HEART);
    Card c3 = new Card(RankType.JACK, SuitType.CLUB);
    Card c4 = new Card(RankType.ACE, SuitType.SPADE);
    Card c5 = new Card(RankType.ACE, SuitType.DIAMOND);
    Card c6 = new Card(RankType.THREE, SuitType.HEART);
    Card c7 = new Card(RankType.JACK, SuitType.CLUB);
    Card c8 = new Card(RankType.FOUR, SuitType.SPADE);
    assertFalse(c2.equals(c5));
    assertFalse(c3.equals(c8));
    assertFalse(c2.equals(c6));
    assertFalse(c2.equals(3));
    assertFalse(c4.equals(c1));
    assertTrue(c3.equals(c7));
  }

  @Test
  public void testHash() {
    Card c1 = new Card(RankType.ACE, SuitType.DIAMOND);
    Card c2 = new Card(RankType.TWO, SuitType.HEART);
    Card c3 = new Card(RankType.JACK, SuitType.CLUB);
    Card c4 = new Card(RankType.ACE, SuitType.SPADE);
    assertEquals(0, c1.hashCode());
    assertEquals(1, c2.hashCode());
    assertEquals(10, c3.hashCode());
    assertEquals(0, c4.hashCode());
  }
}