package edu.ntnu.idatt2003.boardgame.Controller;

import edu.ntnu.idatt2003.boardgame.Model.Board;
import edu.ntnu.idatt2003.boardgame.Model.BoardGame;

import edu.ntnu.idatt2003.boardgame.Observer.BoardGameObserver;
import edu.ntnu.idatt2003.boardgame.View.MenuView;
import edu.ntnu.idatt2003.boardgame.View.MonopolyView;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class MonopolyController implements BoardGameObserver {

    private final MonopolyView monopolyView;
    private final MenuView menuView;
    private final BoardGame boardGame;
    private Board board;

    public MonopolyController(MonopolyView monopolyView, MenuView menuView ,BoardGame boardGame) {
        this.monopolyView = monopolyView;
        this.menuView = menuView;
        this.boardGame = boardGame;

        monopolyView.initialize();
        attachEventHandlers();
    }

    @Override
    public void update(String event, BoardGame boardGame){

    }

    private void attachEventHandlers() {

    }
    public void initializeBoard(int width, int height) {
        boardGame.initializeBoard("monopoly", (width*2+height*2), "hardcodedBoards.json");
        board = boardGame.getBoard();
        monopolyView.createBoardGrid(width, height, board);
    }

    public void setUpMonopolyGame() throws IOException {
        monopolyView.getMonopolyLayout().getChildren().clear();
        monopolyView.getRootLayout().getChildren().clear();
        initializeBoard(12,10);
        monopolyView.createMonopolyLayout();
        BorderPane gameLayout = monopolyView.getMonopolyLayout();
        menuView.getMenuLayout().getChildren().clear();
        menuView.getMenuLayout().getChildren().add(gameLayout);
        StackPane.setAlignment(gameLayout, Pos.CENTER);
    }

}
