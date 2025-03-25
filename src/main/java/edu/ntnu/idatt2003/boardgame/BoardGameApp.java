package edu.ntnu.idatt2003.boardgame;

import edu.ntnu.idatt2003.boardgame.actions.LadderAction;
import edu.ntnu.idatt2003.boardgame.actions.PortalAction;
import edu.ntnu.idatt2003.boardgame.actions.SnakeAction;
import edu.ntnu.idatt2003.boardgame.componentHolders.Board;
import edu.ntnu.idatt2003.boardgame.componentHolders.BoardGame;
import edu.ntnu.idatt2003.boardgame.componentHolders.Dice;
import edu.ntnu.idatt2003.boardgame.componentHolders.PlayerHolder;
import edu.ntnu.idatt2003.boardgame.components.Die;
import edu.ntnu.idatt2003.boardgame.components.Player;
import edu.ntnu.idatt2003.boardgame.components.Tile;
import java.util.HashMap;
import java.util.Map;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javafx.util.Duration;


public class BoardGameApp extends Application {
  private StackPane rootLayout;
  Board board;
  PlayerHolder playerHolder;
  Dice dice;
  BoardGame game = new BoardGame();
  Player currentPlayer;
  VBox displayInfoBox = new VBox();
  HBox rowBox;
  Button startRoundButton;
  HBox dieBox = new HBox();
  int rowBoxesHeigth;

  public static void main(String[] args){
    launch(args);
  }

  @Override
  public void start(Stage stage){
    //huvudlayout
    rootLayout = new StackPane();

    //visa huvudmeny
    showMainMenu();

    //Skapa scenen
    Scene menuScene = new Scene(rootLayout, 1000, 650);
    stage.setScene(menuScene);
    stage.setTitle("Board Game");

    //maximera fönstret
    stage.setMaximized(true);

    stage.show();
  }

  /**
   * displays main menu
   */
  private void showMainMenu(){
    VBox menuBox = new VBox();
    menuBox.setSpacing(15);
    menuBox.setAlignment(Pos.CENTER);

    Button snakesAndLAddersButton = new Button("Snakes and Ladders");
    snakesAndLAddersButton.setOnAction(e-> startSnakesAndLAdders());

    menuBox.getChildren().add(snakesAndLAddersButton);

    rootLayout.getChildren().clear();
    rootLayout.getChildren().add(menuBox);
    StackPane.setAlignment(menuBox, Pos.CENTER);
  }

  /**
   * displays snakes and ladders game
   */
  public void startSnakesAndLAdders(){
    try{
      //Initialize game components
      initializeGameSL();

      //Set up board grid
      VBox boardGrid = createBoardGrid();
      boardGrid.setAlignment(Pos.CENTER);
      boardGrid.setMaxWidth(750);
      boardGrid.setMinWidth(750);
      boardGrid.setMaxHeight(rowBoxesHeigth);
      boardGrid.setMinHeight(rowBoxesHeigth);

      //Add players to the board
      initializePlayers();

      //Set up UI components
      HBox titleWithImage = createTitle();
      VBox rulesColumn = createRulesColumn();
      VBox infoColumn = createInfoColumn();
      Button startRoundButton = createStartButton();
      Button mainMenuButton = createMainMenuButton();

      infoColumn.getChildren().add(startRoundButton);
      rulesColumn.getChildren().add(mainMenuButton);

      //Set up layout
      BorderPane gameLayout = createMainLayout(boardGrid, titleWithImage, rulesColumn, infoColumn);

      //Configure and show stage
      rootLayout.getChildren().clear();
      rootLayout.getChildren().add(gameLayout);
      StackPane.setAlignment(gameLayout, Pos.CENTER);


    } catch(IOException e){
      e.printStackTrace();
    }
  }


