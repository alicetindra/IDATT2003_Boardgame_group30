package edu.ntnu.idatt2003.boardgame.Model;

import edu.ntnu.idatt2003.boardgame.readers.CardReaderGson;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/**
 * Manages a deck of {@link Card} objects for use in the board game.
 * Cards are loaded from a file and drawn randomly.
 * The class also handles applying card effects to a {@link Player}.
 */
public class CardManager{
  /** The list of all loaded cards. */
  private List<Card> cards;

  /** Random instance used to draw cards. */
  private final Random random = new Random();

  /** The path to the file containing the card definitions. */
  private final String filename;

  /** The last card drawn. */
  private Card lastDrawnCard;

  /**
   * Constructs a CardManager and loads cards from the given file.
   *
   * @param fileName the path to the JSON file containing cards
   */
  public CardManager(String fileName){
    this.filename = fileName;
    cards = new ArrayList<>();
    loadCardsFromFile();
  }

  /**
   * Loads card data from the JSON file specified in the constructor.
   * Overwrites the current list of cards.
   */
  public void loadCardsFromFile(){
    cards = CardReaderGson.readCardsFromFile(filename);
  }


  public void drawCard() {
    lastDrawnCard = cards.get(random.nextInt(cards.size()));
  }

  /**
   * Returns the most recently drawn card.
   *
   * @return the last drawn {@link Card}
   */
  public Card getLastDrawnCard() {
    return lastDrawnCard;
  }
  public List<Card> getCards() {
    return cards;
  }

  /**
   * Applies the effect of the specified card to the given player.
   * Effects may include modifying money, moving the player, or sending them to jail.
   *
   * @param card the card whose effect to apply
   * @param player the player to apply the card effect to
   */
  public void applyCard(Card card, Player player) {
    if (card.money != null) player.editMoney(card.money);
    if (card.moveTo != null) player.placeOnTile(player.getBoardGame().getBoard(), card.moveTo);
    if (Boolean.TRUE.equals(card.goToJail)) {
      player.placeOnTile(player.getBoardGame().getBoard(),8);
      player.setInJail(true);
    }
  }

  /**
   * Returns the text of the last drawn card for display purposes.
   *
   * @return the card text as a string
   */
  public String sendAlert(){
    return lastDrawnCard.text;
  }
}