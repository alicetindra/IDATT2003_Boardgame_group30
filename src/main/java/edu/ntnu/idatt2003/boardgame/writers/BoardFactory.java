package edu.ntnu.idatt2003.boardgame.writers;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * A utility class for generating hardcoded board configurations for different board games
 * in JSON format.
 * <p>
 * Each method in this class is responsible for serializing tile layouts with specific
 * game rules and tile actions such as ladders, snakes, portals, jail, and draw actions.
 * <p>
 * This class supports several predefined game types, including:
 * <ul>
 *   <li>"ladders" with 50, 90, or 110 tiles</li>
 *   <li>"monopoly" with 26 tiles</li>
 * </ul>
 * <p>
 * This class cannot be instantiated.
 */
public class BoardFactory {

  /**
   * Private constructor to prevent instantiation of this utility class.
   * Throws {@link UnsupportedOperationException} if called.
   */
  private BoardFactory() {
    throw new UnsupportedOperationException("Utility class");
  }

  /**
   * Serializes the tile layout and tile actions for a specified game type and board size.
   *
   * @param chosenGame the name of the game (e.g., "ladders", "monopoly")
   * @param n the number of tiles on the board
   * @return a {@link JsonObject} representing the serialized tile configuration
   */
  public static JsonObject serializeTiles(String chosenGame, int n) {
    JsonObject tileBook = new JsonObject();

    if(chosenGame.toLowerCase().contains("ladders")) {
      if (n == 90) {
        final var ladderJsonArray = getJsonElements90(n);
        tileBook.add("tiles", ladderJsonArray);
      }
      else if (n == 50) {
        final var ladderJsonArray = getJsonElements50(n);
        tileBook.add("tiles", ladderJsonArray);
      }
      else if (n == 110) {
        final var ladderJsonArray = getJsonElements110(n);
        tileBook.add("tiles", ladderJsonArray);
      }
    }

    else if(chosenGame.toLowerCase().contains("monopoly")) {
      JsonArray monopolyJsonArray = new JsonArray();

      for (int i = 1; i <=26; i++) {
        JsonObject tileJson = new JsonObject();
        tileJson.addProperty("id", i);
        if(i==26){
          tileJson.addProperty("next", 1);
        }
        else{
          tileJson.addProperty("next", i + 1);
        }

        if (i == 21) {
          JsonObject actionObj = new JsonObject();
          actionObj.addProperty("type", "jail");
          actionObj.addProperty("destination", 8);
          tileJson.add("action", actionObj);
        }
        if(i == 4 || i == 11 || i == 17 || i == 22 || i == 25){
          JsonObject actionObj = new JsonObject();
          actionObj.addProperty("type", "draw");
          actionObj.addProperty("destination", 1);
          tileJson.add("action", actionObj);
        }

        monopolyJsonArray.add(tileJson);
      }
      tileBook.add("tiles", monopolyJsonArray);
    }

    return tileBook;
  }

  /**
   * Returns a JSON array of tiles for a 50-tile ladders game.
   *
   * @param n the number of tiles (should be 50)
   * @return a {@link JsonArray} representing the tile layout
   */
  private static JsonArray getJsonElements50(int n) {
    JsonArray ladderJsonArray = new JsonArray();

    for (int i = 1; i <= n; i++) {
      //Tile map
      JsonObject tileJson = new JsonObject();
      tileJson.addProperty("id", i);

      //adding the next tile
      if (i < n) {
        tileJson.addProperty("next", i + 1);
      }

      //action map
      //Ladders
      if (i == 14) {
        JsonObject actionObj = new JsonObject();
        actionObj.addProperty("type", "ladder");
        actionObj.addProperty("destination", 28);
        tileJson.add("action", actionObj);
      }
      if (i == 37) {
        JsonObject actionObj = new JsonObject();
        actionObj.addProperty("type", "ladder");
        actionObj.addProperty("destination", 42);
        tileJson.add("action", actionObj);

      }
      //Snakes
      if (i == 22) {
        JsonObject actionObj = new JsonObject();
        actionObj.addProperty("type", "snake");
        actionObj.addProperty("destination", 2);
        tileJson.add("action", actionObj);
      }

      if (i == 49) {
        JsonObject actionObj = new JsonObject();
        actionObj.addProperty("type", "snake");
        actionObj.addProperty("destination", 30);
        tileJson.add("action", actionObj);
      }
      //Portals
      if (i == 46) {
        JsonObject actionObj = new JsonObject();
        actionObj.addProperty("type", "portal");
        actionObj.addProperty("destination", 0);
        tileJson.add("action", actionObj);
      }
      if (i == 50) {
        JsonObject actionObj = new JsonObject();
        actionObj.addProperty("type", "win");
        actionObj.addProperty("destination", 50);
        tileJson.add("action", actionObj);
      }

      ladderJsonArray.add(tileJson);
    }
    return ladderJsonArray;
  }

