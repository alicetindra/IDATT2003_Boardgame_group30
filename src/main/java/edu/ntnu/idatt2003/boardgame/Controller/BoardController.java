package edu.ntnu.idatt2003.boardgame.Controller;

import edu.ntnu.idatt2003.boardgame.Model.Board;
import edu.ntnu.idatt2003.boardgame.Model.BoardGame;
import edu.ntnu.idatt2003.boardgame.Model.Die;
import edu.ntnu.idatt2003.boardgame.Model.Player;
import edu.ntnu.idatt2003.boardgame.Model.PlayerHolder;
import edu.ntnu.idatt2003.boardgame.Model.Tile;
import edu.ntnu.idatt2003.boardgame.View.BoardView;
import edu.ntnu.idatt2003.boardgame.View.MainMenuView;

import java.io.IOException;
import java.util.Objects;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class BoardController {
  private MainMenuView view;
  private MainMenuController controller;
  private BoardGame boardGame;
  HBox dieBox = new HBox();
  private BoardView boardView;
  private PlayerHolder playerHolder;
  private Player currentPlayer;
  private Board board;

  public BoardController(BoardGame boardGame, MainMenuController controller) {
    this.boardGame = boardGame;
    this.controller = controller;
    this.view = new MainMenuView();
    this.boardView = new BoardView(this);

  }


  public void handleSnakesAndLadders(){
    int selectedSize = view.getComboBoxBoard().getValue();
    try{
      boardGame.initializeBoard("Snakes and ladders", selectedSize, "src/main/resources/hardcodedBoards.json");
      board = boardGame.getBoard();

      BoardView boardView = new BoardView(this);
      GridPane boardGrid = boardView.createGridBoard(selectedSize, board);

      playerHolder = setPlayerHolder();
      System.out.println(playerHolder== boardGame.getPlayerHolder());
      System.out.println(boardGame.getPlayerHolder().getPlayers().size());

      for(Player p: playerHolder.getPlayers()){
        p.setBoardGame(boardGame);
        p.placeOnTile(p.getBoardGame().getBoard(),1);
        Tile t = boardGame.getBoard().getTiles().get(0);
        t.getTileBox().getChildren().add(boardView.getPlayerImage(p));
        System.out.println(p.getCurrentTile().getId());
      }
      playerHolder.setCurrentPlayer(playerHolder.getPlayers().getLast());
      currentPlayer = playerHolder.getCurrentPlayer();

      HBox titleWithImage = boardView.createTitle();
      VBox rulesColumn = boardView.createRulesColumn();
      VBox infoColumn = boardView.createInfoColumn();
      Button startRoundButton = boardView.createStartButton();
      Button mainMenuButton = boardView.createMainMenuButton();

      startRoundButton.setOnAction(this::handleStartRoundButton);
      mainMenuButton.setOnAction(e-> {restartGame();
        controller.returnToMainMenu();
      });

      infoColumn.getChildren().add(startRoundButton);
      rulesColumn.getChildren().add(mainMenuButton);

      BorderPane gameLayout = boardView.createMainLayout(boardGrid, titleWithImage, rulesColumn, infoColumn);


      controller.getRootLayout().getChildren().clear();
      controller.getRootLayout().getChildren().add(gameLayout);
      StackPane.setAlignment(gameLayout, Pos.CENTER);

    }catch (Exception e) {
      e.printStackTrace();
      System.out.println("Failed to initialize game layout ");
    }
    // Add logic for starting the game
  }
  public PlayerHolder setPlayerHolder() throws IOException {
    boardGame.createPlayerHolder("src/main/resources/players.csv", controller.getStringOfPlayers());
    playerHolder = boardGame.getPlayerHolder();
    return playerHolder;
  }



  private void handleStartRoundButton(ActionEvent event) {
    boardView.getDisplayInfoBox().getChildren().clear();
    boardGame.initializeDice(view.getIntegerValue());
    boardGame.play();

    displayDice();

    Player currentPlayer = playerHolder.getCurrentPlayer();
    int newTileId = currentPlayer.getCurrentTile().getId();

    //Remove current player image from board
    removePlayerImageFromOldTile(currentPlayer);

    //Add current player image on new tile
    addPlayerImageToNewTile(currentPlayer, newTileId);

    boardView.updateInfoBox(currentPlayer, newTileId);

    checkForWinner();
  }

  /**
   * Display dice box
   */
  public void displayDice(){
    dieBox.getChildren().clear();
    dieBox.setAlignment(Pos.CENTER);
    dieBox.setSpacing(10);

    for(Die d : boardGame.getDice().getListOfDice()){
      ImageView die = new ImageView(new Image("/images/dice"+d.getValue()+".png"));
      die.setFitHeight(40);
      die.setFitWidth(40);
      dieBox.getChildren().add(die);
    }
    boardView.getDisplayInfoBox().getChildren().add(dieBox);
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
      if(t.getId() == newTileId){
        t.getTileBox().getChildren().add(boardView.getPlayerImage(player));
      }
    }
  }


  /**
   * Checking for winner and if winner is crowned the button disables.
   */
  private void checkForWinner(){
    if(boardGame.getWinner()!=null){
      String winnerName = boardGame.getWinner().getName();
      String winnerColor = boardGame.getWinner().getColor();

      displayWinnerMessage(winnerName, winnerColor);
      boardView.createStartButton().setDisable(true);
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
    mainMenuButton.setOnAction(e->{controller.getRootLayout().getChildren().remove(winnerOverlay);
      restartGame();
      controller.returnToMainMenu();});


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
    newRoundButton.setOnAction(e->{controller.getRootLayout().getChildren().remove(winnerOverlay);
      restartGame();});

    winnerOverlay.getChildren().addAll(messageBox, newRoundButton, mainMenuButton);
    controller.getRootLayout().getChildren().add(winnerOverlay);

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
    controller.getRootLayout().getChildren().add(confettiGroup);

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
        new KeyFrame(Duration.seconds(5), e -> controller.getRootLayout().getChildren().remove(confettiGroup))
    ).play();
  }

  /**
   * Restarts game, undo winner and reload the side, is used in displayWinnerMessage
   */
  private void restartGame(){
    //reset winner
    boardGame.undoWinner(boardGame.getWinner());

    handleSnakesAndLadders();
    boardView.getDisplayInfoBox().getChildren().clear();
  }





}
