package edu.ntnu.idatt2003.boardgame;

import edu.ntnu.idatt2003.boardgame.Controller.MenuController;
import edu.ntnu.idatt2003.boardgame.Controller.SnakesAndLaddersController;

import edu.ntnu.idatt2003.boardgame.View.MenuView;
import edu.ntnu.idatt2003.boardgame.View.SnakesAndLaddersView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class BoardGameApp extends Application {

  @Override
  public void start(Stage primaryStage){
    //SnakesAndLaddersView view = new SnakesAndLaddersView();
    //new SnakesAndLaddersController(view);

    MenuView menuView = new MenuView();
    new MenuController(menuView);


    Scene scene = new Scene(menuView.getMenuLayout());
    scene.getStylesheets().add(getClass().getResource("/design.css").toExternalForm());
    primaryStage.setScene(scene);
    primaryStage.setTitle("Board game");
    primaryStage.setMaximized(true);
    primaryStage.show();
  }


  public static void main(String[] args) {
    launch(args);
  }

}