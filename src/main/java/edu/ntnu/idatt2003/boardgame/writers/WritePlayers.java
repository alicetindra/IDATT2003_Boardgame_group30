package edu.ntnu.idatt2003.boardgame.writers;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

/**
 * Utility class for writing a list of player names to a file.
 */
public class WritePlayers {

    private static final Logger LOGGER = Logger.getLogger(WritePlayers.class.getName());

    /**
     * Writes the given list of player names to the specified file.
     * Each player name is written on a new line.
     *
     * @param fileName the name (or path) of the file to write to
     * @param players the list of player names to write
     * @throws IOException if an I/O error occurs while writing to the file
     */
    public static void writePlayersToFile(String fileName, List<String> players) throws IOException {
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            for (String s : players) {
                fileWriter.write(s + System.lineSeparator());
            }
            LOGGER.info("Wrote " + players.size() + " players to " + fileName);
        }
    }

}