  /**
   * Returns a JSON array of tiles for a 90-tile ladders game.
   *
   * @param n the number of tiles (should be 90)
   * @return a {@link JsonArray} representing the tile layout
   */
  private static JsonArray getJsonElements90(int n) {
    JsonArray ladderJsonArray = new JsonArray();

    for (int i = 1; i <= n; i++) {
      //Tile map
      JsonObject tileJson = new JsonObject();
      tileJson.addProperty("id", i);

      //adding the next tile
      if (i < n) {
        tileJson.addProperty("next", i + 1);
      }

      //action map
      //Ladders
      if (i == 5) {
        JsonObject actionObj = new JsonObject();
        actionObj.addProperty("type", "ladder");
        actionObj.addProperty("destination", 25);
        tileJson.add("action", actionObj);
      }
      if (i == 18) {
        JsonObject actionObj = new JsonObject();
        actionObj.addProperty("type", "ladder");
        actionObj.addProperty("destination", 21);
        tileJson.add("action", actionObj);
      }
      if (i == 55) {
        JsonObject actionObj = new JsonObject();
        actionObj.addProperty("type", "ladder");
        actionObj.addProperty("destination", 73);
        tileJson.add("action", actionObj);
      }
      //Snakes
      if (i == 28) {
        JsonObject actionObj = new JsonObject();
        actionObj.addProperty("type", "snake");
        actionObj.addProperty("destination", 8);
        tileJson.add("action", actionObj);
      }
      if (i == 42) {
        JsonObject actionObj = new JsonObject();
        actionObj.addProperty("type", "snake");
        actionObj.addProperty("destination", 24);
        tileJson.add("action", actionObj);
      }
      if (i == 70) {
        JsonObject actionObj = new JsonObject();
        actionObj.addProperty("type", "snake");
        actionObj.addProperty("destination", 47);
        tileJson.add("action", actionObj);
      }
      if (i == 87) {
        JsonObject actionObj = new JsonObject();
        actionObj.addProperty("type", "snake");
        actionObj.addProperty("destination", 75);
        tileJson.add("action", actionObj);
      }

      if (i == 49 || i == 63) {
        JsonObject actionObj = new JsonObject();
        actionObj.addProperty("type", "portal");
        actionObj.addProperty("destination", 0);
        tileJson.add("action", actionObj);
      }
      if (i == 90) {
        JsonObject actionObj = new JsonObject();
        actionObj.addProperty("type", "win");
        actionObj.addProperty("destination", 90);
        tileJson.add("action", actionObj);
      }

      ladderJsonArray.add(tileJson);
    }
    return ladderJsonArray;
  }

  /**
   * Returns a JSON array of tiles for a 110-tile ladders game.
   *
   * @param n the number of tiles (should be 110)
   * @return a {@link JsonArray} representing the tile layout
   */
  private static JsonArray getJsonElements110(int n) {
    JsonArray ladderJsonArray = new JsonArray();

    for (int i = 1; i <= n; i++) {
      //Tile map
      JsonObject tileJson = new JsonObject();
      tileJson.addProperty("id", i);

      //adding the next tile
      if (i < n) {
        tileJson.addProperty("next", i + 1);
      }

      //action map
      //Ladders
      if (i == 8) {
        JsonObject actionObj = new JsonObject();
        actionObj.addProperty("type", "ladder");
        actionObj.addProperty("destination", 27);
        tileJson.add("action", actionObj);
      }
      if (i == 38) {
        JsonObject actionObj = new JsonObject();
        actionObj.addProperty("type", "ladder");
        actionObj.addProperty("destination", 63);
        tileJson.add("action", actionObj);

      }
      if (i == 48) {
        JsonObject actionObj = new JsonObject();
        actionObj.addProperty("type", "ladder");
        actionObj.addProperty("destination", 93);
        tileJson.add("action", actionObj);

      }
      if (i == 82) {
        JsonObject actionObj = new JsonObject();
        actionObj.addProperty("type", "ladder");
        actionObj.addProperty("destination", 101);
        tileJson.add("action", actionObj);

      }
      //Snakes
      if (i == 24) {
        JsonObject actionObj = new JsonObject();
        actionObj.addProperty("type", "snake");
        actionObj.addProperty("destination", 5);
        tileJson.add("action", actionObj);
      }

      if (i == 52) {
        JsonObject actionObj = new JsonObject();
        actionObj.addProperty("type", "snake");
        actionObj.addProperty("destination", 20);
        tileJson.add("action", actionObj);
      }
      if (i == 62) {
        JsonObject actionObj = new JsonObject();
        actionObj.addProperty("type", "snake");
        actionObj.addProperty("destination", 41);
        tileJson.add("action", actionObj);
      }
      if (i == 98) {
        JsonObject actionObj = new JsonObject();
        actionObj.addProperty("type", "snake");
        actionObj.addProperty("destination", 76);
        tileJson.add("action", actionObj);
      }
      //Portals
      if (i == 19) {
        JsonObject actionObj = new JsonObject();
        actionObj.addProperty("type", "portal");
        actionObj.addProperty("destination", 0);
        tileJson.add("action", actionObj);
      }
      if (i == 45) {
        JsonObject actionObj = new JsonObject();
        actionObj.addProperty("type", "portal");
        actionObj.addProperty("destination", 0);
        tileJson.add("action", actionObj);
      }
      if (i == 74) {
        JsonObject actionObj = new JsonObject();
        actionObj.addProperty("type", "portal");
        actionObj.addProperty("destination", 0);
        tileJson.add("action", actionObj);
      }
      if (i == 110) {
        JsonObject actionObj = new JsonObject();
        actionObj.addProperty("type", "win");
        actionObj.addProperty("destination", 110);
        tileJson.add("action", actionObj);
      }

      ladderJsonArray.add(tileJson);
    }
    return ladderJsonArray;
  }
}