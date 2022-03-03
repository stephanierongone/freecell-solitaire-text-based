package cs3500.freecell;

import cs3500.freecell.model.FreecellModelCreator;
import cs3500.freecell.model.SimpleFreecellModel;
import cs3500.freecell.model.multimove.MultiMoveFreecellModel;
import org.junit.Test;

import static org.junit.Assert.assertTrue;


/**
 * Class for testing the Freecell model creator, which can create either a
 * SimpleFreecellModel or a MultiMoveFreecellModel.
 */
public class FreecellModelCreatorTest {


  //tests if the model produced by a creator with a MULTIMOVE gametype input
  //will return a multimove freecell model.
  @Test
  public void testCreatorOfMultiMove() {
    FreecellModelCreator model = new FreecellModelCreator();
    assertTrue(model.create(FreecellModelCreator.GameType.MULTIMOVE)
            instanceof MultiMoveFreecellModel);
  }

  //tests if the model produced by a creator with a SIMPLE gametype input
  //will return a simple freecell model.
  @Test
  public void testCreatorOfSimpleFreecell() {
    FreecellModelCreator model = new FreecellModelCreator();
    assertTrue(model.create(FreecellModelCreator.GameType.SINGLEMOVE)
            instanceof SimpleFreecellModel);
  }

  //tests if the creator will throw an illegalargumentexception if a null argument is put into
  //the create method.
  @Test(expected = IllegalArgumentException.class)
  public void testCreatorWithNullArgument() {
    FreecellModelCreator model = new FreecellModelCreator();
    model.create(null);
  }


}