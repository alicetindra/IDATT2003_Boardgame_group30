package edu.ntnu.idatt2003.boardgame.Controller;

import edu.ntnu.idatt2003.boardgame.Model.*;
import edu.ntnu.idatt2003.boardgame.Observer.BoardGameObserver;
import edu.ntnu.idatt2003.boardgame.View.BoardGameView;
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

public class GameController implements BoardGameObserver {
    private BoardGameView view;
    private List<String> listOfPlayers = new ArrayList<>();
    private BoardGame boardGame;
    private Board board;
    private Stage primaryStage;

    private Board customBoard;

    public GameController(BoardGameView view) {
        this.view = view;
        this.boardGame = new BoardGame();
        view.initialize();
        attachEventHandlers();
    }

    @Override
    public void update(String event, BoardGame boardGame){
        switch (event) {
            case "playerMoved" -> {
                view.getDisplayInfoBox().getChildren().clear();
                displayDice();
                displayInfoBox();
                System.out.println("notified");

                Player currentPlayer = boardGame.getPlayerHolder().getCurrentPlayer();
                int newTileId = currentPlayer.getCurrentTile().getId();

                addPlayerImageToNewTile(currentPlayer, newTileId);
            }
            case "winnerDeclared" -> {
                Player winner = boardGame.getWinner();
                view.makeWinnerBox(winner.getName(), winner.getColor());
                view.getStartRoundButton().setDisable(true);
                view.getRootLayout().getChildren().addAll(view.getWinnerBox());
                view.playConfettiEffect();
            }
        }
    }
    /**
     * Attaching actions to the buttons made in BoardGameView.java
     */
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

        view.getRestartGameButton().setOnAction(e ->handleRestartGame());

        view.getSLButton().setOnAction(
               e-> view.createSLMenu()
        );
        view.getCustomRadioButton().setOnAction(
                e-> view.createCostumMenu()
        );
        view.getPlusOneButton().setOnAction(
                e-> updateDice(1)
        );
        view.getMinusOneButton().setOnAction(
                e-> updateDice(-1)
        );
        view.getLoadCustomBoardButton().setOnAction(e->loadBoard());

    }

    /**
     * Removes the WinnerBox and the winner of the previous game, disables the restart button, calls handleMakeGame();
     */
    private void handleRestartGame() {
        view.getLayout().getChildren().remove(view.getWinnerBox());
        view.getDisplayInfoBox().getChildren().clear();
        boardGame.undoWinner(boardGame.getWinner());
        view.getStartRoundButton().setDisable(false);
        try {
            handleMakeGame();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void loadBoard() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Custom Board JSON File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON files", "*.json"));
        File file = fileChooser.showOpenDialog(primaryStage);

        if (file != null) {
            boardGame.loadCustomBoard(file.getPath());
        } else {
            System.out.println("User cancelled file selection.");
        }
        customBoard = boardGame.getBoard();
        System.out.println(customBoard.getTiles().size());
    }
    public void updateDice(int i){
        int u = Integer.parseInt(view.getDiceField().getText())+i;
        if(u<1 || u>6){
            getAlert("Nr of dice must be between 1 and 6");
            throw new IllegalArgumentException("Invalid dice value");
        }
        view.getDiceField().setText(String.valueOf(u));
        view.placeDice();
    }


    /**
     * Gets player-name and player-color inputs from the user and adds them to listOfPlayers.
     * Clears the TextField and removes the color from the color-menu.
     * Gives the user an errormessage if the color and/or name field is empty.
     * @throws RuntimeException
     */
    private void handleAddPlayer() throws RuntimeException {
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

    /**
     *
     * @throws IOException
     */
    private void handleMakeGame() throws IOException {
        this.boardGame.removeObserver(this);
        this.boardGame.addObserver(this);
        view.getLayout().getChildren().clear();
        view.getDisplayInfoBox().getChildren().clear();
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

        if (!view.getCustomRadioButton().isSelected()) {
            boardGame.initializeBoard(view.getGameName(), view.getBoardSizeMenu().getValue(), "src/main/resources/hardcodedBoards.json");
        }

        board = boardGame.getBoard();


        boardGame.initializeDice(Integer.parseInt(view.getDiceField().getText()));

        //Create BoardGrid
        view.createGridBoard(board);
        GridPane boardGrid = view.getGrid();
        boardGrid.setAlignment(Pos.CENTER);

        view.createTitleBox();
        view.createRulesColumn();
        view.createStartButton();
        view.createMainMenuButton();

        view.createInfoColumn(boardGame.getPlayerHolder());

        view.getInfoColumn().getChildren().add(view.getStartRoundButton());
        view.getRulesColumn().getChildren().add(view.getRestartGameButton());
        view.getRulesColumn().getChildren().add(view.getMainMenuButton());

        //add to StackPane layout
        BorderPane gameLayout = view.createMainLayout(boardGrid,view.getTitleBox(),view.getRulesColumn(),view.getInfoColumn());

        view.getRootLayout().getChildren().clear();
        view.getRootLayout().getChildren().add(gameLayout);
        StackPane.setAlignment(gameLayout, Pos.CENTER);

        for(Player p : boardGame.getPlayerHolder().getPlayers()){
            p.placeOnTile(board,1);
            addPlayerImageToNewTile(p, 1);
        }
    }

    private void resetMainMenu() {
        view.getCustomRadioButton().setSelected(false);
        view.getSLButton().setSelected(false);
        boardGame.undoWinner(boardGame.getWinner());
        view.getStartRoundButton().setDisable(false);
        view.getGrid().getChildren().clear();
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
        view.getRestartGameButton().setDisable(false);
        boardGame.play();
        System.out.println("Rolled the dice");

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

    public void displayInfoBox(){
        String message = "Player: "+ boardGame.getPlayerHolder().getCurrentPlayer().getColor() + "\nThrew a: " + boardGame.getDice().getTotalSumOfEyes() + "\nLanded on tile: " + boardGame.getPlayerHolder().getCurrentPlayer().getCurrentTile().getId();

        view.updateInfoBox(message);
    }

    private void addPlayerImageToNewTile(Player player, int newTileId){
        for(Tile t : board.getTiles()){
            if(t.getId() == newTileId){
                t.getTileBox().getChildren().add(view.getPlayerImage(player));
            }
        }
    }

    private void getAlert(String errorMessage){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(errorMessage);
        alert.showAndWait();
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}
