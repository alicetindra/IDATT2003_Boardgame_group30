package edu.ntnu.idatt2003.boardgame.readers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import edu.ntnu.idatt2003.boardgame.Model.Board;
import edu.ntnu.idatt2003.boardgame.Model.actions.TileAction;
import edu.ntnu.idatt2003.boardgame.writers.BoardFileWriterGson;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Logger;


public class BoardFileReaderGson implements BoardFileReader {

  private static final Logger LOGGER = Logger.getLogger(BoardFileReaderGson.class.getName());

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
