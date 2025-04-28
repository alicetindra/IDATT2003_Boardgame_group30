package edu.ntnu.idatt2003.boardgame.Controller;

import edu.ntnu.idatt2003.boardgame.Model.*;
import edu.ntnu.idatt2003.boardgame.Observer.BoardGameObserver;
import edu.ntnu.idatt2003.boardgame.View.MenuView;
import edu.ntnu.idatt2003.boardgame.View.SnakesAndLaddersView;
import java.util.logging.Logger;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class SnakesAndLaddersController implements BoardGameObserver {
    private static final Logger log = Logger.getLogger(SnakesAndLaddersController.class.getName());

    private final SnakesAndLaddersView snakesLaddersView;
    private final MenuView menuView;
    private BoardGame boardGame;
    private Board board;
    private Stage primaryStage;


    public SnakesAndLaddersController(SnakesAndLaddersView snakesLaddersView, MenuView menuView, BoardGame boardGame) {
        this.snakesLaddersView = snakesLaddersView;
        this.menuView = menuView;
        this.boardGame = boardGame;
        snakesLaddersView.initialize();
        attachEventHandlers();
    }

    @Override
    public void update(String event, BoardGame boardGame){
        switch (event) {
            case "playerMoved" -> {
                snakesLaddersView.getDisplayInfoBox().getChildren().clear();
                displayDice();
                displayInfoBox();

                Player currentPlayer = boardGame.getPlayerHolder().getCurrentPlayer();
                int newTileId = currentPlayer.getCurrentTile().getId();

                addPlayerImageToNewTile(currentPlayer, newTileId);
            }
            case "winnerDeclared" -> {
                Player winner = boardGame.getWinner();
                snakesLaddersView.createWinnerBox(winner.getName(), winner.getColor());
                snakesLaddersView.getStartRoundButton().setDisable(true);
                menuView.getMenuLayout().getChildren().addAll(snakesLaddersView.getWinnerBox());
                menuView.playConfettiEffect();
            }
        }
    }
    /**
     * Attaching actions to the buttons made in BoardGameView.java
     */
    private void attachEventHandlers() {
        snakesLaddersView.getStartRoundButton().setOnAction(e ->handleStartRound());
        snakesLaddersView.getRestartGameButton().setOnAction(e ->handleRestartGame());
        menuView.getMainMenuButton().setOnAction(e -> clearGame());
    }


    public void loadBoard() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Custom Board JSON File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON files", "*.json"));
        File file = fileChooser.showOpenDialog(primaryStage);

        if (file != null) {
            boardGame.loadCustomBoard(file.getPath());
            menuView.getBoardSizeMenu().setDisable(true);
        } else {
            log.info("User cancelled file selection.");
        }
    }

    public void displayDice(){
        snakesLaddersView.getDieBox().getChildren().clear();
        for(Die d : boardGame.getDice().getListOfDice()){
            ImageView die = new ImageView(new Image("/images/dice"+d.getValue()+".png"));
            die.setFitHeight(40);
            die.setFitWidth(40);
            snakesLaddersView.getDieBox().getChildren().add(die);
            snakesLaddersView.getDieBox().setAlignment(Pos.CENTER);
        }
        snakesLaddersView.getDisplayInfoBox().getChildren().add(snakesLaddersView.getDieBox());
    }

    public void displayInfoBox(){
        String message = "";
        if(boardGame.getDice().getTotalSumOfEyes() == 8|| boardGame.getDice().getTotalSumOfEyes() == 11 || boardGame.getDice().getTotalSumOfEyes() == 18){
            message = "Player "+ boardGame.getPlayerHolder().getCurrentPlayer().getColor() + ", threw an " + boardGame.getDice().getTotalSumOfEyes() + " and landed on tile " + boardGame.getPlayerHolder().getCurrentPlayer().getCurrentTile().getId();
        } else {
            message =
                "Player " + boardGame.getPlayerHolder().getCurrentPlayer().getColor() + ", threw a "
                    + boardGame.getDice().getTotalSumOfEyes() + " and landed on tile "
                    + boardGame.getPlayerHolder().getCurrentPlayer().getCurrentTile().getId();
        }

        snakesLaddersView.updateInfoBox(message);
    }

    private void addPlayerImageToNewTile(Player player, int newTileId){
        for(Tile t : board.getTiles()){
            if(t.getId() == newTileId){
                t.getTileBox().getChildren().add(snakesLaddersView.getPlayerImage(player));
            }
        }
    }

    /**
     *
     * @throws IOException
     */
    public void setUpSnakesLaddersGame() throws IOException {
        this.boardGame.removeObserver(this);
        this.boardGame.addObserver(this);
        snakesLaddersView.getLayout().getChildren().clear();
        snakesLaddersView.getDisplayInfoBox().getChildren().clear();
        try {
            boardGame.createPlayerHolder("src/main/resources/players.csv",boardGame.getListOfPlayers());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        for(Player p : boardGame.getPlayerHolder().getPlayers()){
            ImageView imageView = new ImageView(new Image("images/"+p.getColor()+".png"));
            p.setImageView(imageView);
            p.setBoardGame(boardGame);
        }

        if (!boardGame.isCustomBoardLoaded()) {
            boardGame.initializeBoard(
            menuView.getGameName(), menuView.getBoardSizeMenu().getValue(), "src/main/resources/hardcodedBoards.json");
        }

        board = boardGame.getBoard();

        boardGame.initializeDice(Integer.parseInt(menuView.getDiceField().getText()));

        //Create BoardGrid
        snakesLaddersView.createGridBoard(board);
        GridPane boardGrid = snakesLaddersView.getGrid();
        boardGrid.setAlignment(Pos.CENTER);

        snakesLaddersView.createTitleBox();
        snakesLaddersView.createRulesColumn();
        snakesLaddersView.createStartButton();
        menuView.createMainMenuButton();

        snakesLaddersView.createInfoColumn(boardGame.getPlayerHolder());

        snakesLaddersView.getInfoColumn().getChildren().add(snakesLaddersView.getStartRoundButton());
        snakesLaddersView.getRulesColumn().getChildren().add(snakesLaddersView.getRestartGameButton());
        snakesLaddersView.getRulesColumn().getChildren().add(menuView.getMainMenuButton());

        //add to StackPane layout
        BorderPane gameLayout = snakesLaddersView.createSnakesLaddersLayout(boardGrid, snakesLaddersView.getTitleBox(),
            snakesLaddersView.getRulesColumn(), snakesLaddersView.getInfoColumn());

        menuView.getMenuLayout().getChildren().clear();
        menuView.getMenuLayout().getChildren().add(gameLayout);
        StackPane.setAlignment(gameLayout, Pos.CENTER);

        for(Player p : boardGame.getPlayerHolder().getPlayers()){
            p.placeOnTile(board,1);
            addPlayerImageToNewTile(p, 1);
        }
    }

    private void handleStartRound() {
        snakesLaddersView.getRestartGameButton().setDisable(false);
        boardGame.play();
    }

    /**
     * Removes the WinnerBox and the winner of the previous game, disables the restart button, calls setUpSnakesLaddersGame();
     */
    private void handleRestartGame() {
        snakesLaddersView.getLayout().getChildren().remove(snakesLaddersView.getWinnerBox());
        snakesLaddersView.getDisplayInfoBox().getChildren().clear();
        boardGame.undoWinner(boardGame.getWinner());
        snakesLaddersView.getStartRoundButton().setDisable(false);
        try {
            setUpSnakesLaddersGame();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void clearGame(){
        boardGame.undoWinner(boardGame.getWinner());

        snakesLaddersView.getStartRoundButton().setDisable(false);
        snakesLaddersView.getGrid().getChildren().clear();
        snakesLaddersView.getInfoColumn().getChildren().clear();
        snakesLaddersView.getLayout().getChildren().clear();
        snakesLaddersView.getDieBox().getChildren().clear();

        menuView.getSLButton().setSelected(false);
        menuView.getPlayerData().clear();
        menuView.setPlayerColorBox();
        menuView.getDiceField().clear();

        snakesLaddersView.getLayout().getChildren().clear();
        boardGame.clearBoard();
        menuView.createMainMenu();
    }


}