  /**
   * Initialize the game board, dice, and player holder
   * @throws IOException
   */
  private void initializeGameSL() throws IOException{
    game.createBoard(1, "src/main/resources/snakesAndLaddersBoard.json");
    board = game.getBoard();
    VBox boardGrid = createBoardGrid();
    //Initialize dice and players.
    game.createDice(3);
    dice = game.getDice();

    List<String> stringOfPlayers = new ArrayList<>();
    stringOfPlayers.add("Tindra,green");
    stringOfPlayers.add("Nicoline,blue");
    stringOfPlayers.add("Markus,yellow");
    stringOfPlayers.add("Julie,red");

    game.createPlayerHolder("src/main/resources/players.csv", stringOfPlayers);
    playerHolder = game.getPlayerHolder();
  }

  /**
   * Adds all players to the board and places their images on the starting tile.
   */
  private void initializePlayers(){
    for(Player p: playerHolder.getPlayers()){
      p.setBoardGame(game);
      p.setCurrentTile(board, 1);

      //Add image for all players on tile 1
      for(Tile t : board.getTiles()){
        if(t.getTileId() == 1) {
          // Lägg till bilden i tileBox
          t.getTileBox().getChildren().add(createPlayerImage(p.getColor()));

        }
      }
    }
    playerHolder.setCurrentPlayer(playerHolder.getPlayers().getLast());
    currentPlayer = playerHolder.getCurrentPlayer();
  }

  /**
   * Creates the title bar with images and text for the game.
   * @return
   */
  private HBox createTitle(){
    Font customFontTitle = Font.loadFont(Objects.requireNonNull(getClass().getResource("/font/LuckiestGuy-Regular.ttf")).toExternalForm(),90);

    //Snake image
    Image snakeImage = new Image(Objects.requireNonNull(getClass().getResource("/images/snake.png")).toExternalForm());
    ImageView snakeImageView = new ImageView(snakeImage);
    snakeImageView.setFitHeight(90);
    snakeImageView.setFitWidth(90);
    snakeImageView.setPreserveRatio(true);

    //Title
    Text title = new Text("Snakes and Ladders");
    title.setFont(customFontTitle);
    title.setStyle("-fx-fill: #19599f");

    //Ladder image
    Image ladderImage = new Image(Objects.requireNonNull(getClass().getResource("/images/ladders.png")).toExternalForm());
    ImageView ladderImageView = new ImageView(ladderImage);
    ladderImageView.setFitHeight(90);
    ladderImageView.setFitWidth(90);
    ladderImageView.setPreserveRatio(true);

    HBox titleWithImage = new HBox(20, snakeImageView, title, ladderImageView);
    titleWithImage.setAlignment(Pos.CENTER);
    titleWithImage.setPadding(new Insets(50,0,0,0));

    return titleWithImage;
  }

  /**
   * Creates the rules column for the game.
   * @return
   */
  private VBox createRulesColumn(){
    Font customFontSubTitle = Font.loadFont(Objects.requireNonNull(getClass().getResource("/font/LuckiestGuy-Regular.ttf")).toExternalForm(),30);

    //Information on rules box to the left
    VBox rulesColumn = new VBox(20);
    rulesColumn.setPrefWidth(300);
    rulesColumn.setStyle(
        "-fx-background-color: #6f9c6f; "
            + "-fx-border-color: #6f9c6f;");
    rulesColumn.setAlignment(Pos.TOP_CENTER);

    rulesColumn.setPadding(new Insets(100,0,100,0));

    Text rulesTitle = new Text("Game Rules");
    rulesTitle.setFont(customFontSubTitle);
    rulesTitle.setStyle("-fx-fill: #19599f");

    Text rule1 = createRuleText("1. Roll the dice to move when it's your turn.");
    Text rule2 = createRuleText("2. Land on dark green to climb, dark red to slide, dark blue to teleport.");
    Text rule3 = createRuleText("3. The first player at the finish is the winner.");

    rulesColumn.getChildren().addAll(rulesTitle, rule1, rule2, rule3);

    Region spacer = new Region();
    VBox.setVgrow(spacer, Priority.ALWAYS); // Låt spacern växa och trycka knappen nedåt
    rulesColumn.getChildren().add(spacer);

    return rulesColumn;
  }

