package cs3500.freecell;

import cs3500.freecell.controller.BadAppendable;
import cs3500.freecell.controller.FreecellController;
import cs3500.freecell.controller.SimpleFreecellController;
import cs3500.freecell.model.Card;
import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.RankType;
import cs3500.freecell.model.SuitType;
import cs3500.freecell.model.SimpleFreecellModel;
import cs3500.freecell.model.multimove.MultiMoveFreecellModel;
import cs3500.freecell.view.FreecellTextView;
import cs3500.freecell.view.FreecellView;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertNotEquals;


/**
 * Class to hold tests for a Simple Freecell Controller.
 */
public class SimpleFreecellControllerTest {

  //tests a bad readable in the controller
  @Test (expected = IllegalStateException.class)
  public void testBadReadable() throws IOException {
    StringReader r = new StringReader("");
    FreecellModel model = new SimpleFreecellModel();
    Appendable ap = new StringBuilder();
    FreecellController ctrl = new SimpleFreecellController(model, r, ap);
    r.close();
    ctrl.playGame(model.getDeck(), 4, 4, false);
  }

  //tests gameOver output when an invalid argument was entered
  //added from assignment 2 from self-eval.
  @Test
  public void testGameOverOutputWithInvalidArgument() throws IOException {
    String s1 = "";
    String s2 = "";
    String s3 = "";
    String s4 = "";

    String invalidArgument = "J1 ";

    for (int i = 13; i > 0; i--) {
      s1 += "C1" + " " + i + " F1 ";
      s1 += "C2" + " " + i + " F2 ";
      s1 += "C3" + " " + i + " F3 ";
      s1 += "C4" + " " + i + " F4 ";
    }

    Appendable ap = new StringBuilder();
    Readable r = new StringReader(invalidArgument + s1 + s2 + s3 + s4 + "Q");
    FreecellModel model = new SimpleFreecellModel();
    FreecellController ctrl = new SimpleFreecellController(model, r, ap);
    ctrl.playGame(model.getDeck(), 4, 4, false);
    assertEquals(true, model.isGameOver());
    assertEquals("Game over.", ap.toString().substring(ap.toString().length() - 10));
  }

  //tests a bad appendable in the controller
  @Test
  public void testIOException() {
    Readable input = new StringReader("asdf q");
    Appendable output = new BadAppendable();
    SimpleFreecellModel model = new SimpleFreecellModel();
    SimpleFreecellController ctrl = new SimpleFreecellController(model, input, output);
    try {
      ctrl.playGame(model.getDeck(), 4, 4, false);
      fail("IOException expected but didn't happen.");
    }
    catch (IllegalStateException ioe) {
      assertEquals("Input/Output failed.", ioe.getMessage());
    }
  }

  //tests gameOver output
  @Test
  public void testGameOverOutput() throws IOException {
    String s1 = "";
    String s2 = "";
    String s3 = "";
    String s4 = "";

    for (int i = 13; i > 0; i--) {
      s1 += "C1" + " " + i + " F1 ";
      s1 += "C2" + " " + i + " F2 ";
      s1 += "C3" + " " + i + " F3 ";
      s1 += "C4" + " " + i + " F4 ";
    }

    Appendable ap = new StringBuilder();
    Readable r = new StringReader(s1 + s2 + s3 + s4 + "Q");
    FreecellModel model = new SimpleFreecellModel();
    FreecellController ctrl = new SimpleFreecellController(model, r, ap);
    ctrl.playGame(model.getDeck(), 4, 4, false);
    assertEquals(true, model.isGameOver());
    assertEquals("Game over.", ap.toString().substring(ap.toString().length() - 10));
  }

  //tests gameOver output
  @Test
  public void testGameOverOutputWithInvalidArgumentMulti() throws IOException {
    String s1 = "";
    String s2 = "";
    String s3 = "";
    String s4 = "";

    String invalidArgument = "J1 ";

    for (int i = 13; i > 0; i--) {
      s1 += "C1" + " " + i + " F1 ";
      s1 += "C2" + " " + i + " F2 ";
      s1 += "C3" + " " + i + " F3 ";
      s1 += "C4" + " " + i + " F4 ";
    }

    Appendable ap = new StringBuilder();
    Readable r = new StringReader(invalidArgument + s1 + s2 + s3 + s4 + "Q");
    FreecellModel model = new MultiMoveFreecellModel();
    FreecellController ctrl = new SimpleFreecellController(model, r, ap);
    ctrl.playGame(model.getDeck(), 4, 4, false);
    assertEquals(true, model.isGameOver());
    assertEquals("Game over.", ap.toString().substring(ap.toString().length() - 10));
  }

  //tests to see if model quits when Q pressed
  @Test
  public void testQuit() throws IOException {
    Reader read = new StringReader("Q");
    SimpleFreecellModel model = new SimpleFreecellModel();
    Appendable ap = new StringBuilder();
    FreecellView view = new FreecellTextView(model);
    FreecellController ctrl = new SimpleFreecellController(model, read, ap);
    ctrl.playGame(model.getDeck(), 4, 4, false);
    assertEquals(view.toString() + "\n" + "Game quit prematurely.\n", ap.toString());
  }

  //tests to see if shuffle works from controller
  @Test
  public void testShuffleFromController() throws IOException {
    Reader r1 = new StringReader("asdf q");
    Reader r2 = new StringReader("asdf q");
    StringBuilder b1 = new StringBuilder();
    StringBuilder b2 = new StringBuilder();
    FreecellModel m1 = new SimpleFreecellModel();
    FreecellModel m2 = new SimpleFreecellModel();
    FreecellController c1 = new SimpleFreecellController(m1, r1, b1);
    FreecellController c2 = new SimpleFreecellController(m2, r2, b2);
    c1.playGame(m1.getDeck(), 4, 4, true);
    c2.playGame(m2.getDeck(), 4, 4, false);
    assertNotEquals(b1.toString(), b2.toString());
  }

