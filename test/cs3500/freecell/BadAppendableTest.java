package cs3500.freecell;

import cs3500.freecell.controller.BadAppendable;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;


/**
 * class to hold tests for the BadAppendable class.
 */
public class BadAppendableTest {

  //test for the first append method.
  @Test
  public void testAppend1() {
    Appendable bad = new BadAppendable();
    try {
      bad.append("asdf");
    } catch (IOException e) {
      assertEquals("Fail!", e.getMessage());
    }
  }

  //test for the second append method.
  @Test
  public void testAppend2() {
    Appendable bad = new BadAppendable();
    try {
      bad.append("asdf", 1, 4);
    } catch (IOException e) {
      assertEquals("Fail!", e.getMessage());
    }
  }

  //tests for the third append method.
  @Test
  public void testAppend3() {
    Appendable bad = new BadAppendable();
    try {
      bad.append('a');
    } catch (IOException e) {
      assertEquals("Fail!", e.getMessage());
    }
  }
}