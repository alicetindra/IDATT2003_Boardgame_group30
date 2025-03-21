package edu.ntnu.idatt2003.boardgame;

import edu.ntnu.idatt2003.boardgame.componentHolders.Board;
import edu.ntnu.idatt2003.boardgame.componentHolders.BoardGame;
import edu.ntnu.idatt2003.boardgame.componentHolders.Dice;
import edu.ntnu.idatt2003.boardgame.componentHolders.PlayerHolder;
import edu.ntnu.idatt2003.boardgame.components.Player;
import edu.ntnu.idatt2003.boardgame.components.Tile;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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

  public void init(){
    game.createBoard(90, "src/main/resources/boardGameInfo.json");
    board = game.getBoard();

    game.createDice(1);
    dice = game.getDice();

    game.createPlayerHolder("src/main/resources/players.csv");
    playerHolder = game.getPlayerHolder();

    playerHolder.setCurrentPlayer(playerHolder.getPlayers().getLast());

    for(Player p: playerHolder.getPlayers()){
      p.setBoardGame(game);
      p.setCurrentTile(board, 0);
    }
    playerHolder.setCurrentPlayer(playerHolder.getPlayers().getLast());
    currentPlayer = playerHolder.getCurrentPlayer();
  }

  @Override
  public void start(Stage stage) throws Exception {
    scrollpane = new ScrollPane();
    Pane pane = new Pane();
    vbox = new VBox();
    vbox.setLayoutX(200);
    vbox.setLayoutY(100);
    int i = 10;

    for(Tile t: board.getTiles().reversed()){
      if(i %10==0) {
        rowBox = new HBox();
        rowBox.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        if(i %20 == 0){
          rowBox.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
        }
      }
      VBox tileBox = new VBox();
      tileBox.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
      tileBox.getChildren().add(new Text(""+t.getTileId()));
      tileBox.setPrefWidth(60);
      tileBox.setPrefHeight(60);
      tileBox.setStyle("-fx-background-color: #aeaeff; -fx-border-color: #000000");
      rowBox.getChildren().add(tileBox);
      if(i %10 ==0) {
        rowBox.setAlignment(Pos.CENTER_LEFT);
        vbox.getChildren().add(rowBox);
      }
      i++;

    }

    playerBox = new VBox();
    for(Player p: playerHolder.getPlayers()){
      playerBox.getChildren().add(new Text(p.getName()));
    }

    displayInfoBox.setLayoutY(200);

    buttonBox = new VBox();
    buttonBox.setLayoutY(300);
    buttonBox.setLayoutX(850);
    Button startRoundButton = new Button("Start Round");
    startRoundButton.setOnAction(this::handleStartRoundButton);
    buttonBox.getChildren().add(startRoundButton);


    pane.getChildren().addAll(vbox,playerBox,buttonBox, displayInfoBox);
    scrollpane.setContent(pane);

    Scene scene = new Scene(scrollpane,1000,600);
    stage.setScene(scene);
    stage.setTitle("Board Game");
    stage.show();
  }

  private void handleStartRoundButton(ActionEvent actionEvent) {
    game.play();

    Text text = new Text(playerHolder.getCurrentPlayer().getColor()+" moves "+dice.getTotalSumOfEyes()+"spots and is now on tile "+playerHolder.getCurrentPlayer().getCurrentTile().getTileId());
    text.setStyle("-fx-font-size: 16;");
    displayInfoBox.getChildren().add(text);
  }

}