package edu.ntnu.idatt2003.boardgame.readers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import edu.ntnu.idatt2003.boardgame.Model.Card;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.logging.Logger;

public class CardReaderGson {

  private static final Logger LOGGER = Logger.getLogger(CardReaderGson.class.getName());

  public static List<Card> readCardsFromFile(String filePath) {
    Gson gson = new Gson();
    try (FileReader fileReader = new FileReader(filePath)) {
      Type cardListType = new TypeToken<List<Card>>() {}.getType();
      List<Card> cards = gson.fromJson(fileReader, cardListType);
      LOGGER.info("Successfully loaded " + cards.size() + " cards.");
      return cards;
    } catch (IOException e) {
      LOGGER.severe("Error reading cards from " + filePath + " - " + e.getMessage());
      return List.of();
    }
  }
}