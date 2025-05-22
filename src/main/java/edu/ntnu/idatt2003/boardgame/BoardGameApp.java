package edu.ntnu.idatt2003.boardgame;

import edu.ntnu.idatt2003.boardgame.Controller.MenuController;
import edu.ntnu.idatt2003.boardgame.View.MenuView;
import java.util.Objects;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Entry point for the Board Game application.
 * <p>
 * This class launches the JavaFX application and sets up the initial UI,
 * which consists of the main menu. It initializes the {@link MenuView} and its
 * corresponding {@link MenuController}, sets the scene, applies the stylesheet,
 * and displays the primary stage.
 * <p>
 * This application uses JavaFX and should be launched through the {@code main} method,
 * which delegates control to the {@link #start(Stage)} method via {@link Application#launch(String...)}.
 */
public class BoardGameApp extends Application {

  /**
   * Starts the JavaFX application by setting up the menu view and controller,
   * applying the stylesheet, and displaying the main window.
   *
   * @param primaryStage the primary stage for this application
   */
  @Override
  public void start(Stage primaryStage){
    MenuView menuView = new MenuView();
    new MenuController(menuView);


    Scene scene = new Scene(menuView.getMenuLayout());
    scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/design.css")).toExternalForm());
    primaryStage.setScene(scene);
    primaryStage.setTitle("Board game");
    primaryStage.setMaximized(true);
    primaryStage.show();
  }

  /**
   * Launches the Board Game application.
   *
   * @param args the command-line arguments passed to the application
   */
  public static void main(String[] args) {
    launch(args);
  }

}