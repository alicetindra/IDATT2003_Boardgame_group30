package edu.ntnu.idatt2003.boardgame.readers;

import edu.ntnu.idatt2003.boardgame.componentHolders.Board;

public interface BoardFileReader {
    Board readTilesFromFile(String filePath);
}
