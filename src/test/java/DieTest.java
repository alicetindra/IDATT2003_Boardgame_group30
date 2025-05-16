import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idatt2003.boardgame.Model.Die;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DieTest {
  private Die die;

  @BeforeEach
  public void setUp() {
    die = new Die();
  }


  //-------------------------Positive Tests----------------------------
  @Test
  public void testRoll() {
    int result = die.roll();
    assertTrue(result >= 1 && result <= 6, "Roll result should be between 1 and 6");
  }

  @Test
  public void testGetValue() {
    die.roll();
    int result = die.getValue();
    assertTrue(result >= 1 && result <= 6, "Value should be between 1 and 6");
  }
}
