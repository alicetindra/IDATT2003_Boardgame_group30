package edu.ntnu.idatt2003.boardgame.Model;
import java.util.Random;

/**
 * Represents a standard six-sided die that can be rolled to generate a value from 1 to 6.
 */
public class Die {

  private int lastRolledValue;

  /**
   * Rolls the die to generate a random value between 1 and 6 (inclusive).
   *
   * @return the result of the die roll
   */
  public int roll(){
    lastRolledValue = new Random().nextInt(1, 7);
    return lastRolledValue;
  }
  /**
   * Returns the value from the most recent roll.
   *
   * @return the last rolled value
   */
  public int getValue(){
    return lastRolledValue;
  }


}
