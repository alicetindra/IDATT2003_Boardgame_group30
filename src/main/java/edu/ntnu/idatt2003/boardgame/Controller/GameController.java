package edu.ntnu.idatt2003.boardgame.Controller;

import edu.ntnu.idatt2003.boardgame.Model.*;
import edu.ntnu.idatt2003.boardgame.View.BoardGameView;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameController {
    BoardGameView view;
    List<String> listOfPlayers = new ArrayList<>();
    BoardGame boardGame = new BoardGame();
    Board board;

    public GameController(BoardGameView view) {
        this.view = view;
        view.initialize();
        attachEventHandlers();
    }

    private void attachEventHandlers() {
        view.getAddPlayerButton().setOnAction(e -> handleAddPlayer());

        view.getMakeGameButton().setOnAction(e -> {
            if(listOfPlayers.isEmpty() || view.getDiceField() == null || view.getBoardSizeMenu().getSelectionModel().isEmpty()) {
                    getAlert("To start the game, you need the type of game, players, dice and board size!");
                    throw new IllegalArgumentException("Players, board size, game and/or dice is not added");
            }
            try {
                handleMakeGame();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        view.getStartRoundButton().setOnAction(e ->handleStartRound());

        view.getMainMenuButton().setOnAction(e -> resetMainMenu());

        view.getRestartGameButton().setOnAction(e-> {
            try {
                handleRestartGame();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });


    }

    private void handleRestartGame() throws IOException {
        view.getLayout().getChildren().remove(view.getWinnerBox());
        view.getRestartGameButton().setDisable(true);
        boardGame.undoWinner(boardGame.getWinner());
        view.getStartRoundButton().setDisable(false);
        handleMakeGame();

    }

    private void handleAddPlayer() {
        String selectedColor = view.getPlayerColorMenu().getSelectionModel().getSelectedItem();
        String writtenName = view.getPlayerName().getText();
        if(selectedColor == null || writtenName.isEmpty()) {
            getAlert("Please select a name and color for the player");
            throw new RuntimeException("Color or name is not selected");
        }

        listOfPlayers.add(writtenName+","+selectedColor);

        view.getPlayerName().clear();
        view.getPlayerColorMenu().getItems().remove(selectedColor);
    }

    private void handleMakeGame() throws IOException {
        view.getLayout().getChildren().clear();
        try {
            boardGame.createPlayerHolder("src/main/resources/players.csv",listOfPlayers);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        for(Player p : boardGame.getPlayerHolder().getPlayers()){
            ImageView imageView = new ImageView(new Image("images/"+p.getColor()+".png"));
            p.setImageView(imageView);
            p.setBoardGame(boardGame);
        }
        boardGame.initializeBoard(view.getGameName(),view.getBoardSizeMenu().getValue(),"src/main/resources/hardcodedBoards.json");
        board = boardGame.getBoard();


        boardGame.initializeDice(Integer.parseInt(view.getDiceField().getText()));

        view.createGridBoard(board);
        view.createTitleBox();
        view.createRulesColumn();
        view.createStartButton();
        view.createMainMenuButton();

        view.createInfoColumn(boardGame.getPlayerHolder());

        view.getInfoColumn().getChildren().add(view.getStartRoundButton());
        view.getRulesColumn().getChildren().add(view.getRestartGameButton());
        view.getRulesColumn().getChildren().add(view.getMainMenuButton());

        view.createMainLayout(view.getGrid(),view.getTitleBox(),view.getRulesColumn(),view.getInfoColumn());

        for(Player p : boardGame.getPlayerHolder().getPlayers()){
            p.placeOnTile(board,1);
            addPlayerImageToNewTile(p, 1);
        }
    }

    private void resetMainMenu() {
        boardGame.undoWinner(boardGame.getWinner());
        view.getStartRoundButton().setDisable(false);
        view.getInfoColumn().getChildren().clear();
        view.getLayout().getChildren().clear();
        view.getDieBox().getChildren().clear();

        view.getDiceField().clear();
        listOfPlayers.clear();
        view.setPlayerColorBox();

        boardGame = new BoardGame();
        board = null;

        view.getLayout().getChildren().clear();

        view.createMainMenu();

        attachEventHandlers();
    }

    private void handleStartRound() {
        view.getDisplayInfoBox().getChildren().clear();

        if(view.getRestartGameButton().isDisabled()){
            view.getRestartGameButton().setDisable(false);
        }

        boardGame.play();

        displayDice();

        Player currentPlayer = boardGame.getPlayerHolder().getCurrentPlayer();
        int newTileId = currentPlayer.getCurrentTile().getId();

        addPlayerImageToNewTile(currentPlayer, newTileId);

        checkForWinner();
    }

    public void displayDice(){
        view.getDieBox().getChildren().clear();
        for(Die d : boardGame.getDice().getListOfDice()){
            ImageView die = new ImageView(new Image("/images/dice"+d.getValue()+".png"));
            die.setFitHeight(40);
            die.setFitWidth(40);
            view.getDieBox().getChildren().add(die);
        }
        view.getDisplayInfoBox().getChildren().add(view.getDieBox());
    }

    private void addPlayerImageToNewTile(Player player, int newTileId){
        for(Tile t : board.getTiles()){
            if(t.getId() == newTileId){
                t.getTileBox().getChildren().add(view.getPlayerImage(player));
            }
        }
    }

    private void checkForWinner(){
        if(boardGame.getWinner()!=null){
            String winnerName = boardGame.getWinner().getName();
            String winnerColor = boardGame.getWinner().getColor();

            view.makeWinnerBox(winnerName, winnerColor);
            view.getStartRoundButton().setDisable(true);
            displayWinnerMessage();
        }

    }

    private void displayWinnerMessage(){
        view.getLayout().getChildren().addAll(view.getWinnerBox());
    }

    private void getAlert(String errorMessage){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(errorMessage);
        alert.showAndWait();
    }

}
