package edu.ntnu.idatt2003.boardgame.Controller;

import edu.ntnu.idatt2003.boardgame.Model.BoardGame;
import edu.ntnu.idatt2003.boardgame.View.MenuView;
import edu.ntnu.idatt2003.boardgame.View.MonopolyView;
import edu.ntnu.idatt2003.boardgame.View.SnakesAndLaddersView;
import java.io.IOException;
import javafx.scene.control.Alert;

public class MenuController{

  private BoardGame boardGame;

  private final MenuView menuView;
  private final SnakesAndLaddersController snakeLadderController;
  private MonopolyController monopolyController;


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

  private void attachEventHandlers(){

    //Add players
    menuView.getAddPlayerButton().setOnAction(e -> handleAddPlayer());

    //Start snakes and ladders by setting up the board and layout
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
      if(boardGame.getListOfPlayers().isEmpty() || menuView.getDiceField() == null) {
        getAlert("To start the game, you need players and dice.");
        throw new IllegalArgumentException("Players and/or dice is not added");
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
    menuView.getPlusOneButton().setOnAction(
        e-> updateDice(1)
    );
    menuView.getMinusOneButton().setOnAction(
        e-> updateDice(-1)
    );
    menuView.getLoadCustomBoardButton().setOnAction(e->snakeLadderController.loadBoard());

  }

  private void clearGames() {
    snakeLadderController.clearGame();
    monopolyController.clearGame();
  }

  //Handlers
  /**
   * Gets player-name and player-color inputs from the user and adds them to listOfPlayers.
   * Clears the TextField and removes the color from the color-menu.
   * Gives the user an errormessage if the color and/or name field is empty.
   * @throws RuntimeException
   */
  private void handleAddPlayer() throws RuntimeException {
    String selectedColor = menuView.getPlayerColorMenu().getSelectionModel().getSelectedItem();
    String writtenName = menuView.getPlayerName().getText();
    if(selectedColor == null || writtenName.isEmpty()) {
      getAlert("Please select a name and color for the player");
      throw new RuntimeException("Color or name is not selected");
    }

    boardGame.addPlayer(writtenName,selectedColor);

    System.out.println("Added player " + writtenName);
    System.out.println(boardGame.getListOfPlayers());

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

  public void updateDice(int i){
    int u = Integer.parseInt(menuView.getDiceField().getText())+i;
    if(u<1 || u>6){
      getAlert("Number of dice must be between 1 and 6");
      throw new IllegalArgumentException("Invalid dice value");
    }
    menuView.getDiceField().setText(String.valueOf(u));
    menuView.placeDice();
  }


  //Alerts
  private void getAlert(String errorMessage){
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Error");
    alert.setHeaderText(null);
    alert.setContentText(errorMessage);
    alert.showAndWait();
  }


}
