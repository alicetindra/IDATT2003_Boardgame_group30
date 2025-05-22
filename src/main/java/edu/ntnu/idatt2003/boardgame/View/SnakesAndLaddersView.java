package edu.ntnu.idatt2003.boardgame.View;

import edu.ntnu.idatt2003.boardgame.Model.*;
import edu.ntnu.idatt2003.boardgame.Model.actions.*;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.*;
import java.util.logging.Logger;

/**
 * The {@code SnakesAndLaddersView} class is responsible for handling the visual
 * representation of the Snakes And Ladders-style game board and in-game user interface.
 * <p>
 * It displays the current state of the board, including tiles, players,
 * dice rolls, and actions. This class forms the View component in the
 * Model-View-Controller (MVC) design pattern and focuses solely on rendering
 * output without managing game logic.
 * </p>
 *
 * <p>
 * Key responsibilities include:
 *   <li>Displaying the Snakes And Ladders board and its tiles</li>
 *   <li>Updating player positions visually as the game progresses</li>
 *   <li>Rendering dice roll outcomes and other animations</li>
 *   <li>Showing information about player turns, actions, and tile effects</li>
 * </p>
 */
public class SnakesAndLaddersView {
    private MenuView menuView;
    private static final Logger log = Logger.getLogger(SnakesAndLaddersView.class.getName());

    /**
     * Buttons used in the SnakesAndLadders View
     */
    private final Button startRoundButton = new Button("Roll dice");
    private final Button restartGame = new Button("Restart");

    /**
     * Boxes used in the SnakesAndLadders view.
     */
    private final HBox dieBox = new HBox();
    private VBox rulesColumn = new VBox(30);
    private final VBox displayInfoBox = new VBox();
    private HBox titleBox;
    private final VBox infoColumn = new VBox(30);
    private final VBox winnerOverlay = new VBox(10);


    /**
     * Panes used in the SnakesAndLadders view.
     */
    private final GridPane grid = new GridPane();
    private BorderPane layout;
    private final StackPane stackableBoard = new StackPane();
    private final Pane snakesAndLaddersIcons = new Pane();

    /**
     * Out custom font for titles and button texts.
     */
    private final Font customFont = Font.loadFont(Objects.requireNonNull(getClass().getResource("/font/LuckiestGuy-Regular.ttf")).toExternalForm(),15);

    /**
     * Initializes the SnakesAndLadders view by creating the border pane layout and create start button.
     */
    public void initialize(){
        layout = new BorderPane();
        layout.getStyleClass().add("rootSL");
        createStartButton();
    }

    /**
     * Getters fot buttons in SnakesAndLadders view.
     * @return each button.
     */
    public Button getRestartGameButton() {
        restartGame.setFont(customFont);
        restartGame.setId("restartGameButton");
        return restartGame;
    }
    public Button getStartRoundButton(){
        return startRoundButton;
    }

    /**
     * Getter for panes in SnakesAndLadders view.
     * @return each pane, layout
     */
    public BorderPane getLayout(){
        return layout;
    }
    public GridPane getGrid(){
        return grid;
    }
    public StackPane getStackableBoardWithIcons(){
        stackableBoard.getChildren().clear();
        stackableBoard.getChildren().addAll(getGrid(),getSnakesAndLaddersIcons());
        return stackableBoard;
    }
    public Pane getSnakesAndLaddersIcons(){
        return snakesAndLaddersIcons;
    }

    /**
     * Getter for all boxes in SnakesAndLadders view.
     * @return each box
     */
    public VBox getRulesColumn(){
        return rulesColumn;
    }
    public VBox getInfoColumn(){
        return infoColumn;
    }
    public VBox getDisplayInfoBox() {
        return displayInfoBox;
    }
    public VBox getWinnerBox(){
        return winnerOverlay;
    }
    public HBox getDieBox() {
        return dieBox;
    }
    public HBox getTitleBox(){
        return titleBox;
    }

