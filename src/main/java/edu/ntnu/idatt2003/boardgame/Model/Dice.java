package edu.ntnu.idatt2003.boardgame.Model;

import java.util.ArrayList;
import java.util.List;

public class Dice {
  private List<Die> dice;

  public Dice(int numberOfDice) {
    dice = new ArrayList<>();
    for (int i = 0; i < numberOfDice; i++) {
      this.dice.add(new Die());
    }
  }

  public int roll(){
    int total = 0;
    for(Die die:dice){
      total += die.roll();
    }
    return total;
  }

  public int getDie(int dieNumber){
    if(dieNumber > dice.size()){
      throw new IndexOutOfBoundsException("Index out of bounds");
    }
    return dice.get(dieNumber).getValue();
  }

  public List<Die> getListOfDice() {
    return dice;
  }

  public int getTotalSumOfEyes(){
    int sum = 0;
    for(Die d: getListOfDice()){
      sum += d.getValue();
    }
    return sum;
  }


}