  //tests to see if game starts properly when started already
  @Test
  public void testStartGameStartedAlready() {
    String origString = "F1:\n" +
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
            "C4: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n";
    Reader r = new StringReader("q");
    SimpleFreecellModel model = new SimpleFreecellModel();
    Appendable ap = new StringBuilder();
    model.startGame(model.getDeck(), 6, 4, false);
    SimpleFreecellController ctrl = new SimpleFreecellController(model, r, ap);
    ctrl.playGame(model.getDeck(), 4, 4, false);
    assertEquals(origString + "Game quit prematurely.\n", ap.toString());
  }

  //tests to see if the controller throws exception with the null deck
  @Test(expected = IllegalArgumentException.class)
  public void testControllerThrowsWithNullDeck() {
    Reader r = new StringReader("");
    SimpleFreecellModel model = new SimpleFreecellModel();
    SimpleFreecellController ctrl = new SimpleFreecellController(model, r, new StringBuilder());
    ctrl.playGame(null, 4, 4, false);
  }


  //tests to see if exception is thrown with null model
  @Test(expected = IllegalArgumentException.class)
  public void testNullDeck() throws IOException {
    Reader r = new StringReader("");
    SimpleFreecellModel model = new SimpleFreecellModel();
    SimpleFreecellController ctrl = new SimpleFreecellController(model, r, new StringBuilder());
    ctrl.playGame(null, 4, 4, false);
  }

  //tests to see if exception is thrown when it isn't initialized correctly due to null model
  @Test(expected = IllegalArgumentException.class)
  public void testInitializedNullModel() {
    Reader r = new StringReader("");
    Appendable append = new StringBuilder();
    SimpleFreecellController ctrl = new SimpleFreecellController(null, r, append);
  }

  //tests to see if exception is thrown when it isn't initialized correctly due to null appendable
  @Test(expected = IllegalArgumentException.class)
  public void testInitializedNullAppendable() {
    SimpleFreecellModel model = new SimpleFreecellModel();
    SimpleFreecellController ctrl = new SimpleFreecellController(model, new StringReader(""), null);
  }

  //tests to see if exception is thrown when it isn't initialized correctly due to null reader
  @Test(expected = IllegalArgumentException.class)
  public void testInitializedNullReader() {
    SimpleFreecellModel model = new SimpleFreecellModel();
    Appendable append = new StringBuilder();
    SimpleFreecellController ctrl = new SimpleFreecellController(model, null, append);
  }

  //tests to see if exception is thrown when numOpens is too small
  @Test
  public void testNotEnoughOpens() {
    Reader r = new StringReader("asdf q");
    Appendable ap = new StringBuilder();
    SimpleFreecellModel model = new SimpleFreecellModel();
    SimpleFreecellController ctrl = new SimpleFreecellController(model, r, ap);
    ctrl.playGame(model.getDeck(), 4, 0, false);
    assertEquals("Could not start game.", ap.toString());
  }


  //tests ot see if exception is thrown when numCascades is too small
  @Test
  public void testNotEnoughCascades() {
    Reader r = new StringReader("");
    Appendable ap = new StringBuilder();
    SimpleFreecellModel model = new SimpleFreecellModel();
    SimpleFreecellController ctrl = new SimpleFreecellController(model, r, ap);
    ctrl.playGame(model.getDeck(), 0, 2, false);
    assertEquals("Could not start game.", ap.toString());
  }

  //tests to see if exception is thrown when deck is not valid
  @Test
  public void testIncompleteDeck() {
    SimpleFreecellModel model = new SimpleFreecellModel();
    Appendable ap = new StringBuilder();
    List<Card> c = new ArrayList<>();
    Card c1 = new Card(RankType.ACE, SuitType.CLUB);
    Card c2 = new Card(RankType.ACE, SuitType.CLUB);
    c.add(c1);
    c.add(c2);
    Reader r = new StringReader("");
    SimpleFreecellController ctrl = new SimpleFreecellController(model, r, ap);
    ctrl.playGame(c, 3, 2, false);
    assertEquals("Could not start game.", ap.toString());
  }

  //tests the output when the game cannot be started due to not enough opens
  @Test
  public void testCannotStartGameMessageNoOpens() {
    SimpleFreecellModel model = new SimpleFreecellModel();
    Reader r = new StringReader("");
    Appendable ap = new StringBuilder();
    new SimpleFreecellController(model, r, ap).playGame(model.getDeck(), 5, 0, true);
    assertEquals("Could not start game.", ap.toString());
  }

  //tests the output when the game cannot be started due to not enough cascades
  @Test
  public void testCannotStartGameMessageNoCascades() {
    SimpleFreecellModel model = new SimpleFreecellModel();
    Reader r = new StringReader("");
    Appendable ap = new StringBuilder();
    new SimpleFreecellController(model, r, ap).playGame(model.getDeck(), 1, 3, true);
    assertEquals("Could not start game.", ap.toString());
  }

  //tests the output when the game cannot be started due to an empty deck
  @Test
  public void testCannotStartGameMessageNullDeck() {
    SimpleFreecellModel model = new SimpleFreecellModel();
    Reader r = new StringReader("");
    Appendable ap = new StringBuilder();
    new SimpleFreecellController(model, r, ap).playGame(new ArrayList(), 4, 3, true);
    assertEquals("Could not start game.", ap.toString());
  }

