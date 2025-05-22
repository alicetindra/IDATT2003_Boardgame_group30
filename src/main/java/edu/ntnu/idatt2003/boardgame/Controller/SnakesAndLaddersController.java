package edu.ntnu.idatt2003.boardgame.Controller;

import edu.ntnu.idatt2003.boardgame.Model.*;
import edu.ntnu.idatt2003.boardgame.Observer.BoardGameObserver;
import edu.ntnu.idatt2003.boardgame.View.MenuView;
import edu.ntnu.idatt2003.boardgame.View.SnakesAndLaddersView;
import java.util.logging.Logger;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.IOException;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Controls the flow and interactions of the Snakes and Ladders game.
 *
 *  <p>This class acts as the main controller for the Snakes and Ladders  game, managing
 *  user inputs, game state updates, and interactions between the view and model.
 *  It listens for game state changes and ensures updates are properly reflected
 *  in the UI. Implements {@code BoardGameObserver} to track and respond to
 *  changes in the game state.
 */
public class SnakesAndLaddersController implements BoardGameObserver {
    /**
     * Logger for tracking events and debugging information within the controller.
     */
    private static final Logger log = Logger.getLogger(SnakesAndLaddersController.class.getName());
    /**
     * The view component responsible for rendering the Snakes and Ladders UI.
     */
    private final SnakesAndLaddersView snakesLaddersView;
    /**
     * The view component responsible for handling the main menu UI and user inputs.
     */
    private final MenuView menuView;
    /**
     * The main game logic handler, managing game state and player actions.
     */
    private final BoardGame boardGame;
    /**
     * The game board, which contains the tiles and layout of the game.
     */
    private Board board;
    /**
     * The primary JavaFX stage used to display the application's GUI.
     */
    private Stage primaryStage;

    /**
     * Constructs a new {@code SnakesAndLaddersController} with the specified view components and game model.
     * <p>
     * This constructor initializes the Snakes and Ladders view, stores references to the views and model,
     * and attaches the necessary event handlers for user interaction.
     *
     * @param snakesLaddersView the view component responsible for rendering the Snakes and Ladders UI
     * @param menuView          the view component for handling the main menu and user inputs
     * @param boardGame         the model containing the game logic and state
     */
    public SnakesAndLaddersController(SnakesAndLaddersView snakesLaddersView, MenuView menuView, BoardGame boardGame) {
        this.snakesLaddersView = snakesLaddersView;
        this.menuView = menuView;
        this.boardGame = boardGame;
        snakesLaddersView.initialize();
        attachEventHandlers();
    }

    /**
     * Updates the game state based on the specified event.
     *
     * <p>This method is triggered whenever the game state changes, responding to player movement or winning the game.
     * UI elements are dynamically updated based on the event, ensuring the correct visuals
     * and game flow are maintained.
     *
     * <p>Recognized event types:
     * <ul>
     *   <li>{@code "playerMoved"} - Updates the player's position on the board, processes fees, and adjusts buttons.</li>
     *   <li>{@code "winnerDeclared"} - Displays the winning player's details and disables gameplay and plays confetti.</li>
     * </ul>
     *
     * @param event the game event that has occurred
     * @param boardGame the game instance containing relevant player and board information
     */
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

    /**
     * Loads the custom board if user chooses this option.
     */
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

    /**
     * Displays images of the dice in the player's roll.
     */
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

    /**
     * Displays information about the round, what the current player rolled and which tile they landed on.
     */
    public void displayInfoBox(){
        String message;
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

    /**
     * Adds player image to the new tile on the board.
     *
     * @param player the current player whose image will appear on the board.
     * @param newTileId the new tile on which the image will appear.
     */
    private void addPlayerImageToNewTile(Player player, int newTileId){
        for(Tile t : board.getTiles()){
            if(t.getId() == newTileId){
                t.getTileBox().getChildren().add(snakesLaddersView.getPlayerImage(player));
            }
        }
    }

    /**
     * Initializes and sets up the Snakes and Ladders game environment.
     * <p>
     * This method performs the following operations:
     * <ul>
     *   <li>Resets the observer for the board game to ensure proper UI updates</li>
     *   <li>Clears and prepares UI components in the Snakes and Ladders view</li>
     *   <li>Loads player data from a CSV file and sets up player images</li>
     *   <li>Initializes the board if a custom board has not been loaded</li>
     *   <li>Creates and aligns the game grid and associated UI components (rules, title, info columns)</li>
     *   <li>Initializes the dice based on user input</li>
     *   <li>Builds the full game layout and inserts it into the main menu layout</li>
     *   <li>Places all players on the starting tile and adds their images to the board</li>
     * </ul>
     *
     * @throws IOException if there is an issue reading the player data from the file
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

    /**
     * Starts round for the current player in monopoly. Calls for the play method in the BoardGame class.
     */
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

    /**
     * Clears the game snakes and ladders game and all it's components. Removes winner. Clears board. Disable and removes buttons. And Create the main menu.
     */
    public void clearGame(){
        boardGame.undoWinner(boardGame.getWinner());
        boardGame.clearBoard();
        boardGame.undoCustomBoardLoad();

        snakesLaddersView.getStartRoundButton().setDisable(false);
        snakesLaddersView.getGrid().getChildren().clear();
        snakesLaddersView.getInfoColumn().getChildren().clear();
        snakesLaddersView.getLayout().getChildren().clear();
        snakesLaddersView.getDieBox().getChildren().clear();
        snakesLaddersView.getLayout().getChildren().clear();

        menuView.getSLButton().setSelected(false);
        menuView.getPlayerData().clear();
        menuView.setPlayerColorBox();
        menuView.getDiceField().clear();
        menuView.getBoardSizeMenu().setDisable(false);
        menuView.createMainMenu();
    }


}
