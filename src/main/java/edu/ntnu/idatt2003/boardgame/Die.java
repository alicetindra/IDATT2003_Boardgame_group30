package edu.ntnu.idatt2003.boardgame;
import java.util.Random;

public class Die {
  private int lastRolledValue;


  public int roll(){
    lastRolledValue = new Random().nextInt(1, 7);
    return lastRolledValue;
  }



}
