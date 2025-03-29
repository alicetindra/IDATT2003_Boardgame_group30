package edu.ntnu.idatt2003.boardgame;

import edu.ntnu.idatt2003.boardgame.Controller.MainMenuController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.Objects;


public class BoardGameApp extends Application {
  private StackPane rootLayout;

  @Override
  public void start(Stage stage){
    rootLayout = new StackPane();
    new MainMenuController(rootLayout);

    rootLayout.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/design.css")).toExternalForm());

    Scene menuScene = new Scene(rootLayout, 1000, 650);
    stage.setScene(menuScene);
    stage.setTitle("Board Game");
    stage.setMaximized(true);
    stage.show();

  }

  public static void main(String[] args) {
    launch(args);
  }

}