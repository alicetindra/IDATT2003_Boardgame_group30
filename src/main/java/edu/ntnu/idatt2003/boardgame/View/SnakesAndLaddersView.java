package edu.ntnu.idatt2003.boardgame.View;

import edu.ntnu.idatt2003.boardgame.Model.*;
import edu.ntnu.idatt2003.boardgame.Model.actions.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;

import javafx.scene.text.Font;
import javafx.scene.text.Text;



import java.util.*;

import java.util.logging.Logger;

public class SnakesAndLaddersView {
    private MenuView menuView;
    //Logger
    private static final Logger log = Logger.getLogger(SnakesAndLaddersView.class.getName());

    //Buttons
    private final Button startRoundButton = new Button("Roll dice");
    private final Button restartGame = new Button("Restart");

    //Boxes
    private final HBox dieBox = new HBox();
    private VBox rulesColumn = new VBox(30);
    private final VBox displayInfoBox = new VBox();

    private HBox titleBox;
    private final VBox infoColumn = new VBox(30);
    private final VBox winnerOverlay = new VBox(10);

    //Panes
    private final GridPane grid = new GridPane();
    private BorderPane layout;

    //Font
    Font customFont = Font.loadFont(Objects.requireNonNull(getClass().getResource("/font/LuckiestGuy-Regular.ttf")).toExternalForm(),15);

    public void initialize(){
        layout = new BorderPane();
        layout.getStyleClass().add("rootSL");
        createStartButton();
    }

    //Get button methods
    public Button getRestartGameButton() {
        restartGame.setFont(customFont);
        restartGame.setId("restartGameButton");
        return restartGame;
    }
    public Button getStartRoundButton(){
        return startRoundButton;
    }

    //get layouts
    public BorderPane getLayout(){
        return layout;
    }

    public GridPane getGrid(){
        return grid;
    }


    //Get game layout components
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

    public ImageView getPlayerImage(Player p) {
        ImageView imageView = p.getImageView();
        imageView.setFitHeight(30);
        imageView.setPreserveRatio(true);
        return imageView;
    }

    //Create methods
    public BorderPane createSnakesLaddersLayout(GridPane boardGrid, HBox titleWithImage, VBox rulesColumn, VBox infoColumn){
        layout.setTop(titleWithImage);
        layout.setLeft(rulesColumn);
        layout.setCenter(boardGrid);
        layout.setRight(infoColumn);

        BorderPane.setMargin(rulesColumn, new Insets(0,20,0,20));
        BorderPane.setMargin(infoColumn, new Insets(0,20,0,20));

        layout.setId("mainLayout");
        return layout;
    }

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
        }
    }

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

    public void createStartButton(){

        startRoundButton.setFont(customFont);
        startRoundButton.getStyleClass().add("start-round-button");

        VBox.setMargin(startRoundButton, new Insets(20,0,0,0));
    }


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

    private ImageView createWinnerImage(String color){
        String winnerImagePath = "/images/winners/" + color.toLowerCase() + "_winner.png";

        Image winnerImage = new Image(Objects.requireNonNull(getClass().getResource(winnerImagePath)).toExternalForm());
        ImageView winnerImageView = new ImageView(winnerImage);
        winnerImageView.setFitHeight(250);
        winnerImageView.setPreserveRatio(true);

        return winnerImageView;
    }

    //Logic methods
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

    public void updateInfoBox(String message){
        Text text = new Text(message);
        text.setId("playerInfo");
        text.setWrappingWidth(200);

        displayInfoBox.getChildren().add(text);
    }



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
