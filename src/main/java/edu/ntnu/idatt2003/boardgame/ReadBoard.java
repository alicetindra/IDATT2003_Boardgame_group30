package edu.ntnu.idatt2003.boardgame;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.IOException;

public class ReadBoard {
    public static Board readTilesFromFile(String filePath) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileReader fileReader = new FileReader(filePath)) {
            return gson.fromJson(fileReader, Board.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
