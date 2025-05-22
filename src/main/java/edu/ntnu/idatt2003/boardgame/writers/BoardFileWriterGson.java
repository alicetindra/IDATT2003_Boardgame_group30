package edu.ntnu.idatt2003.boardgame.writers;

import com.google.gson.JsonObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;
/**
 * Implementation of the {@link BoardFileWriter} interface that writes a JSON object to a file
 * using the Gson library's {@link JsonObject} string representation.
 */
public class BoardFileWriterGson implements BoardFileWriter{

  private static final Logger LOGGER = Logger.getLogger(BoardFileWriterGson.class.getName());

  /**
   * Writes the given {@link JsonObject} to a file specified by the file path.
   * Uses a {@link FileWriter} with automatic resource management to write the JSON string.
   *
   * @param jsonObject the JSON object to write to the file
   * @param filePath the path of the file to write the JSON data into
   */
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