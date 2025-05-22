package edu.ntnu.idatt2003.boardgame.writers;

import com.google.gson.JsonObject;

/**
 * Interface for writing a board game's JSON representation to a file.
 *
 * <p>Implementing classes should define how a {@link JsonObject} representing a board
 * configuration is written to a file at a specified path.
 */
public interface BoardFileWriter {
  /**
   * Writes a {@link JsonObject} to a file at the given file path.
   *
   * @param jsonObject the JSON object to write to file
   * @param filePath the path of the file to write to
   */
  void writeJsonToFile(JsonObject jsonObject, String filePath);
}