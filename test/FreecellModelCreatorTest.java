import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import cs3500.freecell.model.FreecellModelCreator;
import cs3500.freecell.model.FreecellModelCreator.GameType;
import cs3500.freecell.model.hw02.SimpleFreecellModel;
import cs3500.freecell.model.hw04.MultiMoveFreecellModel;
import org.junit.Test;

/**
 * testing class for the FreecellModelCreator.
 */
public class FreecellModelCreatorTest {

  @Test
  public void testMakeModel() {
    assertTrue(
        new FreecellModelCreator().create(GameType.MULTIMOVE) instanceof MultiMoveFreecellModel);
    assertTrue(
        new FreecellModelCreator().create(GameType.SINGLEMOVE) instanceof SimpleFreecellModel);
    assertTrue(
        new FreecellModelCreator().create(GameType.MULTIMOVE) instanceof SimpleFreecellModel);
    assertFalse(
        new FreecellModelCreator().create(GameType.SINGLEMOVE) instanceof MultiMoveFreecellModel);
  }
}