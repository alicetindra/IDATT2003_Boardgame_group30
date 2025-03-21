package edu.ntnu.idatt2003.boardgame;

import com.google.gson.JsonObject;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class BoardGameApp extends Application {
  Board board;
  PlayerHolder playerHolder;
  Dice dice;
  Pane pane;
  VBox vbox;
  BoardGame game = new BoardGame();
  Player currentPlayer;

  public void init(){
    game.createBoard(10, "src/main/resources/boardGameInfo.json");
    board = game.getBoard();

    game.createDice(1);
    dice = game.getDice();

    game.createPlayerHolder("src/main/resources/players.csv");
    playerHolder = game.getPlayerHolder();

    playerHolder.setCurrentPlayer(playerHolder.getPlayers().getLast());

    for(Player p: playerHolder.getPlayers()){
      p.setCurrentTile(board, 0);
    }
    playerHolder.setCurrentPlayer(playerHolder.getPlayers().getLast());
    currentPlayer = playerHolder.getCurrentPlayer();
  }

  @Override
  public void start(Stage stage) throws Exception {
    pane = new Pane();
    vbox = new VBox();

    for(Tile t: board.getTiles()){
      vbox.getChildren().add(new Text(""+t.getTileId()));
    }

    for(Player p: playerHolder.getPlayers()){
      vbox.getChildren().add(new Text(p.getName()));
    }

    Button startRoundButton = new Button("Start Round");
    startRoundButton.setOnAction(this::handleStartRoundButton);
    vbox.getChildren().add(startRoundButton);
    pane.getChildren().add(vbox);

    Scene scene = new Scene(pane,600,600);
    stage.setScene(scene);
    stage.setTitle("Board Game");
    stage.show();
  }

  private void handleStartRoundButton(ActionEvent actionEvent) {
    game.play();

    Text text = new Text(playerHolder.getCurrentPlayer().getColor()+" moves "+dice.getTotalSumOfEyes()+"spots and is now on tile "+playerHolder.getCurrentPlayer().getCurrentTile().getTileId());
    vbox.getChildren().add(text);
  }

}