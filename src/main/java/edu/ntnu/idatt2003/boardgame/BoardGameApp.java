package edu.ntnu.idatt2003.boardgame;

import edu.ntnu.idatt2003.boardgame.Controller.GameController;

import edu.ntnu.idatt2003.boardgame.View.BoardGameView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.Objects;


public class BoardGameApp extends Application {
  private StackPane rootLayout;

  @Override
  public void start(Stage primaryStage){
    BoardGameView view = new BoardGameView();
    new GameController(view);


    Scene scene = new Scene(view.getLayout());
    scene.getStylesheets().add(getClass().getResource("/design.css").toExternalForm());
    primaryStage.setMaximized(true);
    primaryStage.setScene(scene);
    primaryStage.setTitle("Board game");
    primaryStage.show();

  }

  public static void main(String[] args) {
    launch(args);
  }

}