  /**
   * Creates a styled rule text.
   *
   * @param content The text content of the rule.
   * @return A styled Text node.
   */
  private Text createRuleText(String content) {
    Text rule = new Text(content);
    rule.setWrappingWidth(180);
    rule.setStyle("-fx-font-size: 14px; -fx-fill: black; -fx-font-family: Georgia;");
    return rule;
  }

  /**
   *
   * @return
   */
  private VBox createInfoColumn(){
    Font customFontSubTitle = Font.loadFont(Objects.requireNonNull(getClass().getResource("/font/LuckiestGuy-Regular.ttf")).toExternalForm(),30);

    VBox infoColumn = new VBox(10);
    infoColumn.setPrefWidth(300);
    infoColumn.setStyle(
        "-fx-background-color: #6f9c6f; "
            + "-fx-border-color: #6f9c6f;");
    infoColumn.setAlignment(Pos.TOP_CENTER);

    infoColumn.setPadding(new Insets(100,0,200,0));

    Text infoTitle = new Text("Player Info");
    infoTitle.setFont(customFontSubTitle);
    infoTitle.setStyle("-fx-fill: #19599f");

    infoColumn.getChildren().add(infoTitle);

    for(Player p: playerHolder.getPlayers()){
      HBox playerInfoBox = new HBox(10);
      playerInfoBox.setAlignment(Pos.CENTER);

      ImageView playerImage = createPlayerImage(p.getColor());

      Text playerName = new Text(" " + p.getName());
      playerName.setStyle("-fx-font-size: 14px; "
          + "-fx-fill: black;"
          + "-fx-font-family: Georgia");
      playerInfoBox.getChildren().addAll(playerImage, playerName);
      infoColumn.getChildren().add(playerInfoBox);
    }

    //Space down to display box
    Region spacerAboveDisplayBox = new Region();
    VBox.setVgrow(spacerAboveDisplayBox, Priority.ALWAYS); // Flexibelt mellanrum som trycker displayInfoBox nedåt
    infoColumn.getChildren().add(spacerAboveDisplayBox);

    // Lägg till displayInfoBox som innehåller tärningar och meddelanden
    displayInfoBox.setAlignment(Pos.CENTER); // Centrera displayBox-innehållet
    infoColumn.getChildren().add(displayInfoBox);


    return infoColumn;
  }

  /**
   * Creates the "Roll Dice" button.
   * @return
   */
  private Button createStartButton(){
    Font customFontButton = Font.loadFont(Objects.requireNonNull(getClass().getResource("/font/LuckiestGuy-Regular.ttf")).toExternalForm(),15);

    startRoundButton = new Button("Roll Dice");
    startRoundButton.setFont(customFontButton);
    startRoundButton.setStyle(
        "-fx-background-color: #416c42; " +  // Bakgrundsfärg
            "-fx-text-fill: white; " +           // Textfärg
            "-fx-background-radius: 20px; " +    // Rundade hörn
            "-fx-border-color: #053005; " +      // Kantfärg
            "-fx-border-width: 2px;" +
            "-fx-border-radius: 20px" );
    startRoundButton.setPrefSize(150, 50);
    startRoundButton.setOnAction(this::handleStartRoundButton);

    VBox.setMargin(startRoundButton, new Insets(20,0,0,0));
    return startRoundButton;
  }


