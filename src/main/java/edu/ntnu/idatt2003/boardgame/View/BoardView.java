package edu.ntnu.idatt2003.boardgame.View;

import edu.ntnu.idatt2003.boardgame.Controller.BoardController;
import edu.ntnu.idatt2003.boardgame.Controller.MainMenuController;
import edu.ntnu.idatt2003.boardgame.Model.Board;
import edu.ntnu.idatt2003.boardgame.Model.Dice;
import edu.ntnu.idatt2003.boardgame.Model.Player;
import edu.ntnu.idatt2003.boardgame.Model.PlayerHolder;
import edu.ntnu.idatt2003.boardgame.Model.Tile;
import edu.ntnu.idatt2003.boardgame.Model.actions.LadderAction;
import edu.ntnu.idatt2003.boardgame.Model.actions.PortalAction;
import edu.ntnu.idatt2003.boardgame.Model.actions.SnakeAction;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class BoardView {

  private PlayerHolder playerHolder;
  private VBox displayInfoBox = new VBox();
  private Dice dice;
  private BoardController controller;
  private Button startRoundButton;

  public BoardView(BoardController controller) {
    this.controller = controller;
  }

  /**
   * Creates the main layout for the game.
   * @param boardGrid
   * @param titleWithImage
   * @param rulesColumn
   * @param infoColumn
   * @return
   */
  public BorderPane createMainLayout(GridPane boardGrid, HBox titleWithImage, VBox rulesColumn, VBox infoColumn){
    BorderPane mainLayout = new BorderPane();
    mainLayout.setTop(titleWithImage);
    mainLayout.setLeft(rulesColumn);
    mainLayout.setCenter(boardGrid);
    mainLayout.setRight(infoColumn);

    BorderPane.setMargin(rulesColumn, new Insets(0,20,0,20));
    BorderPane.setMargin(infoColumn, new Insets(0,20,0,20));

    mainLayout.setStyle("-fx-background-color: #6f9c6f;");
    return mainLayout;
  }


  public GridPane createGridBoard(int totalTiles, Board board){
    GridPane grid = new GridPane();
    grid.setGridLinesVisible(true);

    int tilesPerRow = 10; //fixed number of columns
    int rows = (int) Math.ceil((double) totalTiles / tilesPerRow);

    for(int tileIndex = 0; tileIndex < totalTiles; tileIndex++){
      int row = rows - 1 - (tileIndex/tilesPerRow);
      int col;

      if ((rows - row) % 2 == 1) {
        // Odd row (left-to-right)
        col = tileIndex % tilesPerRow;
      } else {
        // Even row (right-to-left)
        col = tilesPerRow - 1 - (tileIndex % tilesPerRow);
      }

      Tile tile = board.getTiles().get(tileIndex);

      //Fetch destinations
      Map<String, List<Integer>> destinations = collectDestinations(board);
      List<Integer> snakeDestination = destinations.get("snake");
      List<Integer> ladderDestination = destinations.get("ladder");

      VBox tileBox = createTileBox(tile, snakeDestination, ladderDestination);
      tile.setTileBox(tileBox);


      grid.add(tileBox, col, row);
    }
    return grid;
  }

  public VBox createTileBox(Tile tile, List<Integer> snakeDestination, List<Integer> ladderDestination){
    VBox tileBox = new VBox();
    tileBox.setMinSize(75,75);
    tileBox.setAlignment(Pos.CENTER);

    tileBox.getChildren().add(new Text("" + tile.getId()));

    if(snakeDestination.contains(tile.getId())){
      styleSnakeDestination(tileBox);
    }else if(ladderDestination.contains(tile.getId())){
      styleLadderDestination(tileBox);
    }else if (tile.getAction() instanceof PortalAction){
      stylePortalTile(tileBox);
    }else if(tile.getAction() != null){
      styleDefaultTille(tileBox, tile);
    }else{
      tileBox.setStyle("-fx-background-color: #E0E0E0; -fx-border-color: #000000");
    }

    return tileBox;
  }

  /**
   * Collect the destinations for snakes and ladders
   */
  private Map<String, List<Integer>> collectDestinations(Board board){
    List<Integer> snakeDestination = new ArrayList<>();
    List<Integer> ladderDestination = new ArrayList<>();

    //Collect destinations
    for(Tile t : board.getTiles()){
      if(t.getAction() instanceof SnakeAction){
        SnakeAction action = (SnakeAction) t.getAction();
        snakeDestination.add(action.getDestinationTileId());
      } else if(t.getAction() instanceof LadderAction){
        LadderAction action = (LadderAction) t.getAction();
        ladderDestination.add(action.getDestinationTileId());
      }
    }

    Map<String, List<Integer>> destinations = new HashMap<>();
    destinations.put("snake", snakeDestination);
    destinations.put("ladder", ladderDestination);

    return destinations;
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
    Image portalImage = new Image(
        Objects.requireNonNull(getClass().getResource("/images/portal.png")).toExternalForm());
    ImageView portalImageView = new ImageView(portalImage);
    portalImageView.setFitWidth(60);
    portalImageView.setFitHeight(60);
    portalImageView.setPreserveRatio(true);

    tileBox.setAlignment(Pos.CENTER);
    tileBox.getChildren().add(portalImageView);
  }

  /**
   *
   * @param tileBox
   * @param tile
   */
  private void styleDefaultTille(VBox tileBox, Tile tile){
    switch (tile.getAction()) {
      case SnakeAction snakeAction -> tileBox.setStyle("-fx-background-color: #e85b5b;"
          + " -fx-border-color: #000000");
      case LadderAction ladderAction -> tileBox.setStyle("-fx-background-color: #8aca84; "
          + "-fx-border-color: #000000");
      case null, default ->
          tileBox.setStyle("-fx-background-color: #E0E0E0; -fx-border-color: #000000");
    }


  }

  /**
   * Creates the title bar with images and text for the game.
   * @return
   */
  public HBox createTitle(){
    Font customFontTitle = Font.loadFont(Objects.requireNonNull(getClass().getResource("/font/LuckiestGuy-Regular.ttf")).toExternalForm(),90);

    //Snake image
    Image snakeImage = new Image(Objects.requireNonNull(getClass().getResource("/images/snake.png")).toExternalForm());
    ImageView snakeImageView = new ImageView(snakeImage);
    snakeImageView.setFitHeight(90);
    snakeImageView.setFitWidth(90);
    snakeImageView.setPreserveRatio(true);

    //Title
    Text title = new Text("Snakes and Ladders");
    title.setFont(customFontTitle);
    title.setStyle("-fx-fill: #19599f");

    //Ladder image
    Image ladderImage = new Image(Objects.requireNonNull(getClass().getResource("/images/ladders.png")).toExternalForm());
    ImageView ladderImageView = new ImageView(ladderImage);
    ladderImageView.setFitHeight(90);
    ladderImageView.setFitWidth(90);
    ladderImageView.setPreserveRatio(true);

    HBox titleWithImage = new HBox(20, snakeImageView, title, ladderImageView);
    titleWithImage.setAlignment(Pos.CENTER);
    titleWithImage.setPadding(new Insets(50,0,0,0));

    return titleWithImage;
  }

  /**
   * Creates the rules column for the game.
   * @return
   */
  public VBox createRulesColumn(){
    Font customFontSubTitle = Font.loadFont(Objects.requireNonNull(getClass().getResource("/font/LuckiestGuy-Regular.ttf")).toExternalForm(),30);

    //Information on rules box to the left
    VBox rulesColumn = new VBox(20);
    rulesColumn.setPrefWidth(300);
    rulesColumn.setStyle(
        "-fx-background-color: #6f9c6f; "
            + "-fx-border-color: #6f9c6f;");
    rulesColumn.setAlignment(Pos.TOP_CENTER);

    rulesColumn.setPadding(new Insets(100,0,100,0));

    Text rulesTitle = new Text("Game Rules");
    rulesTitle.setFont(customFontSubTitle);
    rulesTitle.setStyle("-fx-fill: #19599f");

    Text rule1 = createRuleText("1. Roll the dice to move when it's your turn.");
    Text rule2 = createRuleText("2. Land on dark green to climb, dark red to slide, dark blue to teleport.");
    Text rule3 = createRuleText("3. The first player at the finish is the winner.");

    rulesColumn.getChildren().addAll(rulesTitle, rule1, rule2, rule3);

    Region spacer = new Region();
    VBox.setVgrow(spacer, Priority.ALWAYS); // Låt spacern växa och trycka knappen nedåt
    rulesColumn.getChildren().add(spacer);

    return rulesColumn;
  }

  /**
   * Creates a styled rule text.
   *
   * @param content The text content of the rule.
   * @return A styled Text node.
   */
  private Text createRuleText(String content) {
    Text rule = new Text(content);
    rule.setWrappingWidth(180);
    rule.setStyle("-fx-font-size: 14px; -fx-fill: black; -fx-font-family: Georgia;");
    return rule;
  }

  public VBox createInfoColumn() throws IOException {
    Font customFontSubTitle = Font.loadFont(Objects.requireNonNull(getClass().getResource("/font/LuckiestGuy-Regular.ttf")).toExternalForm(),30);

    VBox infoColumn = new VBox(10);
    infoColumn.setPrefWidth(300);
    infoColumn.setStyle(
        "-fx-background-color: #6f9c6f; "
            + "-fx-border-color: #6f9c6f;");
    infoColumn.setAlignment(Pos.TOP_CENTER);

    infoColumn.setPadding(new Insets(100,0,200,0));

    Text infoTitle = new Text("Player Info");
    infoTitle.setFont(customFontSubTitle);
    infoTitle.setStyle("-fx-fill: #19599f");

    infoColumn.getChildren().add(infoTitle);

    PlayerHolder playerHolder = controller.setPlayerHolder();
    if (playerHolder == null || playerHolder.getPlayers() == null || playerHolder.getPlayers().isEmpty()) {
      System.out.println("No players to display in the info column!");
      return infoColumn; // Return an empty column if no players
    }


    for(Player p: playerHolder.getPlayers()){
      HBox playerInfoBox = new HBox(10);
      playerInfoBox.setAlignment(Pos.CENTER);

      ImageView playerImage = createPlayerImage(p.getColor());

      Text playerName = new Text(" " + p.getName());
      playerName.setStyle("-fx-font-size: 14px; "
          + "-fx-fill: black;"
          + "-fx-font-family: Georgia");
      playerInfoBox.getChildren().addAll(playerImage, playerName);
      infoColumn.getChildren().add(playerInfoBox);
    }

    //Space down to display box
    Region spacerAboveDisplayBox = new Region();
    VBox.setVgrow(spacerAboveDisplayBox, Priority.ALWAYS); // Flexibelt mellanrum som trycker displayInfoBox nedåt
    infoColumn.getChildren().add(spacerAboveDisplayBox);

    // Lägg till displayInfoBox som innehåller tärningar och meddelanden
    displayInfoBox.setAlignment(Pos.CENTER); // Centrera displayBox-innehållet
    infoColumn.getChildren().add(displayInfoBox);


    return infoColumn;
  }


  public Button createStartButton(){
    Font customFontButton = Font.loadFont(
        Objects.requireNonNull(getClass().getResource("/font/LuckiestGuy-Regular.ttf")).toExternalForm(),15);

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


    VBox.setMargin(startRoundButton, new Insets(20,0,0,0));
    return startRoundButton;
  }

  /**
   * creates main menu to go back to welcome page
   * @return
   */
  public Button createMainMenuButton(){
    Font customFontButton = Font.loadFont(Objects.requireNonNull(getClass().getResource("/font/LuckiestGuy-Regular.ttf")).toExternalForm(),15);

    //back to main
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

    VBox.setMargin(mainMenuButton, new Insets(20,0,0,0));

    return mainMenuButton;
  }



  /**
   * Creation of imageview for the players images that move along the board
   * @param color
   * @return
   */
  public ImageView createPlayerImage(String color) {
    String piecePath = "/images/" + color.toLowerCase() + ".png";

    Image playerImage = new Image(Objects.requireNonNull(getClass().getResource(piecePath)).toExternalForm());
    ImageView playerImageView = new ImageView(playerImage);
    playerImageView.setFitWidth(25); //
    playerImageView.setFitHeight(25);
    playerImageView.setPreserveRatio(true);

    return playerImageView;
  }


  public void updateInfoBox(Player player, int newTileId){
    String message = player.getColor() + " threw a " + dice.roll() + " and landed on tile " + newTileId;

    Text text = new Text(message);
    text.setStyle("-fx-font-size: 14;"
        + "-fx-font-family: Georgia;");
    text.setWrappingWidth(180);

    displayInfoBox.getChildren().add(text);
  }

  public VBox getDisplayInfoBox(){
    return displayInfoBox;
  }

}