  //tests to see if still quits with q is lowercase
  @Test
  public void testLowerCaseQuit() {
    Reader r = new StringReader("q");
    SimpleFreecellModel model = new SimpleFreecellModel();
    FreecellView view = new FreecellTextView(model);
    Appendable ap = new StringBuilder();
    SimpleFreecellController ctrl = new SimpleFreecellController(model, r, ap);
    ctrl.playGame(model.getDeck(), 4, 4, false);
    assertEquals(view.toString() + "\n" + "Game quit prematurely.\n", ap.toString());
  }



  //tests to see if it quits with Q is uppercase
  @Test
  public void testUpperCaseQuit() {
    Reader r = new StringReader("Q");
    SimpleFreecellModel model = new SimpleFreecellModel();
    FreecellView view = new FreecellTextView(model);
    Appendable ap = new StringBuilder();
    SimpleFreecellController ctrl = new SimpleFreecellController(model, r, ap);
    ctrl.playGame(model.getDeck(), 4, 4, false);
    assertEquals(view.toString() + "\n" + "Game quit prematurely.\n", ap.toString());
  }

  //tests to see if it quits when q preceded with space
  @Test
  public void testSpaceBeforeQuit() {
    Reader r = new StringReader(" Q");
    SimpleFreecellModel model = new SimpleFreecellModel();
    FreecellView view = new FreecellTextView(model);
    Appendable ap = new StringBuilder();
    SimpleFreecellController ctrl = new SimpleFreecellController(model, r, ap);
    ctrl.playGame(model.getDeck(), 4, 4, false);
    assertEquals(view.toString() + "\n" + "Game quit prematurely.\n", ap.toString());
  }

  //tests to see if it quits when q is present in the middle of the input
  @Test
  public void testQuitInMiddleOfCommand() {
    Reader r = new StringReader("c1 Q");
    SimpleFreecellModel model = new SimpleFreecellModel();
    FreecellView view = new FreecellTextView(model);
    Appendable ap = new StringBuilder();
    SimpleFreecellController ctrl = new SimpleFreecellController(model, r, ap);
    ctrl.playGame(model.getDeck(), 4, 4, false);
    assertEquals(view.toString() + "\n" + "Game quit prematurely.\n", ap.toString());
  }

  //tests to see if it quits when the q is given after a move has been made already
  @Test
  public void testQuitAfterAMove() {
    String origString = "F1:\n" +
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
            "C4: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n";
    String newString = "F1:\n" +
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
            "C4: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n";
    Reader r = new StringReader("c1 13 o1 q");
    SimpleFreecellModel model = new SimpleFreecellModel();
    FreecellView view = new FreecellTextView(model);
    Appendable ap = new StringBuilder();
    SimpleFreecellController ctrl = new SimpleFreecellController(model, r, ap);
    ctrl.playGame(model.getDeck(), 4, 4, false);
    assertEquals(origString + newString + "Game quit prematurely.\n", ap.toString());
  }

  //tests to see if it quits when a q is put somewhere else in the readable
  @Test
  public void testLowercaseQuitAfterAMove() {
    Reader r = new StringReader("c1 13 q");
    SimpleFreecellModel model = new SimpleFreecellModel();
    FreecellView view = new FreecellTextView(model);
    Appendable ap = new StringBuilder();
    SimpleFreecellController ctrl = new SimpleFreecellController(model, r, ap);
    ctrl.playGame(model.getDeck(), 4, 4, false);
    assertEquals(view.toString() + "\n" + "Game quit prematurely.\n", ap.toString());
  }

  //tests to see if a simple move can be made with lowercase inputs
  @Test
  public void testSimpleMoveLowercase() {
    String origString = "F1:\n" +
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
            "C4: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n";
    String newString = "F1:\n" +
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
            "C4: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n";
    Reader r = new StringReader("c1 13 o1 q");
    SimpleFreecellModel model = new SimpleFreecellModel();
    Appendable ap = new StringBuilder();
    SimpleFreecellController ctrl = new SimpleFreecellController(model, r, ap);
    ctrl.playGame(model.getDeck(), 4, 4, false);
    assertEquals(origString + newString + "Game quit prematurely.\n", ap.toString());
  }

  //tests to see if a simple move can be made with uppercase inputs
  @Test
  public void testSimpleMoveUppercase() {
    String origString = "F1:\n" +
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
            "C4: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n";
    String newString = "F1:\n" +
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
            "C4: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n";
    Reader r = new StringReader("c1 13 o1 Q");
    SimpleFreecellModel model = new SimpleFreecellModel();
    FreecellView view = new FreecellTextView(model);
    Appendable ap = new StringBuilder();
    SimpleFreecellController ctrl = new SimpleFreecellController(model, r, ap);
    ctrl.playGame(model.getDeck(), 4, 4, false);
    assertEquals(origString + newString + "Game quit prematurely.\n", ap.toString());
  }

  //tests to see how the controller handles an invalid move.
  @Test
  public void testInvalidMove() {
    Reader r = new StringReader("C1 11 O1 Q");
    SimpleFreecellModel model = new SimpleFreecellModel();
    FreecellView view = new FreecellTextView(model);
    Appendable ap = new StringBuilder();
    SimpleFreecellController ctrl = new SimpleFreecellController(model, r, ap);
    ctrl.playGame(model.getDeck(), 4, 4, false);
    assertEquals(view.toString() + "\nInvalid input, please try again!" +
                    "\nGame quit prematurely.\n",
            ap.toString());
  }

  //tests to see if the controller can handle an invalid move being made, then a valid move.
  @Test
  public void testInvalidThenInvalidMove() {
    String newString = "F1:\n" +
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
    String origString = "F1:\n" +
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
    Reader r = new StringReader("C1 11 O1 C1 13 O1 Q");
    SimpleFreecellModel model = new SimpleFreecellModel();
    FreecellView view = new FreecellTextView(model);
    Appendable ap = new StringBuilder();
    SimpleFreecellController ctrl = new SimpleFreecellController(model, r, ap);
    ctrl.playGame(model.getDeck(), 4, 4, false);
    assertEquals(origString + "\nInvalid input, please try again!\n" + newString
            + "\nGame quit prematurely.\n", ap.toString());
  }

