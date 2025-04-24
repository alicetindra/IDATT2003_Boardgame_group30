package edu.ntnu.idatt2003.boardgame.writers;

import com.google.gson.JsonObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;


public class BoardFileWriterGson implements BoardFileWriter{

  private static final Logger LOGGER = Logger.getLogger(BoardFileWriterGson.class.getName());

  @Override
  public void writeJsonToFile(JsonObject jsonObject, String filePath) {
    try (FileWriter fileWriter = new FileWriter(filePath)) {
      fileWriter.write(jsonObject.toString());
      fileWriter.flush();
      LOGGER.info("Board data successfully written to " + filePath);
    } catch (IOException e) {
      LOGGER.severe("Error writing to file: " + filePath + " - " + e.getMessage());
    }
  }
}