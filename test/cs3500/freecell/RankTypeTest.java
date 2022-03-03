package cs3500.freecell;

import cs3500.freecell.model.RankType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * Tests for the RankType enum.
 */
public class RankTypeTest {

  //tests for returnString
  @Test
  public void testReturnString() {
    RankType r1 = RankType.ACE;
    RankType r2 = RankType.THREE;
    RankType r3 = RankType.JACK;
    assertEquals("A", r1.toString());
    assertEquals("3", r2.toString());
    assertEquals("J", r3.toString());
  }

  //tests for returnIndex
  @Test
  public void testReturnIndex() {
    RankType r1 = RankType.ACE;
    RankType r2 = RankType.THREE;
    RankType r3 = RankType.JACK;
    assertEquals(0, r1.returnIndex());
    assertEquals(2, r2.returnIndex());
    assertEquals(10, r3.returnIndex());
  }
}