package edu.ntnu.idatt2003.boardgame.writers;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class WritePlayers {

    private static final Logger LOGGER = Logger.getLogger(WritePlayers.class.getName());

    public static void writePlayersToFile(String fileName, List<String> players) throws IOException {
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            for (String s : players) {
                fileWriter.write(s + System.lineSeparator());
            }
            LOGGER.info("Wrote " + players.size() + " players to " + fileName);
        }
    }

}