  /**
   * creates main menu to go back to welcome page
   * @return
   */
  private Button createMainMenuButton(){

    Font customFontButton = Font.loadFont(Objects.requireNonNull(getClass().getResource("/font/LuckiestGuy-Regular.ttf")).toExternalForm(),15);

    //back to main
    Button mainMenuButton = new Button("Main menu");
    mainMenuButton.setFont(customFontButton);
    mainMenuButton.setStyle(
        "-fx-background-color: #416c42; " +  // Bakgrundsfärg
        "-fx-text-fill: white; " +           // Textfärg
        "-fx-background-radius: 20px; " +    // Rundade hörn
        "-fx-border-color: #053005; " +      // Kantfärg
        "-fx-border-width: 2px;" +
        "-fx-border-radius: 20px" );
    mainMenuButton.setPrefSize(150, 50);
    mainMenuButton.setOnAction(e->{restartGame();
      showMainMenu();});

    VBox.setMargin(mainMenuButton, new Insets(20,0,0,0));

    return mainMenuButton;
  }

  /**
   * Restarts game, undo winner and reload the side, is used in displayWinnerMessage
   */
  private void restartGame(){
    //reset winner
    game.undoWinner(game.getWinner());

    startSnakesAndLAdders();
    displayInfoBox.getChildren().clear();
  }

  /**
   * Creates the main layout for the game.
   * @param boardGrid
   * @param titleWithImage
   * @param rulesColumn
   * @param infoColumn
   * @return
   */
  private BorderPane createMainLayout(VBox boardGrid, HBox titleWithImage, VBox rulesColumn, VBox infoColumn){
    BorderPane mainLayout = new BorderPane();
    mainLayout.setTop(titleWithImage);
    mainLayout.setLeft(rulesColumn);
    mainLayout.setCenter(boardGrid);
    mainLayout.setRight(infoColumn);

    BorderPane.setMargin(rulesColumn, new Insets(0,20,0,20));
    BorderPane.setMargin(infoColumn, new Insets(0,20,0,20));

    mainLayout.setStyle("-fx-background-color: #6f9c6f;");
    return mainLayout;
  }




  /**
   *
   * @param actionEvent
   */
  private void handleStartRoundButton(ActionEvent actionEvent) {
    displayInfoBox.getChildren().clear();
    game.play();

    displayDice();

    Player currentPlayer = playerHolder.getCurrentPlayer();
    int newTileId = currentPlayer.getCurrentTile().getTileId();

    //Remove current player image from board
    removePlayerImageFromOldTile(currentPlayer);

    //Add current player image on new tile
    addPlayerImageToNewTile(currentPlayer, newTileId);

    //Update infobox with players move
    updateInfoBox(currentPlayer, newTileId);

    //Check for winner
    checkForWinner();
  }

  /**
   * Display dice box
   */
  public void displayDice(){
    dieBox.getChildren().clear();
    dieBox.setAlignment(Pos.CENTER);
    dieBox.setSpacing(10);

    for(Die d : game.getDice().getListOfDice()){
      ImageView die = new ImageView(new Image("/images/dice"+d.getValue()+".png"));
      die.setFitHeight(40);
      die.setFitWidth(40);
      dieBox.getChildren().add(die);
    }
    displayInfoBox.getChildren().add(dieBox);
  }

  /**
   * Removes picture of player from old tile
   * @param player
   */
  private void removePlayerImageFromOldTile(Player player){
    for(Tile t : board.getTiles()){
      t.getTileBox().getChildren().removeIf(node ->
          node instanceof ImageView && matchesPlayerImage((ImageView) node, player.getColor()));
    }
  }


  private boolean matchesPlayerImage(ImageView imageView, String playerColor){
    //kontrollera om bildens filväg matchar spelarens färg
    String imagePath = imageView.getImage().getUrl();
    return imagePath != null && imagePath.contains(playerColor.toLowerCase());
  }

  /**
   * Add player image to the new position of the player, on the new tile
   * @param player
   * @param newTileId
   */
  private void addPlayerImageToNewTile(Player player, int newTileId){
    for(Tile t : board.getTiles()){
      if(t.getTileId() == newTileId){
        t.getTileBox().getChildren().add(createPlayerImage(player.getColor()));
      }
    }
  }

