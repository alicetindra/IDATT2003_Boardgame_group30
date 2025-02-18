package edu.ntnu.idatt2003.boardgame;

import java.util.ArrayList;
import java.util.List;

public class Dice {
  private List<Die> dice;

  public Dice(int numberOfDice) {
    dice = new ArrayList<>();
    for (int i = 0; i < numberOfDice; i++) {
      dice.add(new Die());
    }
  }



}