  //test to see if controller is able to handle two moves at once.
  @Test
  public void testTwoMovesSimultaneously() {
    String newString = "F1:\n" +
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
            "C4: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n";
    String newString2 = "F1:\n" +
            "F2:\n" +
            "F3: A♠\n" +
            "F4:\n" +
            "O1: A♣\n" +
            "O2:\n" +
            "O3:\n" +
            "O4:\n" +
            "C1: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣\n" +
            "C2: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
            "C3: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠\n" +
            "C4: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n";
    String origString = "F1:\n" +
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
            "C4: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n";
    Reader r = new StringReader("C1 13 O1 C3 13 F3 Q");
    SimpleFreecellModel model = new SimpleFreecellModel();
    FreecellView view = new FreecellTextView(model);
    Appendable ap = new StringBuilder();
    SimpleFreecellController ctrl = new SimpleFreecellController(model, r, ap);
    ctrl.playGame(model.getDeck(), 4, 4, false);
    assertEquals(origString + newString + newString2 + "Game quit prematurely.\n", ap.toString());
  }

  //test an input that initially throws an error and then accepts the input
  @Test
  public void testFixedInput() {
    String origString = "F1:\n" +
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
            "C4: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n";
    String newString = "F1:\n" +
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
            "C4: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n";
    Reader r = new StringReader("C1 1a 13 O1 Q");
    SimpleFreecellModel model = new SimpleFreecellModel();
    FreecellView view = new FreecellTextView(model);
    Appendable ap = new StringBuilder();
    SimpleFreecellController ctrl = new SimpleFreecellController(model, r, ap);
    ctrl.playGame(model.getDeck(), 4, 4, false);
    assertEquals(origString + "Please re-enter card index.\n" + newString
                    + "Game quit prematurely.\n", ap.toString());
  }

  //tests invalid source pile input because of invalid piletype character
  @Test
  public void testInvalidSourceTypeInput() {
    String origString = "F1:\n" +
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
            "C4: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n";
    Reader r = new StringReader("j1 Q");
    SimpleFreecellModel model = new SimpleFreecellModel();
    FreecellView view = new FreecellTextView(model);
    Appendable ap = new StringBuilder();
    SimpleFreecellController ctrl = new SimpleFreecellController(model, r, ap);
    ctrl.playGame(model.getDeck(), 4, 4, false);
    assertEquals(origString + "Please re-enter source info.\nGame quit prematurely.\n",
            ap.toString());
  }

  //tests invalid source pile input due to invalid number input.
  @Test
  public void testInvalidSourceIndexInput() {
    String origString = "F1:\n" +
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
            "C4: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n";
    Reader r = new StringReader("CO Q");
    SimpleFreecellModel model = new SimpleFreecellModel();
    FreecellView view = new FreecellTextView(model);
    Appendable ap = new StringBuilder();
    SimpleFreecellController ctrl = new SimpleFreecellController(model, r, ap);
    ctrl.playGame(model.getDeck(), 4, 4, false);
    assertEquals(origString + "Please re-enter source info.\nGame quit prematurely.\n",
            ap.toString());
  }

  @Test
  public void testInvalidCardIndexInput() {
    String origString = "F1:\n" +
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
            "C4: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n";
    Reader r = new StringReader("C1 ASDF Q");
    SimpleFreecellModel model = new SimpleFreecellModel();
    Appendable ap = new StringBuilder();
    SimpleFreecellController ctrl = new SimpleFreecellController(model, r, ap);
    ctrl.playGame(model.getDeck(), 4, 4, false);
    assertEquals(origString + "Please re-enter card index.\nGame quit prematurely.\n",
            ap.toString());
  }

  //test invalid destination pile input due to invalid pile type
  @Test
  public void testInvalidDestinationTypeInput() {
    String origString = "F1:\n" +
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
            "C4: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n";
    Reader r = new StringReader("C1 2 J3 Q");
    SimpleFreecellModel model = new SimpleFreecellModel();
    Appendable ap = new StringBuilder();
    SimpleFreecellController ctrl = new SimpleFreecellController(model, r, ap);
    ctrl.playGame(model.getDeck(), 4, 4, false);
    assertEquals(origString + "Please re-enter destination info.\nGame quit prematurely.\n",
            ap.toString());
  }

  //test invalid destination index input
  @Test
  public void testInvalidDestinationIndexInput() {
    String origString = "F1:\n" +
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
            "C4: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n";
    Reader r = new StringReader("C1 2 OP Q");
    SimpleFreecellModel model = new SimpleFreecellModel();
    Appendable ap = new StringBuilder();
    SimpleFreecellController ctrl = new SimpleFreecellController(model, r, ap);
    ctrl.playGame(model.getDeck(), 4, 4, false);
    assertEquals(origString + "Please re-enter destination info.\nGame quit prematurely.\n",
            ap.toString());
  }

  //test newline commands
  @Test
  public void testNewlineInput() {
    String origString = "F1:\n" +
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
            "C4: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n";
    String newString = "F1:\n" +
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
            "C4: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n";
    Reader r = new StringReader("C1\n" +
            "13\n" +
            "O1\n" +
            "Q");
    SimpleFreecellModel model = new SimpleFreecellModel();
    Appendable ap = new StringBuilder();
    SimpleFreecellController ctrl = new SimpleFreecellController(model, r, ap);
    ctrl.playGame(model.getDeck(), 4, 4, false);
    assertEquals(origString + newString + "Game quit prematurely.\n", ap.toString());
  }

