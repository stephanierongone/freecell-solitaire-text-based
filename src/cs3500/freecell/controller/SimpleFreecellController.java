package cs3500.freecell.controller;

import cs3500.freecell.model.Card;
import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.PileType;
import cs3500.freecell.view.FreecellTextView;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * Class to represent a SimpleFreecellController. Has ability to read user input, write
 * to an appendable, and play through a game of Freecell using a FreecellModel.
 */
public class SimpleFreecellController implements FreecellController<Card> {

  //fields of a SimpleFreecellController
  private final Appendable output;
  private FreecellModel<Card> model;
  private FreecellTextView view;
  private boolean started;
  private boolean quit;
  private Scanner scanner;
  private PileType source;
  private PileType destination;
  private int sourceNum;
  private int destNum;
  private int cardIndex;
  private int commandLength;

  /**
   * Constructs a SimpleFreecellController to be used in a game of freecell.
   * Uses the given freecell model to take user input from the given Readable to play
   * the game of freecell and write to the given Appendable to interact with the user.
   * @param model - FreecellModel to play this game of Freecell.
   * @param rd - Readable item to take user input.
   * @param ap - Appendable item to add to throughout the course of the game as Output.
   */
  public SimpleFreecellController(FreecellModel<Card> model, Readable rd, Appendable ap) {
    //changed from assignment 2, rather than catching a nullpointerexception,
    //checks if any of the given arguments are null and throws an illegalargumentexception.
    if (model == null || rd == null || ap == null) {
      throw new IllegalArgumentException("Arguments cannot be null.");
    }
    //assign attributes
    this.model = model;
    this.scanner = new Scanner(rd);
    this.output = ap;
    this.view = new FreecellTextView(ap, model);
    this.started = false;
    this.quit = false;
    this.source = null;
    this.destination = null;
    this.sourceNum = -1;
    this.cardIndex = -1;
    this.destNum = -1;
    this.commandLength = 0;
  }

  /**
   * Start and play a new game of freecell with the provided deck. This deck
   * should be used as-is. This method returns only when the game is over
   * (either by winning or by quitting)
   *
   * @param deck        the deck to be used to play this game
   * @param numCascades the number of cascade piles
   * @param numOpens    the number of open piles
   * @param shuffle     shuffle the deck if true, false otherwise
   * @throws IllegalStateException if writing to the Appendable object used by it fails
   *                                or reading from the provided Readable fails
   * @throws IllegalArgumentException if the deck provided to it are null
   */
  public void playGame(List<Card> deck, int numCascades, int numOpens, boolean shuffle) {
    //changed from assignment 2, rather than catching a nullpointerexception, checks
    //if the deck is null and throws an illegalargumentexception accordingly.
    if (deck == null) {
      throw new IllegalArgumentException("Deck cannot be null.");
    }

    try {
      try {
        //start game with the model and given parameters
        model.startGame(deck, numCascades, numOpens, shuffle);
        started = true;
        view.renderBoard();
      } catch (IllegalArgumentException e) {
        output.append("Could not start game.");
      }

      if (started) {
        runGame(model);
      }
    }
    //changed from assignment 2: combined catch of IOExceptions
    catch (IOException ioe) {
      throw new IllegalStateException("Input/Output failed.");
    }


    if (!quit && started) {
      try {
        output.append("Game over.");
      } catch (IOException ioe) {
        //nothing to do here
      }
    }
    return;
  }

  /**
   * Runs the game of freecell with this controller using the given model. Continues
   * until the game is complete or quit.
   * @param model - model to be used for this game of freecell.
   * @throws IOException - if input or output fails whilst game is being played.
   */
  private void runGame(FreecellModel<Card> model) throws IOException {
    try {
      while (!model.isGameOver()) {
        if (scanner.hasNext()) {
          String in = scanner.next();

          if (in.charAt(0) == 'q' || in.charAt(0) == 'Q') {
            view.renderMessage("Game quit prematurely.");
            quit = true;
            return;
          }

          //build command to use for next move
          getInstruction(in);

          //tries to make the move
          execute();

        } else {
          throw new IllegalStateException("Readable has run out.");
        }
      }
    }
    catch (IOException ioe) {
      throw new IllegalStateException("Input/Output failed.");
    }
    return;
  }