  /**
   * Update displayInfoBox to show players latest move and dice results.
   * @param player
   * @param newTileId
   */
  private void updateInfoBox(Player player, int newTileId){
    String message = player.getColor() + " threw a " + dice.getTotalSumOfEyes() + " and landed on tile " + newTileId;

    Text text = new Text(message);
    text.setStyle("-fx-font-size: 14;"
        + "-fx-font-family: Georgia;");
    text.setWrappingWidth(180);

    displayInfoBox.getChildren().add(text);
  }

  /**
   * Checking for winner and if winner is crowned the button disables.
   */
  private void checkForWinner(){
    if(game.getWinner()!=null){
      String winnerName = game.getWinner().getName();
      String winnerColor = game.getWinner().getColor();

      displayWinnerMessage(winnerName, winnerColor);
      startRoundButton.setDisable(true);
    }
  }

  private void displayWinnerMessage(String winnerName, String winnerColor){
    //Skapa semi-transparent lager för meddelandet
    VBox winnerOverlay = new VBox(10);
    winnerOverlay.setAlignment(Pos.CENTER);
    winnerOverlay.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7);"); //Semi-trasnperant svart bakgrun
    winnerOverlay.setPadding(new Insets(50));

    //ladda upp spelarens vinnarbild
    ImageView winnerImage = createWinnerImage(winnerColor);

    //Text för meddelandet
    Font customFont = Font.loadFont(Objects.requireNonNull(getClass().getResource("/font/LuckiestGuy-Regular.ttf")).toExternalForm(),90);
    Text winnerText = new Text(winnerName + " is the Winner!");
    winnerText.setFont(customFont);
    winnerText.setStyle("-fx-fill: #ffffff;");

    //Text och bild i en box
    HBox messageBox = new HBox(20);
    messageBox.setAlignment(Pos.CENTER);
    messageBox.getChildren().addAll(winnerImage, winnerText);

    //Main menu-knapp
    Font customFontButton = Font.loadFont(Objects.requireNonNull(getClass().getResource("/font/LuckiestGuy-Regular.ttf")).toExternalForm(),20);
    Button mainMenuButton = new Button("Main menu");
    mainMenuButton.setFont(customFontButton);
    mainMenuButton.setStyle(
        "-fx-background-color: #416c42; " +  // Bakgrundsfärg
            "-fx-text-fill: white; " +           // Textfärg
            "-fx-background-radius: 20px; " +    // Rundade hörn
            "-fx-border-color: #053005; " +      // Kantfärg
            "-fx-border-width: 2px;" +
            "-fx-border-radius: 20px" );
    mainMenuButton.setPrefSize(150, 50);
    mainMenuButton.setOnAction(e->{rootLayout.getChildren().remove(winnerOverlay);
      restartGame();
      showMainMenu();});

    //New Round- knapp
    Button newRoundButton = new Button("New Round");
    newRoundButton.setFont(customFontButton);
    newRoundButton.setStyle(
        "-fx-background-color: #416c42; " +  // Bakgrundsfärg
            "-fx-text-fill: white; " +           // Textfärg
            "-fx-background-radius: 20px; " +    // Rundade hörn
            "-fx-border-color: #053005; " +      // Kantfärg
            "-fx-border-width: 2px;" +
            "-fx-border-radius: 20px" );
    newRoundButton.setPrefSize(150, 50);
    newRoundButton.setOnAction(e->{rootLayout.getChildren().remove(winnerOverlay);
      restartGame();});

    winnerOverlay.getChildren().addAll(messageBox, newRoundButton, mainMenuButton);
    rootLayout.getChildren().add(winnerOverlay);

