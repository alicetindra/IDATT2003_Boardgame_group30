package edu.ntnu.idatt2003.boardgame.Controller;

import edu.ntnu.idatt2003.boardgame.Model.Board;
import edu.ntnu.idatt2003.boardgame.Model.BoardGame;
import edu.ntnu.idatt2003.boardgame.Model.Player;
import edu.ntnu.idatt2003.boardgame.Model.PlayerHolder;
import edu.ntnu.idatt2003.boardgame.Model.Tile;
import edu.ntnu.idatt2003.boardgame.View.BoardView;
import edu.ntnu.idatt2003.boardgame.View.MainMenuView;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class MainMenuController {
  private final MainMenuView view;
  private final StackPane rootLayout;
  private final List<String> stringOfPlayers;
    private final BoardController boardController;


  public MainMenuController(StackPane rootLayout) {
    this.rootLayout = rootLayout;
    this.stringOfPlayers = new ArrayList<>();
      BoardGame boardGame = new BoardGame();
    view = new MainMenuView();

    this.boardController = new BoardController(boardGame, this);

    setupButtonActions();
    setupToggleBoxBehavior();

    rootLayout.getChildren().add(view.getMenuBox());
  }

  private void setupButtonActions() {
    view.getAddPlayerButton().setOnAction(this::addPlayer);
    view.getSnakesAndLaddersButton().setOnAction(e -> boardController.handleSnakesAndLadders());
    view.getCandyLandButton().setOnAction(e -> startCandyLand());
  }

  public void returnToMainMenu() {
    System.out.println("Returning to Main Menu...");

    rootLayout.getChildren().clear();
    VBox mainMenu = view.getMenuBox();
    rootLayout.getChildren().add(mainMenu);
    StackPane.setAlignment(mainMenu, Pos.CENTER);

  }

  private void setupToggleBoxBehavior() {
    view.getToggleGroup().selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
      view.getMenuBox().getChildren().clear(); // Clear the menuBox
      if (newValue == view.getToggleGroup().getToggles().getFirst()) {
        // Snakes and Ladders selected
        view.getMenuBox().getChildren().addAll(
            view.getHeader(),
            view.getPlayerNameField(),
            view.getComboBoxColor(),
            view.getAddPlayerButton(),
            view.getComboBoxBoard(),
            view.getIntegerField(),
            view.getSnakesAndLaddersButton()
        );
      }else {
        // CandyLand selected
        view.getMenuBox().getChildren().addAll(
            view.getPlayerNameField(),
            view.getComboBoxColor(),
            view.getAddPlayerButton(),
            view.getComboBoxBoard(),
            view.getIntegerField(),
            view.getCandyLandButton()
        );
      }
    });

  }

  private void addPlayer(ActionEvent e) {
    String playerName = view.getPlayerNameField().getText();
    String selectedColor = view.getComboBoxColor().getSelectionModel().getSelectedItem();

    if(playerName.isEmpty() || selectedColor.isEmpty()) {
      throw new IllegalArgumentException("Name and color cannot be empty");
    }

    stringOfPlayers.add(playerName + "," + selectedColor);

    view.getPlayerNameField().clear();
    view.getComboBoxColor().getItems().remove(selectedColor);

  }

  public List<String> getStringOfPlayers() {
    return stringOfPlayers;
  }

  public StackPane getRootLayout() {
    return rootLayout;
  }

  private void startSnakesAndLadders() {

  }





  private void startCandyLand() {
    System.out.println("Starting Candy Land...");
    // Add logic for starting the game
  }
}