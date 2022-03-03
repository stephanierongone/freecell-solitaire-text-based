package cs3500.freecell.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * Tests for the SuitType enum.
 */
public class SuitTypeTest {

  //test for return string
  @Test
  public void testReturnString() {
    SuitType s1 = SuitType.CLUB;
    SuitType s2 = SuitType.DIAMOND;
    SuitType s3 = SuitType.SPADE;
    SuitType s4 = SuitType.HEART;
    assertEquals("♣", s1.toString());
    assertEquals("♦", s2.toString());
    assertEquals("♠", s3.toString());
    assertEquals("♥", s4.toString());
  }

  //test for return color
  @Test
  public void testReturnColor() {
    SuitType s1 = SuitType.CLUB;
    SuitType s2 = SuitType.DIAMOND;
    SuitType s3 = SuitType.SPADE;
    SuitType s4 = SuitType.HEART;
    assertEquals(0, s1.returnColor());
    assertEquals(1, s2.returnColor());
    assertEquals(0, s3.returnColor());
    assertEquals(1, s4.returnColor());
  }
}