    playConfettiEffect();
  }


  /**
   * Creation pf imageview of winner when announced
   * @param color
   * @return
   */
  private ImageView createWinnerImage(String color){
    String winnerImagePath = "/images/" + color.toLowerCase() + "_winner.png";

    Image winnerImage = new Image(Objects.requireNonNull(getClass().getResource(winnerImagePath)).toExternalForm());
    ImageView winnerImageView = new ImageView(winnerImage);
    winnerImageView.setFitWidth(200); //
    winnerImageView.setFitHeight(200);
    winnerImageView.setPreserveRatio(true);

    return winnerImageView;
  }

  //confetti
  private void playConfettiEffect() {
    // Skapa en grupp för konfetti
    Group confettiGroup = new Group();

    // Lägg till konfetti i rootLayout
    rootLayout.getChildren().add(confettiGroup);

    // Generera konfetti-bitar
    for (int i = 0; i < 100; i++) {
      // Skapa en liten rektangel som representerar en konfettibit
      Rectangle confetti = new Rectangle(5, 15); // Smala "bitar"
      confetti.setFill(Color.color(Math.random(), Math.random(), Math.random())); // Random färg
      confetti.setX(Math.random() * 1000); // Slumpmässig position x
      confetti.setY(-50); // Starta ovanför skärmen

      confettiGroup.getChildren().add(confetti);

      // Skapa animation för varje konfettibit
      TranslateTransition transition = new TranslateTransition();
      transition.setNode(confetti); // Anslut animationen till konfettin
      transition.setDuration(Duration.seconds(3 + Math.random() * 2)); // Varje bit faller i 3-5 sekunder
      transition.setByY(1500); // Faller ner 800 pixlar

      // Skapa fade-out animation
      FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2), confetti);
      fadeTransition.setFromValue(1.0); // Full synlighet
      fadeTransition.setToValue(0.0); // Gradvis försvinn

      // Kör båda animationerna samtidigt och avsluta innan de "ligger" i en linje
      ParallelTransition parallelTransition = new ParallelTransition(transition, fadeTransition);
      parallelTransition.setCycleCount(1);
      parallelTransition.play();

    }
    // Ta bort konfettin efter animationen är klar
    new Timeline(
        new KeyFrame(Duration.seconds(5), e -> rootLayout.getChildren().remove(confettiGroup))
    ).play();
  }


  /**
   * Creation of imageview for the players images that move along the board
   * @param color
   * @return
   */
  private ImageView createPlayerImage(String color) {
    String piecePath = "/images/" + color.toLowerCase() + ".png";

    Image playerImage = new Image(Objects.requireNonNull(getClass().getResource(piecePath)).toExternalForm());
    ImageView playerImageView = new ImageView(playerImage);
    playerImageView.setFitWidth(25); //
    playerImageView.setFitHeight(25);
    playerImageView.setPreserveRatio(true);

    return playerImageView;
  }

  /**
   * Creation of the board for snakes and ladders game
   * @return
   */
  private VBox createBoardGrid(){
    VBox boardGrid = new VBox();
    int tileCounter = 10;

    //Fetch destinations
    Map<String, List<Integer>> destinations = collectDestinations();
    List<Integer> snakeDestination = destinations.get("snake");
    List<Integer> ladderDestination = destinations.get("ladder");

    rowBoxesHeigth = 0;
    int rowBoxesWidth = 0;
    //Iterate through the boars tiles in reverse order
    for(Tile t: board.getTiles().reversed()){
      if(tileCounter %10==0) {
        rowBox = new HBox();
        rowBoxesHeigth += 75;
        rowBox.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        if(tileCounter %20 == 0){
          rowBox.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
        }
      }

      //create visual representation of a tile
      VBox tileBox = createTileBox(t, snakeDestination, ladderDestination);
      t.setTileBox(tileBox);

      rowBox.getChildren().add(tileBox);

      // Arr rowBox to vbox if it's the start of a new row
      if(tileCounter %10 ==0) {
        rowBox.setAlignment(Pos.CENTER_LEFT);
        boardGrid.getChildren().add(rowBox);
      }
      tileCounter++;
    }
    return boardGrid;
  }


  /**
   * Collect the destinations for snakes and ladders
   */
  private Map<String, List<Integer>> collectDestinations(){
    List<Integer> snakeDestination = new ArrayList<>();
    List<Integer> ladderDestination = new ArrayList<>();

    //Collect destinations
    for(Tile t : board.getTiles()){
      if(t.getAction() instanceof SnakeAction){
        SnakeAction action = (SnakeAction) t.getAction();
        snakeDestination.add(action.getDestinationTile());
      } else if(t.getAction() instanceof LadderAction){
        LadderAction action = (LadderAction) t.getAction();
        ladderDestination.add(action.getDestinationTile());
      }
    }

    Map<String, List<Integer>> destinations = new HashMap<>();
    destinations.put("snake", snakeDestination);
    destinations.put("ladder", ladderDestination);

    return destinations;
  }

  /**
   * Method handles the creation of a single tile, including text, styling anf any other visuals.
   * @param t
   * @param snakeDestination
   * @param ladderDestination
   * @return
   */
  private VBox createTileBox(Tile t, List<Integer> snakeDestination, List<Integer> ladderDestination){
    VBox tileBox = new VBox();
    tileBox.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
    tileBox.setMinSize(75,75);
    tileBox.setAlignment(Pos.CENTER);
    tileBox.getChildren().add(new Text(""+t.getTileId()));

    //Apply styles based on tile type
    if(snakeDestination.contains(t.getTileId())){
      styleSnakeDestination(tileBox);
    }else if(ladderDestination.contains(t.getTileId())){
      styleLadderDestination(tileBox);
    }
    else if (t.getAction() instanceof PortalAction) {
      stylePortalTile(tileBox);
    }
    else if(t.getAction() != null){
      styleDefaultTile(tileBox, t);
    } else{
      tileBox.setStyle("-fx-background-color: #E0E0E0; -fx-border-color: #000000");
    }

    return tileBox;
  }

  /**
   * Style snake destination box
   * @param tileBox
   */
  private void styleSnakeDestination(VBox tileBox){
    tileBox.setStyle(
        "-fx-background-color: #FBC4C4; "
            + "-fx-border-color: #000000");
  }

  /**
   * Style Ladder destination tile
   * @param tileBox
   */
  private void styleLadderDestination(VBox tileBox){
    tileBox.setStyle(
        "-fx-background-color: #D3F9D8; "
            + "-fx-border-color: #000000");
  }

  /**
   * Style portal tile with picture
   * @param tileBox
   */
  private void stylePortalTile(VBox tileBox){
    tileBox.setStyle("-fx-background-color: #77b7d8; -fx-border-color: #000000");
    // Lägg till bild för portaler
    Image portalImage = new Image(Objects.requireNonNull(getClass().getResource("/images/portal.png")).toExternalForm());
    ImageView portalImageView = new ImageView(portalImage);
    portalImageView.setFitWidth(60);
    portalImageView.setFitHeight(60);
    portalImageView.setPreserveRatio(true);

    tileBox.setAlignment(Pos.CENTER);
    tileBox.getChildren().add(portalImageView);
  }

  /**
   * Create Default tiles + actions tiles
   * @param tileBox
   * @param t
   */
  private void styleDefaultTile(VBox tileBox, Tile t){

      switch (t.getAction().getClass().getSimpleName()){
        case "LadderAction":
          tileBox.setStyle(
              "-fx-background-color: #8aca84; "
                  + "-fx-border-color: #000000");
          break;
        case "SnakeAction":
          tileBox.setStyle(
              "-fx-background-color: #e85b5b;"
                  + " -fx-border-color: #000000");
          break;
        default:
          tileBox.setStyle("-fx-background-color: #E0E0E0; -fx-border-color: #000000");
      }

  }



}