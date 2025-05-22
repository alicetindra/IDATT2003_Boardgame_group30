package edu.ntnu.idatt2003.boardgame.readers;


import edu.ntnu.idatt2003.boardgame.Model.Player;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Utility class for reading {@link Player} objects from a CSV file.
 * <p>
 * Each line in the file is expected to contain a player's name and color, separated by a comma.
 * This class provides a static method that reads these lines, creates {@code Player} objects,
 * and returns them as a list.
 * <p>
 * Logs the number of players successfully read, or an error if the file could not be processed.
 */
public class ReadPlayers {

    private static final Logger log = Logger.getLogger(ReadPlayers.class.getName());

    /**
     * Reads player data from a file and returns a list of {@link Player} objects.
     *
     * <p>The expected format for each line in the file is:
     * {@code name,color}
     *
     * @param fileName the path to the CSV file containing player data
     * @return a list of {@code Player} objects read from the file; may be empty if the file is not found or is invalid
     */
    public static List<Player> readPlayersFromFile(String fileName) {
        List<Player> players = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;

            while ((line = reader.readLine()) != null) {

                String[] parts = line.split(",");

                if (parts.length >= 2) {
                    String name = parts[0].trim();
                    String color = parts[1].trim();

                    Player player = new Player(name, color);
                    players.add(player);
                }
            }
            log.info("Successfully read " + players.size() + " players");
        } catch (IOException e) {
            log.severe("Error reading file: " + fileName + " - " + e.getMessage());
        }
        return players;
    }
}
