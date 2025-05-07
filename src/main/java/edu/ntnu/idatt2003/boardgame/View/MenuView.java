package edu.ntnu.idatt2003.boardgame.View;

import java.util.Objects;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class MenuView {

  /*Buttons*/
  private final Button addPlayerButton = new Button("Add Player");
  private final Button setUpSnakesLaddersGameButton = new Button("Start game");
  private final Button setUpMonopolyGameButton = new Button("Start game");
  private final Button loadCustomBoardButton = new Button("Load Custom Board");
  private final Button plusOneButton = new Button("+");
  private final Button minusOneButton = new Button("-");
  private final Button mainMenuButton = new Button("Main menu");

  /*Boxes*/
  private final VBox gameInfoMenuBox = new VBox(20);
  private final HBox diceImagesBox = new HBox(10);
  private final VBox boardBox = new VBox(10); //Holds the text 'Board' with the pull down menu in game menu
  HBox startOrMainButtons = new HBox(20);
  VBox diceSection = new VBox(10);

  /*Radio buttons*/
  private final ToggleGroup toggleGroup = new ToggleGroup();
  private final RadioButton SLButton = new RadioButton("Snakes and ladders");
  private final RadioButton MButton = new RadioButton("Monopoly");

  /*TextFields*/
  private final TextField diceField = new TextField();
  private final TextField playerName = new TextField();

  /*ComboBoxes*/
  private final ComboBox<Integer> boardSizeMenu = new ComboBox<>();
  public ComboBox<String> playerColorMenu = new ComboBox<>();

  /*player list*/
  private ObservableList<String> playerData = FXCollections.observableArrayList();
  private ListView<String> playerList;

  /*Layouts */
  private StackPane menuLayout;

  Text titleText;

  /*Font*/
  Font customFont = Font.loadFont(
      Objects.requireNonNull(getClass().getResource("/font/LuckiestGuy-Regular.ttf")).toExternalForm(),15);

  /**
   *
   */
  public void initialize(){
    menuLayout = new StackPane();

    createMainMenu();
    setBoardSizeBox();
    setPlayerColorBox();
    initializePlayerList();
  }

  /*Get buttons*/
  public Button getAddPlayerButton() {
    addPlayerButton.setId("addPlayerButton");
    return addPlayerButton;
  }
  public Button getSetUpSnakesLaddersGameButton() {
    setUpSnakesLaddersGameButton.setId("setUpSnakesLaddersGame");
    return setUpSnakesLaddersGameButton;
  }
  public Button getSetUpMonopolyGameButton() {
    setUpMonopolyGameButton.setId("setUpSnakesLaddersGame");
    return setUpMonopolyGameButton;
  }
  public Button getPlusOneButton() {
    plusOneButton.setId("plusOneButton");
    return plusOneButton;
  }
  public Button getMinusOneButton() {
    plusOneButton.setId("minusOneButton");
    return minusOneButton;
  }
  public Button getLoadCustomBoardButton() {
    loadCustomBoardButton.setId("loadCustomBoardButton");
    return loadCustomBoardButton;
  }

  public RadioButton getSLButton() {
    return SLButton;
  }
  public RadioButton getMButton() {
    return MButton;
  }

  public Button getMainMenuButton(){
    return mainMenuButton;
  }

  /*Get pull down menus*/
  public ComboBox<Integer> getBoardSizeMenu() {
    return boardSizeMenu;
  }
  public ComboBox<String> getPlayerColorMenu() {
    return playerColorMenu;
  }

  /*Get text fields*/
  public TextField getPlayerName() {
    return playerName;
  }
  public TextField getDiceField() {
    return diceField;
  }

  /*Get menu data*/
  public String getGameName(){
    Toggle selectedToggle = toggleGroup.getSelectedToggle();
    RadioButton selectedRadioButton = (RadioButton) selectedToggle;
    return selectedRadioButton.getText();
  }

  public ListView<String> getPlayerList() {
    return playerList;
  }
  public ObservableList<String> getPlayerData(){
    return playerData;
  }

  /*Get layouts*/
  public StackPane getMenuLayout() {
    return menuLayout;
  }

  /*Set pull down menu data*/
  public void setBoardSizeBox(){
    ObservableList<Integer> options = FXCollections.observableArrayList();
    options.addAll(50,90,110);
    boardSizeMenu.setItems(options);
    boardSizeMenu.getSelectionModel().select(1);
    boardSizeMenu.getStyleClass().add("pullDownMenu");
  }

  public void setPlayerColorBox(){
    ObservableList<String> colors = FXCollections.observableArrayList();
    colors.addAll("blue","green","red","yellow","pink","black");
    playerColorMenu.setItems(colors);
    playerColorMenu.getSelectionModel().select(0);
    playerColorMenu.getStyleClass().add("pullDownMenu");
  }

  /*Create methods, different menus*/
  public void createMainMenu(){
    Text titleText = new Text("Choose your game");
    titleText.setFont(customFont);
    titleText.getStyleClass().add("title");
    VBox titleBox = new VBox(20);
    titleBox.setAlignment(Pos.CENTER);
    titleBox.getChildren().addAll(titleText);
    titleBox.setPadding(new Insets(20,0,20,0));

    SLButton.setToggleGroup(toggleGroup);
    SLButton.setId("SLButton");
    MButton.setToggleGroup(toggleGroup);
    MButton.setId("MButton");

    HBox radioButtonBox = new HBox(20);
    radioButtonBox.setId("radioButtonBox");
    radioButtonBox.getChildren().addAll(SLButton, MButton);
    radioButtonBox.setAlignment(Pos.CENTER);

    gameInfoMenuBox.getChildren().clear();
    gameInfoMenuBox.getChildren().addAll(titleBox, radioButtonBox);
    gameInfoMenuBox.setAlignment(Pos.CENTER);
    gameInfoMenuBox.setId("gameInfoMenuBox");

    menuLayout.getChildren().clear();
    menuLayout.getChildren().addAll(gameInfoMenuBox);
    menuLayout.getStyleClass().add("rootMainMenu");
  }
  public VBox getDiceSection(){
    return diceSection;
  }

  public void createSLMenu(){
    diceField.setText("2");
    Text boardText = new Text("Board size");
    boardText.setId("boardSizeText");
    loadCustomBoardButton.setId("loadCustomBoardButton");

    Text diceText = new Text("Dice");
    diceText.setId("diceText");
    minusOneButton.setId("minusOneButton");
    plusOneButton.setId("plusOneButton");
    HBox diceHBox = new HBox(10);
    diceHBox.getChildren().addAll(minusOneButton,diceField,plusOneButton);
    diceSection.getChildren().addAll(diceText, diceHBox);

    titleText = new Text("Snakes and ladders");

    boardBox.getChildren().clear();
    boardBox.getChildren().addAll(boardText, boardSizeMenu, loadCustomBoardButton);

    startOrMainButtons.getChildren().clear();
    startOrMainButtons.getChildren().addAll(mainMenuButton, setUpSnakesLaddersGameButton);
    setUpSnakesLaddersGameButton.setFont(customFont);
    createGameMenu();
  }
  public void createMMenu() {
    diceField.setText("1");
    boardBox.getChildren().clear();
    startOrMainButtons.getChildren().clear();
    titleText = new Text("Monopoly");


    Text diceText = new Text("Dice");
    diceText.setId("diceText");
    HBox diceHBox = new HBox(10);
    diceHBox.getChildren().add(diceField);
    diceSection.getChildren().addAll(diceText, diceHBox);

    startOrMainButtons.getChildren().addAll(mainMenuButton, setUpMonopolyGameButton);
    setUpMonopolyGameButton.setFont(customFont);
    createGameMenu();
  }

  public void createGameMenu(){
    gameInfoMenuBox.getChildren().clear();
    diceField.setDisable(true);

    //Title
    titleText.setFont(customFont);
    titleText.getStyleClass().add("title");
    VBox titleBox = new VBox(20);
    titleBox.setAlignment(Pos.CENTER);
    titleBox.getChildren().addAll(titleText);
    titleBox.setPadding(new Insets(20,0,20,0));

    //Left column
    VBox leftColumn = new VBox(15);
    leftColumn.setAlignment(Pos.TOP_LEFT);


    //Player name section
    Text nameText = new Text("Player name");
    nameText.setId("nameText");
    playerName.setMinWidth(120);
    VBox playerNameSection = new VBox(10);
    playerNameSection.getChildren().addAll(nameText, playerName);

    //Player color section
    Text colorText = new Text("Player color");
    colorText.setId("colorText");
    playerColorMenu.setMinWidth(120);
    VBox playerColorSection = new VBox(10);
    playerColorSection.getChildren().addAll(colorText, playerColorMenu);

    //Add addPlayerButton to left column
    addPlayerButton.setMinWidth(150);
    addPlayerButton.setFont(customFont);
    addPlayerButton.setId("addPlayerButton");
    leftColumn.getChildren().addAll(boardBox, diceSection, playerNameSection, playerColorSection, addPlayerButton);

    //Right column player list + dice images
    VBox rightColumn = new VBox(15);
    rightColumn.setPrefWidth(300);
    rightColumn.setAlignment(Pos.TOP_RIGHT);

    //Player list section
    Text playerTitle = new Text("Players");
    playerTitle.setFont(customFont);
    playerTitle.getStyleClass().add("subTitle");

    //Insert list
    ListView<String> playerList = createPlayerList();

    // Dice Images Section
    Text diceImagesTitle = new Text("Dice");
    diceImagesTitle.setFont(customFont);
    diceImagesTitle.getStyleClass().add("subTitle");
    diceImagesBox.setAlignment(Pos.CENTER);
    placeDice();

    //right column
    rightColumn.setAlignment(Pos.TOP_CENTER);
    rightColumn.setSpacing(15);
    rightColumn.getChildren().addAll(playerTitle, playerList, diceImagesTitle, diceImagesBox);

    HBox mainLayout = new HBox(100);
    mainLayout.setAlignment(Pos.CENTER);
    mainLayout.getChildren().addAll(leftColumn, rightColumn);

    startOrMainButtons.setAlignment(Pos.CENTER);

    mainMenuButton.setFont(customFont);
    mainMenuButton.setId("menuButtonBack");

    //Apply layout
    gameInfoMenuBox.getChildren().clear();
    gameInfoMenuBox.getChildren().addAll(titleBox, mainLayout, startOrMainButtons);

    menuLayout.getChildren().clear();
    menuLayout.getChildren().add(gameInfoMenuBox);
    StackPane.setAlignment(gameInfoMenuBox, Pos.CENTER);
  }


  public void placeDice(){
    diceImagesBox.getChildren().clear();
    for(int i = 1; i<= Integer.parseInt(diceField.getText()); i++){
      ImageView diceImageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/dice"+i+".png")).toExternalForm()));
      diceImageView.setFitHeight(40);
      diceImageView.setPreserveRatio(true);
      diceImagesBox.getChildren().add(diceImageView);
    }
  }

  public void createMainMenuButton(){
    mainMenuButton.setFont(customFont);
    mainMenuButton.setId("main-menu-button");

    VBox.setMargin(mainMenuButton, new Insets(20,0,0,0));

  }

  /* Initialize and create player list fpr the menu */
  private void initializePlayerList(){
    playerList = createPlayerList();
  }

  private ListView<String> createPlayerList(){
    ListView<String> playerList = new ListView<>(playerData);
    playerList.setId("playerList");

    playerList.setFixedCellSize(30);
    playerList.setPrefHeight(6*30+3);

    //Create list with 6 empty rows
    for (int i = 0; i < 6; i++){
      playerData.add("");
    }

    //Alternative row different color
    playerList.setCellFactory(lv -> new ListCell<>() {
      @Override
      protected void updateItem(String player, boolean empty) {
        super.updateItem(player, empty);
        if (empty || player == null) {
          setText(null);
          setStyle(""); //
        } else {
          setText(player.isBlank() ? "" : player);

          // alternating row different color
          if (getIndex() % 2 == 0) {
            setStyle("-fx-background-color: rgba(176,197,221,0.75);");
          } else {
            setStyle("-fx-background-color: rgba(255,255,255,0.7);");
          }
        }
      }
    });

    return playerList;
  }

  public void playConfettiEffect() {
    // Create a group for confetti
    Group confettiGroup = new Group();

    // Add confetti to menuLayout
    menuLayout.getChildren().add(confettiGroup);

    for (int i = 0; i < 100; i++) {
      // Create each confetti rectangle
      Rectangle confetti = new Rectangle(5, 15);
      confetti.setFill(Color.color(Math.random(), Math.random(), Math.random()));
      confetti.setX(Math.random() * 1000); //
      confetti.setY(-50); //

      confettiGroup.getChildren().add(confetti);

      // Animation for each confetti piece
      TranslateTransition transition = new TranslateTransition();
      transition.setNode(confetti);
      transition.setDuration(Duration.seconds(3 + Math.random() * 2));
      transition.setByY(1500);

      // fade out animation
      FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2), confetti);
      fadeTransition.setFromValue(1.0);
      fadeTransition.setToValue(0.0);

      // Run both animations above and finish before the lay in a line
      ParallelTransition parallelTransition = new ParallelTransition(transition, fadeTransition);
      parallelTransition.setCycleCount(1);
      parallelTransition.play();

    }
    // Remove confetti after animation is done
    new Timeline(
        new KeyFrame(Duration.seconds(5), e -> menuLayout.getChildren().remove(confettiGroup))
    ).play();
  }



}
