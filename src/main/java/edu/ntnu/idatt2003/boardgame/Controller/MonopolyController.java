package edu.ntnu.idatt2003.boardgame.Controller;

import edu.ntnu.idatt2003.boardgame.Model.BoardGame;

import edu.ntnu.idatt2003.boardgame.Observer.BoardGameObserver;
import edu.ntnu.idatt2003.boardgame.View.MonopolyView;

public class MonopolyController implements BoardGameObserver {

    private MonopolyView monopolyView;
    private BoardGame boardGame;

    public MonopolyController(MonopolyView monopolyView) {
        this.monopolyView = monopolyView;
        this.boardGame = new BoardGame();
        monopolyView.initialize();
        attachEventHandlers();
    }

    @Override
    public void update(String event, BoardGame boardGame){

    }
    /**
     * Attaching actions to the buttons made in BoardGameView.java
     */
    private void attachEventHandlers() {

    }

}
