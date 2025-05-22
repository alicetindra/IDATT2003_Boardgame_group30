package edu.ntnu.idatt2003.boardgame.Controller;

import edu.ntnu.idatt2003.boardgame.Model.BoardGame;
import edu.ntnu.idatt2003.boardgame.View.MenuView;
import edu.ntnu.idatt2003.boardgame.View.MonopolyView;
import edu.ntnu.idatt2003.boardgame.View.SnakesAndLaddersView;
import java.io.IOException;
import javafx.scene.control.Alert;

/**
 * Controls menu functionality for the game selections and game setup.
 * This class manages user interactions within the menu, allowing players to select a game and
 * configure settings before starting.
 * <p>
 * Games supported:
 * - Monopoly
 * - Snakes and Ladders
 * <p>
 * It interacts with specific game controllers and view components to update
 * UI and start the selected game.
 */
public class MenuController{

  /**
   * The main game logic manager.
   */
  private final BoardGame boardGame;

  /**
   * The menu view for the user interface.
   */
  private final MenuView menuView;
  /**
   * Controller for snakes and ladders game.
   */
  private final SnakesAndLaddersController snakeLadderController;
  /**
   * Controller for monopoly game.
   */
  private final MonopolyController monopolyController;


  /**
   * Initializes the menu controller by setting up views and game controllers.
   *
   * @param menuView The menu view that handles the visual representation of the menu.
   */
  public MenuController(MenuView menuView){
    this.menuView = menuView;
    this.boardGame = new BoardGame();

    //Views
    SnakesAndLaddersView snakeLadderView = new SnakesAndLaddersView();
    MonopolyView monopolyView = new MonopolyView();

    //Controllers
    this.snakeLadderController = new SnakesAndLaddersController(snakeLadderView, menuView, boardGame);
    this.monopolyController = new MonopolyController(monopolyView, menuView, boardGame);


    menuView.initialize();
    attachEventHandlers();

  }

  /**
   * This method handles the actions of the buttons in the menu. Set on action for each occurring button.
   */
  private void attachEventHandlers(){

    //Add players
    menuView.getAddPlayerButton().setOnAction(e -> handleAddPlayer());

    menuView.getSetUpSnakesLaddersGameButton().setOnAction(e -> {
      if(boardGame.getListOfPlayers().isEmpty() || menuView.getDiceField() == null || menuView.getBoardSizeMenu().getSelectionModel().isEmpty()) {
        getAlert("To start the game, you need the type of game, players, dice and board size!");
        throw new IllegalArgumentException("Players, board size, game and/or dice is not added");
      }
      try {
        snakeLadderController.setUpSnakesLaddersGame();
      } catch (IOException ex) {
        throw new RuntimeException(ex);
      }
    });

    menuView.getSetUpMonopolyGameButton().setOnAction(e -> {
      if(boardGame.getListOfPlayers().size()<2 || menuView.getDiceField() == null) {
        getAlert("To start the game, you need at least 2 players.");
        throw new IllegalArgumentException("Not enough players added");
      }
      try {
            monopolyController.setUpMonopolyGame();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    });

    //Back to main menu button
    menuView.getMainMenuButton().setOnAction(e -> clearGames());

    //Radio buttons to choose game
    menuView.getSLButton().setOnAction(
        e-> menuView.createSLMenu()
    );
    menuView.getMButton().setOnAction(
            e-> menuView.createMMenu()
    );
    //Buttons for amount of dice
    menuView.getPlusOneButton().setOnAction(
        e-> updateDice(1)
    );
    menuView.getMinusOneButton().setOnAction(
        e-> updateDice(-1)
    );
    //Custom board button
    menuView.getLoadCustomBoardButton().setOnAction(e->snakeLadderController.loadBoard());

  }

  /**
   * This method refers to the clear game methods in each game controller. Which clears all components of the game when returning to main menu.
   */
  private void clearGames() {
    snakeLadderController.clearGame();
    monopolyController.clearGame();
  }

  //Handlers
  /**
   * Gets player-name and player-color inputs from the user and adds them to listOfPlayers.
   * Clears the TextField and removes the color from the color-menu.
   * Gives the user an errormessage if the color and/or name field is empty.
   *
   * @throws RuntimeException if the player name or color is not selected.
   */
  private void handleAddPlayer() throws RuntimeException {
    String selectedColor = menuView.getPlayerColorMenu().getSelectionModel().getSelectedItem();
    String writtenName = menuView.getPlayerName().getText();
    if(selectedColor == null || writtenName.isEmpty()) {
      getAlert("Please select a name and color for the player");
      throw new RuntimeException("Color or name is not selected");
    }

    boardGame.addPlayer(writtenName,selectedColor);

    for (int i = 0; i < menuView.getPlayerData().size(); i++) {
      if (menuView.getPlayerData().get(i).isBlank()) { // Find first empty slot
        menuView.getPlayerData().set(i, writtenName + " - " + selectedColor);
        break;
      }
    }

    menuView.getPlayerName().clear();
    menuView.getPlayerColorMenu().getItems().remove(selectedColor);

    if (!menuView.getPlayerColorMenu().getItems().isEmpty()) {
      menuView.getPlayerColorMenu().getSelectionModel().select(0);
    }
  }

  /**
   * Updates the dice value by the specified increment.
   *
   * <p>The method retrieves the current dice value from the text field,
   * adds the given increment, and ensures that the resulting value stays
   * within the valid range (1 to 6). If the new value is out of bounds,
   * an error alert is displayed, and an {@code IllegalArgumentException} is thrown.
   * Otherwise, the updated dice value is set in the text field, and the dice placement
   * is updated accordingly.
   *
   * @param i the amount to increase or decrease the dice value
   * @throws IllegalArgumentException if the resulting dice value is less than 1 or greater than 6
   */
  public void updateDice(int i){
    int u = Integer.parseInt(menuView.getDiceField().getText())+i;
    if(u<1 || u>6){
      getAlert("Number of dice must be between 1 and 6");
      throw new IllegalArgumentException("Invalid dice value");
    }
    menuView.getDiceField().setText(String.valueOf(u));
    menuView.placeDice();
  }

  /**
   * Displays an error alert dialog with the given error message.
   *
   * <p>This method creates an {@code Alert} of type {@code ERROR},
   * sets its title to "Error", removes the header text, and sets
   * the content to the specified error message. The alert waits for
   * user acknowledgment before closing.
   *
   * @param errorMessage the error message to be displayed in the alert
   */
  private void getAlert(String errorMessage){
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Error");
    alert.setHeaderText(null);
    alert.setContentText(errorMessage);
    alert.showAndWait();
  }


}