  //test for a valid input that fails inside the model's move method
  @Test
  public void validInputInvalidMove() {
    String origString = "F1:\n" +
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
            "C4: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n";
    Reader r = new StringReader("C1 3 O1 q");
    SimpleFreecellModel model = new SimpleFreecellModel();
    Appendable ap = new StringBuilder();
    SimpleFreecellController ctrl = new SimpleFreecellController(model, r, ap);
    ctrl.playGame(model.getDeck(), 4, 4, false);
    assertEquals(origString + "Invalid input, please try again!\nGame quit prematurely.\n",
            ap.toString());
  }

  /**
   * added tests for multimove.
   */


  //tests a bad appendable in the controller
  @Test
  public void testIOExceptionMulti() {
    Readable input = new StringReader("asdf q");
    Appendable output = new BadAppendable();
    MultiMoveFreecellModel model = new MultiMoveFreecellModel();
    SimpleFreecellController ctrl = new SimpleFreecellController(model, input, output);
    try {
      ctrl.playGame(model.getDeck(), 4, 4, false);
      fail("IOException expected but didn't happen.");
    }
    catch (IllegalStateException ioe) {
      assertEquals("Input/Output failed.", ioe.getMessage());
    }
  }

  //tests gameOver output
  @Test
  public void testGameOverOutputMulti() throws IOException {
    String s1 = "";
    String s2 = "";
    String s3 = "";
    String s4 = "";

    for (int i = 13; i > 0; i--) {
      s1 += "C1" + " " + i + " F1 ";
      s1 += "C2" + " " + i + " F2 ";
      s1 += "C3" + " " + i + " F3 ";
      s1 += "C4" + " " + i + " F4 ";
    }

    Appendable ap = new StringBuilder();
    Readable r = new StringReader(s1 + s2 + s3 + s4 + "Q");
    FreecellModel model = new MultiMoveFreecellModel();
    FreecellController ctrl = new SimpleFreecellController(model, r, ap);
    ctrl.playGame(model.getDeck(), 4, 4, false);
    assertEquals(true, model.isGameOver());
    assertEquals("Game over.", ap.toString().substring(ap.toString().length() - 10));
  }

  //tests to see if model quits when Q pressed
  @Test
  public void testQuitMulti() throws IOException {
    Reader read = new StringReader("Q");
    SimpleFreecellModel model = new SimpleFreecellModel();
    Appendable ap = new StringBuilder();
    FreecellView view = new FreecellTextView(model);
    FreecellController ctrl = new SimpleFreecellController(model, read, ap);
    ctrl.playGame(model.getDeck(), 4, 4, false);
    assertEquals(view.toString() + "\n" + "Game quit prematurely.\n", ap.toString());
  }

  //tests to see if shuffle works from controller
  @Test
  public void testShuffleFromControllerMulti() throws IOException {
    Reader r1 = new StringReader("asdf q");
    Reader r2 = new StringReader("asdf q");
    StringBuilder b1 = new StringBuilder();
    StringBuilder b2 = new StringBuilder();
    FreecellModel m1 = new MultiMoveFreecellModel();
    FreecellModel m2 = new MultiMoveFreecellModel();
    FreecellController c1 = new SimpleFreecellController(m1, r1, b1);
    FreecellController c2 = new SimpleFreecellController(m2, r2, b2);
    c1.playGame(m1.getDeck(), 4, 4, true);
    c2.playGame(m2.getDeck(), 4, 4, false);
    assertNotEquals(b1.toString(), b2.toString());
  }

  //tests to see if game starts properly when started already
  @Test
  public void testStartGameStartedAlreadyMulti() {
    String origString = "F1:\n" +
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
            "C4: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n";
    Reader r = new StringReader("q");
    FreecellModel model = new MultiMoveFreecellModel();
    Appendable ap = new StringBuilder();
    model.startGame(model.getDeck(), 6, 4, false);
    SimpleFreecellController ctrl = new SimpleFreecellController(model, r, ap);
    ctrl.playGame(model.getDeck(), 4, 4, false);
    assertEquals(origString + "Game quit prematurely.\n", ap.toString());
  }

  //tests to see if the controller throws exception with the null deck
  @Test(expected = IllegalArgumentException.class)
  public void testControllerThrowsWithNullDeckMulti() {
    Reader r = new StringReader("");
    FreecellModel model = new MultiMoveFreecellModel();
    SimpleFreecellController ctrl = new SimpleFreecellController(model, r, new StringBuilder());
    ctrl.playGame(null, 4, 4, false);
  }


  //tests to see if exception is thrown with null model
  @Test(expected = IllegalArgumentException.class)
  public void testNullDeckMulti() throws IOException {
    Reader r = new StringReader("");
    FreecellModel model = new MultiMoveFreecellModel();
    SimpleFreecellController ctrl = new SimpleFreecellController(model, r, new StringBuilder());
    ctrl.playGame(null, 4, 4, false);
  }

  //tests to see if exception is thrown when it isn't initialized correctly due to null model
  @Test(expected = IllegalArgumentException.class)
  public void testInitializedNullModelMulti() {
    Reader r = new StringReader("");
    Appendable append = new StringBuilder();
    SimpleFreecellController ctrl = new SimpleFreecellController(null, r, append);
  }

  //tests to see if exception is thrown when it isn't initialized correctly due to null appendable
  @Test(expected = IllegalArgumentException.class)
  public void testInitializedNullAppendableMulti() {
    FreecellModel model = new MultiMoveFreecellModel();
    SimpleFreecellController ctrl = new SimpleFreecellController(model, new StringReader(""), null);
  }

