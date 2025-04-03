package edu.ntnu.idatt2003.boardgame.readers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import edu.ntnu.idatt2003.boardgame.Model.Board;
import edu.ntnu.idatt2003.boardgame.Model.Tile;
import edu.ntnu.idatt2003.boardgame.Model.actions.TileAction;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class BoardFileReaderGson implements BoardFileReader {

  @Override
  public Board readTilesFromFile(String filePath) {
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
