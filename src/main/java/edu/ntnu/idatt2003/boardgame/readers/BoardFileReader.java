package edu.ntnu.idatt2003.boardgame.readers;

import edu.ntnu.idatt2003.boardgame.Model.Board;

/**
 * Interface for reading a {@link Board} configuration from a file.
 * <p>
 * Implementations of this interface define how a game board's tiles
 * are loaded from a file, enabling different formats (e.g., JSON, CSV).
 * This abstraction allows for flexible and extensible board loading logic.
 */
public interface BoardFileReader {

  /**
   * Reads board tile data from the specified file path and returns a {@link Board} instance.
   *
   * @param filePath the path to the file containing the board configuration
   * @return a {@code Board} object initialized with the tile data from the file
   */
  Board readTilesFromFile(String filePath);
}