  //tests to see if exception is thrown when it isn't initialized correctly due to null reader
  @Test(expected = IllegalArgumentException.class)
  public void testInitializedNullReaderMulti() {
    FreecellModel model = new MultiMoveFreecellModel();
    Appendable append = new StringBuilder();
    SimpleFreecellController ctrl = new SimpleFreecellController(model, null, append);
  }

  //tests to see if exception is thrown when numOpens is too small
  @Test
  public void testNotEnoughOpensMulti() {
    Reader r = new StringReader("asdf q");
    Appendable ap = new StringBuilder();
    FreecellModel model = new MultiMoveFreecellModel();
    SimpleFreecellController ctrl = new SimpleFreecellController(model, r, ap);
    ctrl.playGame(model.getDeck(), 4, 0, false);
    assertEquals("Could not start game.", ap.toString());
  }


  //tests ot see if exception is thrown when numCascades is too small
  @Test
  public void testNotEnoughCascadesMulti() {
    Reader r = new StringReader("");
    Appendable ap = new StringBuilder();
    FreecellModel model = new MultiMoveFreecellModel();
    SimpleFreecellController ctrl = new SimpleFreecellController(model, r, ap);
    ctrl.playGame(model.getDeck(), 0, 2, false);
    assertEquals("Could not start game.", ap.toString());
  }

  //tests to see if exception is thrown when deck is not valid
  @Test
  public void testIncompleteDeckMulti() {
    FreecellModel model = new MultiMoveFreecellModel();
    Appendable ap = new StringBuilder();
    List<Card> c = new ArrayList<>();
    Card c1 = new Card(RankType.ACE, SuitType.CLUB);
    Card c2 = new Card(RankType.ACE, SuitType.CLUB);
    c.add(c1);
    c.add(c2);
    Reader r = new StringReader("");
    SimpleFreecellController ctrl = new SimpleFreecellController(model, r, ap);
    ctrl.playGame(c, 3, 2, false);
    assertEquals("Could not start game.", ap.toString());
  }

  //tests the output when the game cannot be started due to not enough opens
  @Test
  public void testCannotStartGameMessageNoOpensMulti() {
    FreecellModel model = new MultiMoveFreecellModel();
    Reader r = new StringReader("");
    Appendable ap = new StringBuilder();
    new SimpleFreecellController(model, r, ap).playGame(model.getDeck(), 5, 0, true);
    assertEquals("Could not start game.", ap.toString());
  }

  //tests the output when the game cannot be started due to not enough cascades
  @Test
  public void testCannotStartGameMessageNoCascadesMulti() {
    FreecellModel model = new MultiMoveFreecellModel();
    Reader r = new StringReader("");
    Appendable ap = new StringBuilder();
    new SimpleFreecellController(model, r, ap).playGame(model.getDeck(), 1, 3, true);
    assertEquals("Could not start game.", ap.toString());
  }

  //tests the output when the game cannot be started due to an empty deck
  @Test
  public void testCannotStartGameMessageNullDeckMulti() {
    FreecellModel model = new MultiMoveFreecellModel();
    Reader r = new StringReader("");
    Appendable ap = new StringBuilder();
    new SimpleFreecellController(model, r, ap).playGame(new ArrayList(), 4, 3, true);
    assertEquals("Could not start game.", ap.toString());
  }

  //tests to see if still quits with q is lowercase
  @Test
  public void testLowerCaseQuitMulti() {
    Reader r = new StringReader("q");
    FreecellModel model = new MultiMoveFreecellModel();
    FreecellView view = new FreecellTextView(model);
    Appendable ap = new StringBuilder();
    SimpleFreecellController ctrl = new SimpleFreecellController(model, r, ap);
    ctrl.playGame(model.getDeck(), 4, 4, false);
    assertEquals(view.toString() + "\n" + "Game quit prematurely.\n", ap.toString());
  }



  //tests to see if it quits with Q is uppercase
  @Test
  public void testUpperCaseQuitMulti() {
    Reader r = new StringReader("Q");
    FreecellModel model = new MultiMoveFreecellModel();
    FreecellView view = new FreecellTextView(model);
    Appendable ap = new StringBuilder();
    SimpleFreecellController ctrl = new SimpleFreecellController(model, r, ap);
    ctrl.playGame(model.getDeck(), 4, 4, false);
    assertEquals(view.toString() + "\n" + "Game quit prematurely.\n", ap.toString());
  }

  //tests to see if it quits when q preceded with space
  @Test
  public void testSpaceBeforeQuitMulti() {
    Reader r = new StringReader(" Q");
    FreecellModel model = new MultiMoveFreecellModel();
    FreecellView view = new FreecellTextView(model);
    Appendable ap = new StringBuilder();
    SimpleFreecellController ctrl = new SimpleFreecellController(model, r, ap);
    ctrl.playGame(model.getDeck(), 4, 4, false);
    assertEquals(view.toString() + "\n" + "Game quit prematurely.\n", ap.toString());
  }

  //tests to see if it quits when q is present in the middle of the input
  @Test
  public void testQuitInMiddleOfCommandMulti() {
    Reader r = new StringReader("c1 Q");
    FreecellModel model = new MultiMoveFreecellModel();
    FreecellView view = new FreecellTextView(model);
    Appendable ap = new StringBuilder();
    SimpleFreecellController ctrl = new SimpleFreecellController(model, r, ap);
    ctrl.playGame(model.getDeck(), 4, 4, false);
    assertEquals(view.toString() + "\n" + "Game quit prematurely.\n", ap.toString());
  }

