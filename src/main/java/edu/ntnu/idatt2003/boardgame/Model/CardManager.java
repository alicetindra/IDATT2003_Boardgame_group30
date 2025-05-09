package edu.ntnu.idatt2003.boardgame.Model;

import edu.ntnu.idatt2003.boardgame.readers.CardReaderGson;
import edu.ntnu.idatt2003.boardgame.readers.CardReaderGson;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CardManager{
  private List<Card> cards;
  private final Random random = new Random();
  private final String filename;
  private Card lastDrawnCard;

  public CardManager(String fileName){
    this.filename = fileName;
    cards = new ArrayList<>();
    loadCardsFromFile();
  }

  public void loadCardsFromFile(){
    cards = CardReaderGson.readCardsFromFile(filename);
  }

  public Card drawCard() {
    lastDrawnCard = cards.get(random.nextInt(cards.size()));
    return lastDrawnCard;
  }
  public Card getLastDrawnCard() {
    return lastDrawnCard;
  }

  public void applyCard(Card card, Player player) {
    System.out.println(card.text);
    if (card.money != null) player.editMoney(card.money);
    if (card.moveTo != null) player.placeOnTile(player.getBoardGame().getBoard(), card.moveTo);
    if (Boolean.TRUE.equals(card.goToJail)) {
      player.placeOnTile(player.getBoardGame().getBoard(),8);
      player.setInJail(true);
    }
  }
  public String sendAlert(){
    return lastDrawnCard.text;
  }
}