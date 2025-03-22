package edu.ntnu.idatt2003.boardgame.readers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.ntnu.idatt2003.boardgame.componentHolders.Board;
import edu.ntnu.idatt2003.boardgame.actions.TileAction;

import java.io.FileReader;
import java.io.IOException;

public class ReadBoard {
    public static Board readTilesFromFile(String filePath) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(TileAction.class, new TileActionReader())
                .create();
        try (FileReader fileReader = new FileReader(filePath)) {
            return gson.fromJson(fileReader, Board.class);
        } catch (IOException e) {
            System.out.println("Error reading file " + filePath);
            return null;
        }
    }
}

