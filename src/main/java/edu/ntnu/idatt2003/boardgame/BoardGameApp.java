package edu.ntnu.idatt2003.boardgame;

import edu.ntnu.idatt2003.boardgame.actions.LadderAction;
import edu.ntnu.idatt2003.boardgame.actions.PortalAction;
import edu.ntnu.idatt2003.boardgame.actions.SnakeAction;
import edu.ntnu.idatt2003.boardgame.componentHolders.Board;
import edu.ntnu.idatt2003.boardgame.componentHolders.BoardGame;
import edu.ntnu.idatt2003.boardgame.componentHolders.Dice;
import edu.ntnu.idatt2003.boardgame.componentHolders.PlayerHolder;
import edu.ntnu.idatt2003.boardgame.components.Player;
import edu.ntnu.idatt2003.boardgame.components.Tile;
import java.util.HashMap;
import java.util.Map;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class BoardGameApp extends Application {
  Board board;
  PlayerHolder playerHolder;
  Dice dice;
  VBox vbox;
  BoardGame game = new BoardGame();
  Player currentPlayer;
  VBox displayInfoBox = new VBox();
  HBox rowBox;
  VBox buttonBox;
  VBox playerBox;
  Button startRoundButton;

  public static void main(String[] args){
    launch(args);
  }

  @Override
  public void start(Stage stage){
    double windowWidth = 1000;
    double windowHeight = 600;

    VBox menuBox = new VBox();
    menuBox.setSpacing(15);
    menuBox.setAlignment(Pos.CENTER);

    Button snakesAndLAddersButton = new Button("Snakes and Ladders");
    snakesAndLAddersButton.setOnAction(e-> startSnakesAndLAdders(stage));

    menuBox.getChildren().add(snakesAndLAddersButton);

    Scene menuScene = new Scene(menuBox, windowWidth, windowHeight);
    stage.setScene(menuScene);
    stage.setTitle("Choose your game");
    stage.show();
  }


  public void startSnakesAndLAdders(Stage stage){
    try{
      //The board og the game
      game.createBoard(90, "src/main/resources/snakesAndLaddersBoard.json");
      board = game.getBoard();
      VBox boardGrid = createBoardGrid();
      //Initialize dice and players.
      game.createDice(1);
      dice = game.getDice();

      game.createPlayerHolder("src/main/resources/players.csv", null);
      playerHolder = game.getPlayerHolder();

      for(Player p: playerHolder.getPlayers()){
        p.setBoardGame(game);
        p.setCurrentTile(board, 1);

        //lägg spelares bild på tile 1
        for(Tile t : board.getTiles()){
          if(t.getTileId() == 1) {
            ImageView playerImageView = createPlayerImage(p.getColor());

            // Justera positionen för att skapa överlappning
            playerImageView.setTranslateX(Math.random() * 5); // Slumpmässig liten förskjutning i X
            playerImageView.setTranslateY(Math.random() * 5); // Slumpmässig liten förskjutning i Y

            t.getTileBox().getChildren().add(playerImageView);
          }
        }
      }

      playerHolder.setCurrentPlayer(playerHolder.getPlayers().getLast());
      currentPlayer = playerHolder.getCurrentPlayer();

      //Setting custom fonts
      Font customFontTitle = Font.loadFont(Objects.requireNonNull(getClass().getResource("/font/LuckiestGuy-Regular.ttf")).toExternalForm(),50);
      Font customFontSubTitle = Font.loadFont(Objects.requireNonNull(getClass().getResource("/font/LuckiestGuy-Regular.ttf")).toExternalForm(),30);
      Font customFontButton = Font.loadFont(Objects.requireNonNull(getClass().getResource("/font/LuckiestGuy-Regular.ttf")).toExternalForm(),15);


      // Lägg till rubriken högst upp med bilder
      HBox titleWithImage = new HBox();
      titleWithImage.setSpacing(10);
      titleWithImage.setAlignment(Pos.CENTER);

      //Snake image
      Image snakeImage = new Image(Objects.requireNonNull(getClass().getResource("/images/snake.png")).toExternalForm());
      ImageView snakeImageView = new ImageView(snakeImage);
      snakeImageView.setFitHeight(40);
      snakeImageView.setFitWidth(40);
      snakeImageView.setPreserveRatio(true);

      //Title
      Text title = new Text("Snakes and Ladders");
      title.setFont(customFontTitle);
      title.setStyle("-fx-fill: #19599f");

      //Ladder image
      Image ladderImage = new Image(Objects.requireNonNull(getClass().getResource("/images/ladders.png")).toExternalForm());
      ImageView ladderImageView = new ImageView(ladderImage);
      ladderImageView.setFitHeight(40);
      ladderImageView.setFitWidth(40);
      ladderImageView.setPreserveRatio(true);

      titleWithImage.getChildren().addAll(snakeImageView, title, ladderImageView);
      titleWithImage.setPadding(new Insets(20,0,0,0));


      //Information on rules box to the left
      VBox rulesColumn = new VBox();
      rulesColumn.setSpacing(10);
      rulesColumn.setPrefWidth(200);
      rulesColumn.setStyle(
          "-fx-background-color: #6f9c6f; "
          + "-fx-border-color: #6f9c6f;");
      rulesColumn.setAlignment(Pos.CENTER);

      //Rules
      Text rulesTitle = new Text("Game Rules");
      rulesTitle.setFont(customFontSubTitle);
      rulesTitle.setStyle("-fx-fill: #19599f");
      rulesColumn.getChildren().add(rulesTitle);

      Text rule1 = new Text("1. Roll the dice to move when it's your turn.");
      Text rule2 = new Text("2. If you land on...\n"
          + "\ndark green, climb up to light green.\n"
          + "\ndark red, slide down to light red.\n"
          + "\ndark blue, you will be transported to a random tile.");
      Text rule3 = new Text("3. The first player at the finish is the winner.");
      rule1.setWrappingWidth(180);
      rule1.setStyle("-fx-font-size: 14px; "
          + "-fx-fill: black;"
          + "-fx-font-family: Georgia");
      rule2.setWrappingWidth(180);
      rule2.setStyle("-fx-font-size: 14px; "
          + "-fx-fill: black;"
          + "-fx-font-family: Georgia");
      rule3.setWrappingWidth(180);
      rule3.setStyle("-fx-font-size: 14px; "
          + "-fx-fill: black;"
          + "-fx-font-family: Georgia");
      rulesColumn.getChildren().addAll(rule1, rule2, rule3);


      //set up board grid for snakes and ladders

      boardGrid.setAlignment(Pos.CENTER);

      //Player information to the right
      VBox infoColumn = new VBox();
      infoColumn.setSpacing(10);
      infoColumn.setPrefWidth(200);
      infoColumn.setStyle(
          "-fx-background-color: #6f9c6f; "
              + "-fx-border-color: #6f9c6f;");
      infoColumn.setAlignment(Pos.CENTER);

      Text infoTitle = new Text("Player Info");
      infoTitle.setFont(customFontSubTitle);
      infoTitle.setStyle("-fx-fill: #19599f");
      infoColumn.getChildren().add(infoTitle);

      for(Player p: playerHolder.getPlayers()){
        Text playerName = new Text(p.getName());
        playerName.setStyle("-fx-font-size: 14px; "
            + "-fx-fill: black;"
            + "-fx-font-family: Georgia");
        infoColumn.getChildren().add(playerName);
      }
      infoColumn.getChildren().add(displayInfoBox);


      //Start button down to the right
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
      infoColumn.getChildren().add(startRoundButton);

      //Layout borderPane with everything
      BorderPane mainLayout = new BorderPane();
      mainLayout.setTop(titleWithImage);
      mainLayout.setLeft(rulesColumn);
      mainLayout.setCenter(boardGrid);
      mainLayout.setRight(infoColumn);

      mainLayout.setStyle("-fx-background-color: #6f9c6f;");


      Scene scene = new Scene(mainLayout,1000,600);
      stage.setScene(scene);
      stage.setTitle("Board Game: Snakes and Ladders");
      stage.show();
    } catch(IOException e){
      e.printStackTrace();
    }
  }



  private void handleStartRoundButton(ActionEvent actionEvent) {
    displayInfoBox.getChildren().clear();
    game.play();

    Player currentPlayer = playerHolder.getCurrentPlayer();
    int newTile = currentPlayer.getCurrentTile().getTileId();

    //ta bort spelarens bild från gammal tile
    for(Tile t : board.getTiles()){
      if(t.getTileId() == currentPlayer.getCurrentTile().getTileId()){
        t.getTileBox().getChildren().removeIf(node -> node instanceof ImageView);
      }
    }

    //lägg till bild på ny tile
    for(Tile t : board.getTiles()){
      if(t.getTileId() == newTile){
        t.getTileBox().getChildren().add(createPlayerImage(currentPlayer.getColor()));
      }
    }

    currentPlayer.setCurrentTile(board, newTile);

    Text text = new Text(playerHolder.getCurrentPlayer().getColor()+" threw a "+dice.getTotalSumOfEyes()+" and landed on " + newTile);
    text.setStyle("-fx-font-size: 14;");
    text.setWrappingWidth(180);

    if(game.getWinner()!=null){
      displayInfoBox.getChildren().add(new Text("Winner: "+game.getWinner().getName()));
      startRoundButton.setDisable(true);
    }
    displayInfoBox.getChildren().add(text);
  }

  private ImageView createPlayerImage(String color) {
    String piecePath = "/images/default_piece.png";
    if (color.equalsIgnoreCase("red")) {
      piecePath = "/images/red_piece.png";
    } else if (color.equalsIgnoreCase("blue")) {
      piecePath = "/images/blue_piece.png";
    } else if (color.equalsIgnoreCase("green")) {
      piecePath = "/images/green_piece.png";
    }

    Image playerImage = new Image(Objects.requireNonNull(getClass().getResource(piecePath)).toExternalForm());
    ImageView playerImageView = new ImageView(playerImage);
    playerImageView.setFitWidth(20); //
    playerImageView.setFitHeight(20);
    playerImageView.setPreserveRatio(true);

    return playerImageView;
  }



  private VBox createBoardGrid(){
    VBox vbox = new VBox();
    int tileCounter = 10;

    //Fetch destinations
    Map<String, List<Integer>> destinations = collectDestinations();
    List<Integer> snakeDestination = destinations.get("snake");
    List<Integer> ladderDestination = destinations.get("ladder");

    //Iterate through the boars tiles in reverse order
    for(Tile t: board.getTiles().reversed()){
      if(tileCounter %10==0) {
        rowBox = new HBox();
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
        vbox.getChildren().add(rowBox);
      }
      tileCounter++;
    }
    return vbox;
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
   * Manages creation of new rows based on the tileCounter value
   * @param tileCounter
   * @return
   */
  private HBox initializeRow(int tileCounter){
    HBox rowBox = new HBox();
    rowBox.setNodeOrientation(
        (tileCounter % 20 == 0) ? NodeOrientation.LEFT_TO_RIGHT : NodeOrientation.RIGHT_TO_LEFT
    );
    return rowBox;
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
    tileBox.setPrefWidth(60);
    tileBox.setPrefHeight(60);
    tileBox.setMaxWidth(60);
    tileBox.setMaxHeight(60);
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
    portalImageView.setFitWidth(40);
    portalImageView.setFitHeight(40);
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