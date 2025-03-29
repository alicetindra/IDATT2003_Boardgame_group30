package edu.ntnu.idatt2003.boardgame.readers;


import edu.ntnu.idatt2003.boardgame.Model.Player;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadPlayers {

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
        } catch (IOException e) {
            System.out.println("Error reading file: " + fileName);
        }
        return players;
    }
}
