package edu.ntnu.idatt2003.boardgame.Model;

/**
 * Represents a game card that can perform various actions in the game,
 * such as giving or taking money, moving a player to a specific tile,
 * or sending them to jail.
 */
public class Card {
  /** The text description of the card's effect. */
  public String text;
  /** The amount of money to add or subtract when the card is drawn. Can be null. */
  public Integer money;
  /** The tile index to move the player to. Can be null. */
  public Integer moveTo;
  /** Indicates whether the card sends the player to jail. */
  public Boolean goToJail;

  /**
   * Constructs an empty card. All fields must be set manually.
   */
  public Card(){
  }

  /**
   * Returns the text description of the card.
   *
   * @return the card's text description
   */
  @Override
  public String toString() {
    return text;
  }
}