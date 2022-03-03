package cs3500.freecell.view;


import org.junit.Test;

import cs3500.freecell.model.Card;
import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.PileType;
import cs3500.freecell.model.SimpleFreecellModel;

import static org.junit.Assert.assertEquals;

/**
 * Class to represent a test on a freecell view.
 */
public class FreecellViewTest {

  @Test
  public void testToStringNotStarted() {
    FreecellModel<Card> model = new SimpleFreecellModel();
    FreecellTextView view = new FreecellTextView(model);
    assertEquals("", view.toString());
  }

  @Test
  public void testToStringBeginningOfGame() {
    String returnString = "F1:\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1:\n" +
            "O2:\n" +
            "O3:\n" +
            "O4:\n" +
            "C1: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
            "C2: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
            "C3: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
            "C4: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥";
    FreecellModel<Card> model = new SimpleFreecellModel();
    FreecellTextView view = new FreecellTextView(model);
    model.startGame(model.getDeck(), 4, 4, false);
    assertEquals(returnString, view.toString());
  }

  @Test
  public void testToStringCardCardsMoved() {
    String returnString = "F1:\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1: A♣\n" +
            "O2:\n" +
            "O3:\n" +
            "O4:\n" +
            "C1: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣\n" +
            "C2: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
            "C3: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
            "C4: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥";
    String returnString2 = "F1:\n" +
            "F2: A♠\n" +
            "F3:\n" +
            "F4:\n" +
            "O1: A♣\n" +
            "O2:\n" +
            "O3:\n" +
            "O4:\n" +
            "C1: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣\n" +
            "C2: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
            "C3: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠\n" +
            "C4: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥";
    FreecellModel<Card> model = new SimpleFreecellModel();
    FreecellTextView view = new FreecellTextView(model);
    model.startGame(model.getDeck(), 4, 4, false);
    model.move(PileType.CASCADE, 0, 12, PileType.OPEN, 0);
    assertEquals(returnString, view.toString());
    model.move(PileType.CASCADE, 2, 12, PileType.FOUNDATION, 1);
    assertEquals(returnString2, view.toString());
  }

}