  //tests to see if it quits when the q is given after a move has been made already
  @Test
  public void testQuitAfterAMoveMulti() {
    String origString = "F1:\n" +
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
            "C4: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n";
    String newString = "F1:\n" +
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
            "C4: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n";
    Reader r = new StringReader("c1 13 o1 q");
    FreecellModel model = new MultiMoveFreecellModel();
    FreecellView view = new FreecellTextView(model);
    Appendable ap = new StringBuilder();
    SimpleFreecellController ctrl = new SimpleFreecellController(model, r, ap);
    ctrl.playGame(model.getDeck(), 4, 4, false);
    assertEquals(origString + newString + "Game quit prematurely.\n", ap.toString());
  }

  //tests to see if it quits when a q is put somewhere else in the readable
  @Test
  public void testLowercaseQuitAfterAMoveMulti() {
    Reader r = new StringReader("c1 13 q");
    FreecellModel model = new MultiMoveFreecellModel();
    FreecellView view = new FreecellTextView(model);
    Appendable ap = new StringBuilder();
    SimpleFreecellController ctrl = new SimpleFreecellController(model, r, ap);
    ctrl.playGame(model.getDeck(), 4, 4, false);
    assertEquals(view.toString() + "\n" + "Game quit prematurely.\n", ap.toString());
  }

  //tests to see if a simple move can be made with lowercase inputs
  @Test
  public void testSimpleMoveLowercaseMulti() {
    String origString = "F1:\n" +
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
            "C4: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n";
    String newString = "F1:\n" +
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
            "C4: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n";
    Reader r = new StringReader("c1 13 o1 q");
    FreecellModel model = new MultiMoveFreecellModel();
    Appendable ap = new StringBuilder();
    SimpleFreecellController ctrl = new SimpleFreecellController(model, r, ap);
    ctrl.playGame(model.getDeck(), 4, 4, false);
    assertEquals(origString + newString + "Game quit prematurely.\n", ap.toString());
  }

  //tests to see if a simple move can be made with uppercase inputs
  @Test
  public void testSimpleMoveUppercaseMulti() {
    String origString = "F1:\n" +
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
            "C4: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n";
    String newString = "F1:\n" +
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
            "C4: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n";
    Reader r = new StringReader("c1 13 o1 Q");
    FreecellModel model = new MultiMoveFreecellModel();
    FreecellView view = new FreecellTextView(model);
    Appendable ap = new StringBuilder();
    SimpleFreecellController ctrl = new SimpleFreecellController(model, r, ap);
    ctrl.playGame(model.getDeck(), 4, 4, false);
    assertEquals(origString + newString + "Game quit prematurely.\n", ap.toString());
  }

  //tests to see how the controller handles an invalid move.
  @Test
  public void testInvalidMoveMulti() {
    Reader r = new StringReader("C1 11 O1 Q");
    FreecellModel model = new MultiMoveFreecellModel();
    FreecellView view = new FreecellTextView(model);
    Appendable ap = new StringBuilder();
    SimpleFreecellController ctrl = new SimpleFreecellController(model, r, ap);
    ctrl.playGame(model.getDeck(), 4, 4, false);
    assertEquals(view.toString() + "\nInvalid input, please try again!" +
                    "\nGame quit prematurely.\n",
            ap.toString());
  }


  //tests to see if the controller can handle an invalid move being made, then a valid move.
  @Test
  public void testInvalidThenInvalidMoveMulti() {
    String newString = "F1:\n" +
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
    String origString = "F1:\n" +
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
    Reader r = new StringReader("C1 11 O1 C1 13 O1 Q");
    FreecellModel model = new MultiMoveFreecellModel();
    FreecellView view = new FreecellTextView(model);
    Appendable ap = new StringBuilder();
    SimpleFreecellController ctrl = new SimpleFreecellController(model, r, ap);
    ctrl.playGame(model.getDeck(), 4, 4, false);
    assertEquals(origString + "\nInvalid input, please try again!\n" + newString
            + "\nGame quit prematurely.\n", ap.toString());
  }

  //test to see if controller is able to handle two moves at once.
  @Test
  public void testTwoMovesSimultaneouslyMulti() {
    String newString = "F1:\n" +
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
            "C4: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n";
    String newString2 = "F1:\n" +
            "F2:\n" +
            "F3: A♠\n" +
            "F4:\n" +
            "O1: A♣\n" +
            "O2:\n" +
            "O3:\n" +
            "O4:\n" +
            "C1: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣\n" +
            "C2: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
            "C3: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠\n" +
            "C4: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n";
    String origString = "F1:\n" +
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
            "C4: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n";
    Reader r = new StringReader("C1 13 O1 C3 13 F3 Q");
    FreecellModel model = new MultiMoveFreecellModel();
    FreecellView view = new FreecellTextView(model);
    Appendable ap = new StringBuilder();
    SimpleFreecellController ctrl = new SimpleFreecellController(model, r, ap);
    ctrl.playGame(model.getDeck(), 4, 4, false);
    assertEquals(origString + newString + newString2 + "Game quit prematurely.\n", ap.toString());
  }

  //test an input that initially throws an error and then accepts the input
  @Test
  public void testFixedInputMulti() {
    String origString = "F1:\n" +
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
            "C4: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n";
    String newString = "F1:\n" +
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
            "C4: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n";
    Reader r = new StringReader("C1 1a 13 O1 Q");
    FreecellModel model = new MultiMoveFreecellModel();
    FreecellView view = new FreecellTextView(model);
    Appendable ap = new StringBuilder();
    SimpleFreecellController ctrl = new SimpleFreecellController(model, r, ap);
    ctrl.playGame(model.getDeck(), 4, 4, false);
    assertEquals(origString + "Please re-enter card index.\n" + newString
            + "Game quit prematurely.\n", ap.toString());
  }