    /**
     * Getter for the player image.
     * @param p player whose image is fetched.
     * @return imageview of player image.
     */
    public ImageView getPlayerImage(Player p) {
        ImageView imageView = p.getImageView();
        imageView.setFitHeight(30);
        imageView.setPreserveRatio(true);
        return imageView;
    }

    /**
     * Creates the SnakesAndLadders layout with all its components.
     * @param boardGrid is the grid of the board.
     * @param titleWithImage is the box with the title along with images.
     * @param rulesColumn is the box with information about the rules of the game.
     * @param infoColumn is the box with player and round information along with the rolled dice.
     * @return a borderpane with the game layout.
     */
    public BorderPane createSnakesLaddersLayout(StackPane boardGrid, HBox titleWithImage, VBox rulesColumn, VBox infoColumn){
        layout.setTop(titleWithImage);
        layout.setLeft(rulesColumn);
        layout.setCenter(boardGrid);
        layout.setRight(infoColumn);

        BorderPane.setMargin(rulesColumn, new Insets(0,20,0,20));
        BorderPane.setMargin(infoColumn, new Insets(0,20,0,20));

        layout.setId("mainLayout");
        return layout;
    }

    /**
     * Creates SnakesAndLadders grid board with numbered tiles in alternating directions like a snakes movement.
     * @param board the board that is to be created.
     */
    public void createGridBoard(Board board){
        grid.setGridLinesVisible(true);

        int tilesPerRow = 10;
        int rows = (int) Math.ceil((double) board.getTiles().size() / tilesPerRow);

        for(int tileIndex = 0; tileIndex < board.getTiles().size(); tileIndex++){
            int row = rows - 1 - (tileIndex/tilesPerRow);
            int col;

            if ((rows - row) % 2 == 1) {
                col = tileIndex % tilesPerRow;
            } else {
                col = tilesPerRow - 1 - (tileIndex % tilesPerRow);
            }

            Tile tile = board.getTiles().get(tileIndex);

            Map<String, List<Integer>> destinations = collectDestinations(board);
            List<Integer> snakeDestination = destinations.get("snake");
            List<Integer> ladderDestination = destinations.get("ladder");

            VBox tileBox = new VBox();
            decorateTileBox(tileBox, tile.getAction(), tile.getId(), snakeDestination, ladderDestination);
            tile.setTileBox(tileBox);

            grid.add(tileBox, col, row);
            makeSnakesAndLaddersOverlay(board);
        }
    }

    /**
     * Creates the rules column on the right side of the layout.
     * <p>
     * Initializes the column if needed, sets styling and alignment,
     * and adds the game rules text.
     * </p>
     */
    public void createRulesColumn() {
        if (rulesColumn == null) {
            rulesColumn = new VBox(30);
        }
        rulesColumn.setPrefWidth(300);
        rulesColumn.setId("rulesColumn");
        rulesColumn.setAlignment(Pos.TOP_CENTER);

        rulesColumn.setPadding(new Insets(100,0,100,0));

        rulesColumn.getChildren().clear();
        Text title = new Text("Game Rules");
        title.setFont(customFont);
        title.getStyleClass().add("subTitle");


        Text ruleText = new Text("1. Roll the dice to move when it's your turn.\n\n2. Land on dark green to climb, dark red to slide, dark blue to teleport.\n\n3. The first player at the finish is the winner.");
        ruleText.setWrappingWidth(200);
        ruleText.getStyleClass().add("infoText");

        VBox ruleBox = new VBox(20);
        ruleBox.setPadding(new Insets(20));
        ruleBox.setAlignment(Pos.CENTER);
        ruleBox.setId("ruleText");
        ruleBox.getChildren().addAll(title, ruleText);

        rulesColumn.getChildren().add(ruleBox);

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);
        rulesColumn.getChildren().add(spacer);

