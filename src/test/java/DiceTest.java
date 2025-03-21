import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idatt2003.boardgame.componentHolders.Dice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DiceTest {

  private Dice dice;

  @BeforeEach
  public void setUp() {
    dice = new Dice(6);
  }

  //------------------------Positive tests--------------------------------

  @Test
  public void testRoll() {
    int result = dice.roll();
    assertTrue(result >= 5 && result <= 30, "Total roll result should be between 5 and 30");
  }

  @Test
  public void testGetDie() {
    dice.roll();
    for (int i = 0; i < 5; i++) {
      int result = dice.getDie(i);
      assertTrue(result >= 1 && result <= 6, "Die value should be between 1 and 6");
    }
  }

  //--------------------------Negative tests-------------------------

  @Test
  public void testGetDieInvalidIndex() {
    Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
      dice.getDie(8); // Index out of bounds
    });
    assertEquals("Index out of bounds", exception.getMessage());
  }









}
