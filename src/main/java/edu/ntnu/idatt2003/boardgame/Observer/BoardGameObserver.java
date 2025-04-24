package edu.ntnu.idatt2003.boardgame.Observer;

import edu.ntnu.idatt2003.boardgame.Model.BoardGame;

public interface BoardGameObserver {

  void update(String event, BoardGame boardGame);

}