        if (layout.getRight() != rulesColumn) {
            layout.setRight(rulesColumn);
        }
    }

    /**
     * Creates the info column on the left side of the layout.
     * <p>
     *   Initializes the column if needed, sets styling and alignment,
     *   and adds the player information from the playerHolder and dice.
     * </p></>
     * @param playerHolder the information about the players in the game.
     */
    public void createInfoColumn(PlayerHolder playerHolder){
        infoColumn.getChildren().clear();
        infoColumn.setPrefWidth(300);
        infoColumn.setId("infoColumn");
        infoColumn.setAlignment(Pos.TOP_CENTER);
        infoColumn.setPadding(new Insets(100, 0, 200, 0));

        VBox infoBoxText = new VBox(20);
        infoBoxText.setPadding(new Insets(20));
        infoBoxText.setSpacing(5);
        infoBoxText.setAlignment(Pos.CENTER);
        infoBoxText.setId("infoText");

        Text infoTitle = new Text("Player Info");
        infoTitle.setFont(customFont);
        infoTitle.getStyleClass().add("subTitle");

        infoBoxText.getChildren().add(infoTitle);

        for (Player p : playerHolder.getPlayers()) {
            HBox playerInfoBox = new HBox(10);
            playerInfoBox.setAlignment(Pos.CENTER);

            Text playerName = new Text(" " + p.getName());
            playerName.getStyleClass().add("infoText");
            Image playerIcon = p.getImageView().getImage();
            ImageView playerImage = new ImageView(playerIcon);
            playerImage.setFitHeight(30);
            playerImage.setPreserveRatio(true);
            playerInfoBox.getChildren().addAll(playerImage, playerName);

            infoBoxText.getChildren().add(playerInfoBox);
        }


        Region SpacerAboveDisplayBox = new Region();
        VBox.setVgrow(SpacerAboveDisplayBox, Priority.ALWAYS);
        infoBoxText.getChildren().add(SpacerAboveDisplayBox);

        displayInfoBox.setAlignment(Pos.CENTER);
        displayInfoBox.getStyleClass().add("infoBox");

        infoBoxText.getChildren().addAll(displayInfoBox);
        infoColumn.getChildren().add(infoBoxText);

        if (layout.getRight() != infoColumn) {
            layout.setRight(infoColumn);
        }
    }

    /**
     * Creates start round button.
     */
    public void createStartButton(){

        startRoundButton.setFont(customFont);
        startRoundButton.getStyleClass().add("start-round-button");

        VBox.setMargin(startRoundButton, new Insets(20,0,0,0));
    }

    /**
     * Creates the title box with the title 'Snakes And Ladders' as well as images of ladders and snakes in the title.
     */
    public void createTitleBox() {
        //Snake image
        ImageView snakeImageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(
            "/images/misc/snake.png")).toExternalForm()));
        snakeImageView.setFitHeight(90);
        snakeImageView.setFitWidth(90);
        snakeImageView.setPreserveRatio(true);

        //Title
        Text title = new Text("Snakes and Ladders");
        title.setFont(customFont);
        title.getStyleClass().add("title");

        //Ladder image
        Image ladderImage = new Image(Objects.requireNonNull(getClass().getResource(
            "/images/misc/ladders.png")).toExternalForm());
        ImageView ladderImageView = new ImageView(ladderImage);
        ladderImageView.setFitHeight(90);
        ladderImageView.setFitWidth(90);
        ladderImageView.setPreserveRatio(true);

        titleBox = new HBox(20, snakeImageView, title, ladderImageView);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setPadding(new Insets(50,0,0,0));
    }

    /**
     * Created a box when winner is announced.
     * It is discarded when enter is pressed.
     * @param winnerName the player name who won.
     * @param winnerColor the color of the player who won.
     */
    public void createWinnerBox(String winnerName, String winnerColor){
        winnerOverlay.getChildren().clear();
        winnerOverlay.setAlignment(Pos.CENTER);
        winnerOverlay.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7);");
        winnerOverlay.setPadding(new Insets(50));

        ImageView winnerImage = createWinnerImage(winnerColor);

        Text winnerText = new Text(winnerName + " is the Winner!");
        winnerText.setFont(customFont);
        winnerText.setId("winnerText");

        Text quitMessage = new Text("Press enter to continue");
        quitMessage.setId("quitMessage");

        HBox messageBox = new HBox(20);
        messageBox.setAlignment(Pos.CENTER);
        messageBox.getChildren().addAll(winnerImage, winnerText);

        winnerOverlay.getChildren().addAll(messageBox, quitMessage);
        winnerOverlay.setMouseTransparent(true);

        // Add a key press handler to remove the winnerOverlay when Enter is pressed
        winnerOverlay.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                // Remove the winner box from the scene
                menuView.getMenuLayout().getChildren().remove(winnerOverlay);
                log.info("Winner overlay dismissed with Enter key!");
            }
        });
        // Request focus for the winnerOverlay so it can receive key events
        winnerOverlay.requestFocus();

    }
    public void makeSnakesAndLaddersOverlay(Board board) {
        snakesAndLaddersIcons.getChildren().clear();
        snakesAndLaddersIcons.getStyleClass().add("snakesAndLaddersIcons");

        List<Map<String, Object>> dests = collectActionPaths(board);

        for (Map<String, Object> path : dests) {
            String type = (String) path.get("type");
            int startTile = (int) path.get("start");
            int endTile = (int) path.get("end");

            Point2D start = getTilePosition(startTile, 75, 9);
            Point2D end = getTilePosition(endTile, 75, 9);

            double dx = end.getX() - start.getX();
            double dy = end.getY() - start.getY();
            double length = Math.hypot(dx, dy);
            double angle = Math.toDegrees(Math.atan2(dy, dx));

            // Choose the image
            String imagePath = type.equals("snake") ? "snakeForBoard.png" : "ladderForBoard.png";
            Image image = new Image(getClass().getResourceAsStream(imagePath));
            ImageView imageView = new ImageView(image);

            // Set size: width = length between tiles, height = your image height
            imageView.setFitWidth(length);
            imageView.setFitHeight(50); // Adjust based on your image proportions
            imageView.setPreserveRatio(false);

            // Rotate the image to face the correct direction
            imageView.setRotate(angle);

            // Position imageView so it starts at the starting tile
            imageView.setLayoutX(start.getX());
            imageView.setLayoutY(start.getY());

            // Optional: shift image to center on the line
            imageView.setTranslateX(-imageView.getFitWidth() / 2);
            imageView.setTranslateY(-imageView.getFitHeight() / 2);

            snakesAndLaddersIcons.getChildren().add(imageView);
        }
    }

    public Point2D getTilePosition(int tileNumber, int cellSize, int numRows) {
        int index = tileNumber - 1;  // 0-based index

        int rowFromBottom = index / 10;  // which row, starting from bottom (0 = bottom)
        int row = (numRows - 1) - rowFromBottom;  // convert to top-down row index for JavaFX

        int colInRow = index % 10;

        int col;
        if (rowFromBottom % 2 == 0) {
            // Even row from bottom: left to right
            col = colInRow;
        } else {
            // Odd row from bottom: right to left
            col = 9 - colInRow;
        }

        // Pixel position (center of the tile)
        int x = col * cellSize + cellSize / 2;
        int y = row * cellSize + cellSize / 2;

        return new Point2D(x, y);
    }


    /**
     * Created winner image.
     * @param color the color of the player who won.
     * @return imageview of winner image.
     */
    private ImageView createWinnerImage(String color){
        String winnerImagePath = "/images/winners/" + color.toLowerCase() + "_winner.png";

        Image winnerImage = new Image(Objects.requireNonNull(getClass().getResource(winnerImagePath)).toExternalForm());
        ImageView winnerImageView = new ImageView(winnerImage);
        winnerImageView.setFitHeight(250);
        winnerImageView.setPreserveRatio(true);

        return winnerImageView;
    }

    /**
     * Collects the destinations of action based tiles and ut the information in a Map.
     * @param board the board that is used in the game.
     * @return Map with information about the destinations for each action tile.
     */
    private Map<String, List<Integer>> collectDestinations(Board board){
        List<Integer> snakeDestination = new ArrayList<>();
        List<Integer> ladderDestination = new ArrayList<>();

        //Collect destinations
        for(Tile t : board.getTiles()){
            if(t.getAction() instanceof SnakeAction action){
                snakeDestination.add(action.getDestinationTileId());
            } else if(t.getAction() instanceof LadderAction action){
                ladderDestination.add(action.getDestinationTileId());
            }
        }

        Map<String, List<Integer>> destinations = new HashMap<>();
        destinations.put("snake", snakeDestination);
        destinations.put("ladder", ladderDestination);

        return destinations;
    }

    public List<Map<String, Object>> collectActionPaths(Board board) {
        List<Map<String, Object>> paths = new ArrayList<>();

        for (Tile t : board.getTiles()) {
            int start = t.getId(); // or however you get tile number

            if (t.getAction() instanceof SnakeAction snake) {
                int end = snake.getDestinationTileId();

                Map<String, Object> entry = new HashMap<>();
                entry.put("start", start);
                entry.put("end", end);
                entry.put("type", "snake");

                paths.add(entry);
            } else if (t.getAction() instanceof LadderAction ladder) {
                int end = ladder.getDestinationTileId();

                Map<String, Object> entry = new HashMap<>();
                entry.put("start", start);
                entry.put("end", end);
                entry.put("type", "ladder");

                paths.add(entry);
            }
        }

        return paths;
    }
    /**
     * Adds text to displayInfoBox with the information about the round and the current player.
     * @param message of the players moves.
     */
    public void updateInfoBox(String message){
        Text text = new Text(message);
        text.setId("playerInfo");
        text.setWrappingWidth(200);

        displayInfoBox.getChildren().add(text);
    }


    /**
     * Decorated each tile in the snakes and ladders board according to action tiles, finish tiles and others.
     * @param tileBox the tileBox that is changed in appearance.
     * @param tileAction the action of the tile in question.
     * @param tileId the ID of the tile.
     * @param snakeDestination the destination tiles for snake action tiles.
     * @param ladderDestination the destination tiles for ladder action tiles.
     */
    public void decorateTileBox(VBox tileBox, TileAction tileAction, int tileId,List<Integer> snakeDestination, List<Integer> ladderDestination) {
        tileBox.getChildren().add(new Text(tileId+""));

        for(Integer i:snakeDestination){
            if(tileId == i){
                tileBox.getStyleClass().add("snakeDestBox");
            }
        }
        for(Integer i:ladderDestination){
            if(tileId == i){
                tileBox.getStyleClass().add("ladderDestBox");
            }
        }

        if(tileAction instanceof LadderAction){
            tileBox.getStyleClass().add("ladderBox");
        }
        if(tileAction instanceof LadderAction){
            tileBox.getStyleClass().add("ladderBox");
        }
        else if(tileAction instanceof SnakeAction){
            tileBox.getStyleClass().add("snakeBox");
        }
        else if(tileAction instanceof PortalAction){
            BackgroundImage backgroundImagePortal = new BackgroundImage(
                new Image("images/misc/portal.png"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
            );
            tileBox.setBackground(new Background(backgroundImagePortal));
        }
        else if(tileAction instanceof WinAction){
            BackgroundImage backgroundImageWin = new BackgroundImage(
                new Image("images/tiles/finish.png"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
            );
            tileBox.setBackground(new Background(backgroundImageWin));
        }
        else{
            tileBox.getStyleClass().add("tileBox");
        }
    }


}
