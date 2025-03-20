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

  public void init(){
    WriteBoard writeBoard = new WriteBoard();
    ReadBoard readBoard = new ReadBoard();

    WritePlayers writePlayers = new WritePlayers();
    ReadPlayers readPlayers = new ReadPlayers();


    JsonObject tileJson = writeBoard.serializeTiles(10);
    writeBoard.writeJsonToFile(tileJson, "src/main/resources/boardGameInfo.json");

    board = readBoard.readTilesFromFile("src/main/resources/boardGameInfo.json");

    JsonObject playerJson = writePlayers.serializePlayers();
    writePlayers.writeJsonToFile(playerJson, "src/main/resources/players.json");

    playerHolder = readPlayers.readPlayersFromFile("src/main/resources/players.json");

    dice = new Dice(2);
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
    int totalEyes = dice.roll();
    Text text = new Text("Total Eyes: "+totalEyes);
    vbox.getChildren().add(text);
  }

}


