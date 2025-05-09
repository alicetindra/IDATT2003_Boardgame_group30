package edu.ntnu.idatt2003.boardgame.Model;

public class Card {
  public String text;
  public Integer money;
  public Integer moveTo;
  public Boolean goToJail;

  public Card(){

  }

  @Override
  public String toString() {
    return text;
  }
}