package edu.ntnu.idatt2003.boardgame.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a collection of dice used in the board game.
 * Provides functionality to roll all dice, access individual dice values,
 * and compute the total sum of rolled values.
 */
public class Dice {
  /** The list of individual dice. */
  private final List<Die> dice;

  /**
   * Constructs a {@code Dice} object with the specified number of dice.
   *
   * @param numberOfDice the number of dice to initialize
   */
  public Dice(int numberOfDice) {
    dice = new ArrayList<>();
    for (int i = 0; i < numberOfDice; i++) {
      this.dice.add(new Die());
    }
  }

  /**
   * Rolls all dice and returns the total sum of the rolled values.
   *
   * @return the total sum of all dice rolls
   */
  public int roll(){
    int total = 0;
    for(Die die:dice){
      total += die.roll();
    }
    return total;
  }

  /**
   * Returns the last rolled value of the die at the specified index.
   *
   * @param dieNumber the index of the die (0-based)
   * @return the value of the specified die
   * @throws IndexOutOfBoundsException if the index is invalid
   */
  public int getDie(int dieNumber){
    if(dieNumber > dice.size()){
      throw new IndexOutOfBoundsException("Index out of bounds");
    }
    return dice.get(dieNumber).getValue();
  }

  /**
   * Returns the list of all dice objects.
   *
   * @return the list of {@link Die} instances
   */
  public List<Die> getListOfDice() {
    return dice;
  }

  /**
   * Returns the total sum of the values currently shown on all dice.
   *
   * @return the sum of all current die values
   */
  public int getTotalSumOfEyes(){
    int sum = 0;
    for(Die d: getListOfDice()){
      sum += d.getValue();
    }
    return sum;
  }


}