  /**
   * Uses this Controller's InstructionBuilder to build an instruction using
   * the user input provided to this method.
   * @param in - input taken from the user.
   */
  private void getInstruction(String in) {
    //changed since assignment 2: held instructions within the class
    //and updated them internally.
    try {
      switch (commandLength) {
        case (0):
          if ((isValidPileInput(in.charAt(0)))
                  && isValidIndexInput(in.substring(1))) {
            source = addPile(in.charAt(0));
            sourceNum = Integer.valueOf(in.substring(1)) - 1;
            commandLength += 2;
          } else {
            view.renderMessage("Please re-enter source info.");
          }
          break;
        case (2):
          if (isValidIndexInput(in)) {
            cardIndex = Integer.valueOf(in) - 1;
            commandLength += 1;
          } else {
            view.renderMessage("Please re-enter card index.");
          }
          break;
        case (3):
          if ((isValidPileInput(in.charAt(0)))
                  && isValidIndexInput(in.substring(1))) {
            destination = addPile(in.charAt(0));
            destNum = Integer.valueOf(in.substring(1)) - 1;
            commandLength += 2;
          } else {
            view.renderMessage("Please re-enter destination info.");
          }
          break;
        default:
          break;
      }
    }
    catch (IOException ioe) {
      throw new IllegalStateException("Input/Output failed.");
    }
  }

  private PileType addPile(char pileType) {
    PileType pile = null;
    switch (pileType) {
      case 'C':
        pile = PileType.CASCADE;
        break;
      case 'c':
        pile = PileType.CASCADE;
        break;
      case 'F':
        pile = PileType.FOUNDATION;
        break;
      case 'f':
        pile = PileType.FOUNDATION;
        break;
      case 'O':
        pile = PileType.OPEN;
        break;
      case 'o':
        pile = PileType.OPEN;
        break;
      default:
        break;
    }
    return pile;
  }


  /**
   * Executes the instruction on the model and attempts to move the card.
   * if the model determines that this is an invalid move, lets the user know
   * through the appendable. if a move is successfully made, the instruction and
   * instruction builder are reset in anticipation of the next move.
   */
  private void execute() {
    try {
      if (commandLength == 5) {

        try {
          model.move(source, sourceNum, cardIndex, destination, destNum);
          view.renderBoard();
        } catch (IllegalArgumentException e) {
          view.renderMessage("Invalid input, please try again!");
        }

        this.commandLength = 0;
        this.source = null;
        this.sourceNum = -1;
        this.cardIndex = -1;
        this.destination = null;
        this.destNum = -1;
      }
    }
    catch (IOException ioe) {
      throw new IllegalStateException("Input/Output failed.");
    }

  }

  /**
   * Determines whether the given char is a valid input to designate a pile input type to
   * a cascade, foundation, or open pile type.
   * @param in - character taken from user input to be compared to acceptable values.
   * @return - whether the given character represents a piletype.
   */
  private boolean isValidPileInput(char in) {
    return (in == 'o' || in == 'O' || in == 'f' || in == 'F'
            || in == 'c' || in == 'C');
  }

  /**
   * determines whether the given String is a valid input to designate a card or pile index.
   * for this to pass, the String must parse as an integer. all integers are
   * accepted as values that pass.
   * @param in - String to be taken in as input to be parsed.
   * @return - whether the inputted String is a representation of an integer index.
   */
  private boolean isValidIndexInput(String in) {
    try {
      Integer.parseInt(in);
    } catch (NumberFormatException nfe) {
      return false;
    }
    return true;
  }


}