  //tests invalid source pile input because of invalid piletype character
  @Test
  public void testInvalidSourceTypeInputMulti() {
    String origString = "F1:\n" +
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
            "C4: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n";
    Reader r = new StringReader("j1 Q");
    FreecellModel model = new MultiMoveFreecellModel();
    FreecellView view = new FreecellTextView(model);
    Appendable ap = new StringBuilder();
    SimpleFreecellController ctrl = new SimpleFreecellController(model, r, ap);
    ctrl.playGame(model.getDeck(), 4, 4, false);
    assertEquals(origString + "Please re-enter source info.\nGame quit prematurely.\n",
            ap.toString());
  }

  //tests invalid source pile input due to invalid number input.
  @Test
  public void testInvalidSourceIndexInputMulti() {
    String origString = "F1:\n" +
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
            "C4: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n";
    Reader r = new StringReader("CO Q");
    FreecellModel model = new MultiMoveFreecellModel();
    FreecellView view = new FreecellTextView(model);
    Appendable ap = new StringBuilder();
    SimpleFreecellController ctrl = new SimpleFreecellController(model, r, ap);
    ctrl.playGame(model.getDeck(), 4, 4, false);
    assertEquals(origString + "Please re-enter source info.\nGame quit prematurely.\n",
            ap.toString());
  }

  @Test
  public void testInvalidCardIndexInputMulti() {
    String origString = "F1:\n" +
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
            "C4: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n";
    Reader r = new StringReader("C1 ASDF Q");
    FreecellModel model = new MultiMoveFreecellModel();
    Appendable ap = new StringBuilder();
    SimpleFreecellController ctrl = new SimpleFreecellController(model, r, ap);
    ctrl.playGame(model.getDeck(), 4, 4, false);
    assertEquals(origString + "Please re-enter card index.\nGame quit prematurely.\n",
            ap.toString());
  }

  //test invalid destination pile input due to invalid pile type
  @Test
  public void testInvalidDestinationTypeInputMulti() {
    String origString = "F1:\n" +
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
            "C4: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n";
    Reader r = new StringReader("C1 2 J3 Q");
    FreecellModel model = new MultiMoveFreecellModel();
    Appendable ap = new StringBuilder();
    SimpleFreecellController ctrl = new SimpleFreecellController(model, r, ap);
    ctrl.playGame(model.getDeck(), 4, 4, false);
    assertEquals(origString + "Please re-enter destination info.\nGame quit prematurely.\n",
            ap.toString());
  }

  //test invalid destination index input
  @Test
  public void testInvalidDestinationIndexInputMulti() {
    String origString = "F1:\n" +
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
            "C4: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n";
    Reader r = new StringReader("C1 2 OP Q");
    FreecellModel model = new MultiMoveFreecellModel();
    Appendable ap = new StringBuilder();
    SimpleFreecellController ctrl = new SimpleFreecellController(model, r, ap);
    ctrl.playGame(model.getDeck(), 4, 4, false);
    assertEquals(origString + "Please re-enter destination info.\nGame quit prematurely.\n",
            ap.toString());
  }

  //test newline commands
  @Test
  public void testNewlineInputMulti() {
    String origString = "F1:\n" +
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
            "C4: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n";
    String newString = "F1:\n" +
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
            "C4: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n";
    Reader r = new StringReader("C1\n" +
            "13\n" +
            "O1\n" +
            "Q");
    FreecellModel model = new MultiMoveFreecellModel();
    Appendable ap = new StringBuilder();
    SimpleFreecellController ctrl = new SimpleFreecellController(model, r, ap);
    ctrl.playGame(model.getDeck(), 4, 4, false);
    assertEquals(origString + newString + "Game quit prematurely.\n", ap.toString());
  }

  //test for a valid input that fails inside the model's move method
  @Test
  public void validInputInvalidMoveMulti() {
    String origString = "F1:\n" +
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
            "C4: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n";
    Reader r = new StringReader("C1 3 O1 q");
    FreecellModel model = new MultiMoveFreecellModel();
    Appendable ap = new StringBuilder();
    SimpleFreecellController ctrl = new SimpleFreecellController(model, r, ap);
    ctrl.playGame(model.getDeck(), 4, 4, false);
    assertEquals(origString + "Invalid input, please try again!\nGame quit prematurely.\n",
            ap.toString());
  }


  //test that it can perform a multimove
  @Test
  public void testControllerPerformsMultiMove() {
    String returnString = "F1:\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1: A♦\n" +
            "O2: 2♣\n" +
            "O3:\n" +
            "O4:\n" +
            "C1: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♦, A♣\n" +
            "C2: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦\n" +
            "C3: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
            "C4: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n" +
            "Game quit prematurely.\n";
    Reader r = new StringReader("C2 13 O1 C1 13 C2 C1 12 O2 C2 12 C1 q");
    FreecellModel model = new MultiMoveFreecellModel();
    Appendable ap = new StringBuilder();
    SimpleFreecellController ctrl = new SimpleFreecellController(model, r, ap);
    ctrl.playGame(model.getDeck(), 4, 4, false);
    assertEquals(returnString, ap.toString().substring(ap.toString().length() - 277));
  }

  //test that it will throw an error if an incorrect cascade-cascade move is performed (build)
  @Test
  public void testMultiControllerBadBuild() {
    String origString = "F1:\n" +
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
            "C4: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n";
    Reader r = new StringReader("C2 12 O2 q");
    FreecellModel model = new MultiMoveFreecellModel();
    Appendable ap = new StringBuilder();
    SimpleFreecellController ctrl = new SimpleFreecellController(model, r, ap);
    ctrl.playGame(model.getDeck(), 4, 4, false);
    assertEquals(origString
            + "Invalid input, please try again!\nGame quit prematurely.\n", ap.toString());
  }
}