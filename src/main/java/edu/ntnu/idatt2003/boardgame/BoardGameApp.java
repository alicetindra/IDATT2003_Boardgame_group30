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
  ScrollPane scrollpane;
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
      //Initialize dice and players.
      game.createDice(1);
      dice = game.getDice();

      game.createPlayerHolder("src/main/resources/players.csv", null);
      playerHolder = game.getPlayerHolder();

      for(Player p: playerHolder.getPlayers()){
        p.setBoardGame(game);
        p.setCurrentTile(board, 1);
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
      VBox boardGrid = createBoardGrid();
      boardGrid.setAlignment(Pos.CENTER);
      //Setting the height and width to the grid so it does not adjust with the screen
      boardGrid.setMaxHeight(540);
      boardGrid.setMaxWidth(600);
      boardGrid.setMinHeight(540);
      boardGrid.setMinWidth(600);

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

    int newTile = playerHolder.getCurrentPlayer().getCurrentTile().getTileId();
    Text text = new Text(playerHolder.getCurrentPlayer().getColor()+" threw a "+dice.getTotalSumOfEyes()+" and landed on " + newTile);
    text.setStyle("-fx-font-size: 14;");
    text.setWrappingWidth(180);
    if(game.getWinner()!=null){
      displayInfoBox.getChildren().add(new Text("Winner: "+game.getWinner().getName()));
      startRoundButton.setDisable(true);
    }
    displayInfoBox.getChildren().add(text);
  }




  private VBox createBoardGrid(){
    VBox vbox = new VBox();
    HBox rowBox = null;
    int i = 10;

    //List for destinations for colour
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

    for(Tile t: board.getTiles().reversed()){
      if(i %10==0) {
        rowBox = new HBox();
        rowBox.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        if(i %20 == 0){
          rowBox.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
        }
      }
      //create tile and colour
      VBox tileBox = new VBox();
      tileBox.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
      tileBox.getChildren().add(new Text(""+t.getTileId()));
      tileBox.setPrefWidth(60);
      tileBox.setPrefHeight(60);

      if(snakeDestination.contains(t.getTileId())){
        tileBox.setStyle(
            "-fx-background-color: #FBC4C4; "
                + "-fx-border-color: #000000");
      }else if(ladderDestination.contains(t.getTileId())){
        tileBox.setStyle(
            "-fx-background-color: #D3F9D8; "
            + "-fx-border-color: #000000");
      }
      else if (t.getAction() instanceof PortalAction) {
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
      else if(t.getAction() != null){
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
      }else{
        tileBox.setStyle("-fx-background-color: #E0E0E0; -fx-border-color: #000000");
      }


      if (rowBox != null){
        rowBox.getChildren().add(tileBox);
      }

      if(i %10 ==0 && rowBox != null) {
        rowBox.setAlignment(Pos.CENTER_LEFT);
        vbox.getChildren().add(rowBox);
      }
      i++;
    }
    return vbox;
  }

}