package edu.ntnu.idatt2003.boardgame.readers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import edu.ntnu.idatt2003.boardgame.Model.Board;
import edu.ntnu.idatt2003.boardgame.Model.actions.TileAction;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Implementation of {@link BoardFileReader} that reads a {@link Board} configuration from a JSON file using Gson.
 * <p>
 * This class uses the Gson library to deserialize JSON data into a {@code Board} object.
 * It supports custom deserialization of {@link TileAction} objects using {@link TileActionReader}.
 * Logging is used to report success or failure during the file reading process.
 */
public class BoardFileReaderGson implements BoardFileReader {

  private static final Logger LOGGER = Logger.getLogger(BoardFileReaderGson.class.getName());

  /**
   * Reads a board configuration from a JSON file and returns it as a {@link Board} object.
   * <p>
   * Uses a custom deserializer for {@link TileAction} types.
   *
   * @param filePath the path to the JSON file containing the board data
   * @return the deserialized {@code Board} object, or {@code null} if an error occurred while reading the file
   */
  @Override
  public Board readTilesFromFile(String filePath) {
    Gson gson = new GsonBuilder()
        .registerTypeAdapter(TileAction.class, new TileActionReader())
        .create();
    try (FileReader fileReader = new FileReader(filePath)) {
      LOGGER.info("Successfully read board file: " + filePath);
      return gson.fromJson(fileReader, Board.class);
    } catch (IOException e) {
      LOGGER.severe("Error reading file " + filePath+ " - " + e.getMessage());
      return null;
    }
  }
}
