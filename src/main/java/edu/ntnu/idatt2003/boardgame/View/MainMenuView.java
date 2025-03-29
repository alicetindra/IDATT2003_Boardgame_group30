package edu.ntnu.idatt2003.boardgame.View;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.function.UnaryOperator;

public class MainMenuView {
  private final VBox menuBox;
  private final ToggleGroup toggleGroup;
  private final TextField playerNameField = new TextField();
  private final ComboBox<String> comboBoxColor = new ComboBox<>();
  private final ComboBox<Integer> comboBoxBoard = new ComboBox<>();
  private final TextField integerField = new TextField();
  private final Button snakesAndLaddersButton = new Button("Snakes and Ladders");
  private final Button candyLandButton = new Button("Candy Land");
  private final Button addPlayerButton = new Button("Add Player");

  public MainMenuView() {
    menuBox = new VBox();
    menuBox.setSpacing(15);
    menuBox.setAlignment(Pos.CENTER);

    toggleGroup = new ToggleGroup();
    RadioButton radioSnakesLadders = new RadioButton("Snakes and Ladders");
    RadioButton radioCandy = new RadioButton("CandyLand");
    radioSnakesLadders.setToggleGroup(toggleGroup);
    radioCandy.setToggleGroup(toggleGroup);

    menuBox.getChildren().addAll(radioSnakesLadders, radioCandy);

    setupMenuInteractions(radioSnakesLadders, radioCandy);
  }

  private void setupMenuInteractions(RadioButton radioSnakesLadders, RadioButton radioCandy) {
    toggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
      menuBox.getChildren().clear();
      if (newValue == radioSnakesLadders) {
        menuBox.getChildren().add(snakesAndLaddersButton);
      } else {
        menuBox.getChildren().add(candyLandButton);
      }
    });
  }

  public VBox getMenuBox() {
    return menuBox;
  }

  public ToggleGroup getToggleGroup() {
    return toggleGroup;
  }

  public Text getHeader() {
    Text header = new Text("Add a Player");
    header.setId("header");
    return header;
  }

  public TextField getPlayerNameField() {
    playerNameField.setMaxWidth(150);
    playerNameField.setPromptText("Player Name");
    return playerNameField;
  }

  public ComboBox<String> getComboBoxColor() {
    ObservableList<String> colors = FXCollections.observableArrayList("green", "red", "blue", "yellow", "pink", "black");
    comboBoxColor.setItems(colors);
    comboBoxColor.getSelectionModel().select(0);
    return comboBoxColor;
  }

  public ComboBox<Integer> getComboBoxBoard() {
    ObservableList<Integer> options = FXCollections.observableArrayList(50, 90, 110);
    comboBoxBoard.setItems(options);
    comboBoxBoard.getSelectionModel().select(1);
    return comboBoxBoard;
  }

  public TextField getIntegerField() {
    UnaryOperator<TextFormatter.Change> filter = change -> {
      String newText = change.getControlNewText();
      if (newText.matches("[1-6]?")) {
        return change;
      }
      return null;
    };
    integerField.setTextFormatter(new TextFormatter<>(filter));
    integerField.setMaxWidth(60);
    integerField.setPromptText("2");
    return integerField;
  }

  public int getIntegerValue(){
    String text = integerField.getText();
    try{
      return Integer.parseInt(text);
    }catch(NumberFormatException e){
      System.out.println("Invalid input, defaulting to 2.");
      return 2; // Default value if parsing fails
    }
  }

  public Button getSnakesAndLaddersButton() {
    return snakesAndLaddersButton;
  }

  public Button getCandyLandButton() {
    return candyLandButton;
  }

  public Button getAddPlayerButton() {
    return addPlayerButton;
  }
}