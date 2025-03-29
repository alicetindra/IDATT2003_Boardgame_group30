package edu.ntnu.idatt2003.boardgame.View;

import edu.ntnu.idatt2003.boardgame.Model.*;
import edu.ntnu.idatt2003.boardgame.Model.actions.LadderAction;
import edu.ntnu.idatt2003.boardgame.Model.actions.PortalAction;
import edu.ntnu.idatt2003.boardgame.Model.actions.SnakeAction;
import edu.ntnu.idatt2003.boardgame.Model.actions.TileAction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import javafx.scene.text.Font;
import javafx.scene.text.Text;


import java.io.IOException;
import java.util.*;
import java.util.function.UnaryOperator;

public class BoardGameView {
    private final ToggleGroup toggleGroup = new ToggleGroup();
    private final RadioButton SLButton = new RadioButton("Snakes and ladders");
    private final RadioButton CLButton = new RadioButton("CandyLand");

    private final ComboBox<Integer> boardSizeBox = new ComboBox<>();

    private final TextField diceField = new TextField();
    VBox dieBox;

    UnaryOperator<TextFormatter.Change> filter = change -> {
        String newText = change.getControlNewText();
        if (newText.matches("[1-6]?")) {
            return change;
        }
        return null;
    };

    private final TextField playerName = new TextField("Player name");

    public ComboBox<String> playerColorBox = new ComboBox<>();

    private final Button addPlayerButton = new Button("Add Player");

    private final Button makeGameButton = new Button("Start game");

    private final Button startbutton = new Button("Roll dice!");
    private final Button startRoundButton = new Button("Start");

    private final VBox boardBox = new VBox();

    private final VBox displayInfoBox = new VBox();

    private final VBox layout = new VBox(10, SLButton, CLButton, boardSizeBox,diceField, playerName, playerColorBox, addPlayerButton, makeGameButton);


    public void initialize(){
        SLButton.setToggleGroup(toggleGroup);
        CLButton.setToggleGroup(toggleGroup);
        layout.setAlignment(Pos.CENTER);
        boardBox.setAlignment(Pos.CENTER);
        diceField.setTextFormatter(new TextFormatter<>(filter));
        setBoardSizeBox();
        setPlayerColorBox();
    }


    public VBox getLayout(){
        return layout;
    }

    public void setBoardSizeBox(){
        ObservableList<Integer> options = FXCollections.observableArrayList();
        options.addAll(50,90,110);
        boardSizeBox.setItems(options);
        //Setting the default value to 90 tiles
        boardSizeBox.getSelectionModel().select(1);
    }

    public ComboBox<Integer> getBoardSizeBox() {
        return boardSizeBox;
    }
    public void setPlayerColorBox(){
        ObservableList<String> colors = FXCollections.observableArrayList();
        colors.addAll("blue","green","red","yellow","pink","black");
        playerColorBox.setItems(colors);
        //Setting the default value to the first color in the list
        playerColorBox.getSelectionModel().select(0);
    }

    public ComboBox<String> getPlayerColorBox() {
        return playerColorBox;
    }

    public String getGameName(){
        Toggle selectedToggle = toggleGroup.getSelectedToggle();
        RadioButton selectedRadioButton = (RadioButton) selectedToggle;
        return selectedRadioButton.getText();
    }

    public TextField getDiceField() {
        diceField.setMaxWidth(50);
        return diceField;
    }

    public Button getAddPlayerButton() {
        return addPlayerButton;
    }

    public TextField getPlayerName() {
        return playerName;
    }

    public Button getMakeGameButton() {
        return makeGameButton;
    }

    public void setBoardBox(Board board) {
        HBox rowBox = new HBox();
        int i = 0;
        for(Tile t: board.getTiles().reversed()) {
            if(i % 10 == 0){
                rowBox = new HBox();
            }
            rowBox.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            if(i %20 == 0){
                rowBox.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
            }
            VBox tileBox = new VBox();
            t.setTileBox(tileBox);
            decorateTileBox(tileBox, t.getAction(),t.getId());
            rowBox.getChildren().add(tileBox);
            if(i % 10 == 0){
                boardBox.getChildren().add(rowBox);
            }
            i++;
        }
    }

    public VBox getBoardBox() {
        return boardBox;
    }

    public BorderPane createMainLayout(GridPane boardGrid, HBox titleWithImage, VBox rulesColumn, VBox infoColumn){
        BorderPane mainLayout = new BorderPane();
        mainLayout.setTop(titleWithImage);
        mainLayout.setLeft(rulesColumn);
        mainLayout.setCenter(boardGrid);
        mainLayout.setRight(infoColumn);

        BorderPane.setMargin(rulesColumn, new Insets(0,20,0,20));
        BorderPane.setMargin(infoColumn, new Insets(0,20,0,20));

        mainLayout.setId("mainLayout");
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

            VBox tileBox = new VBox();
            decorateTileBox(tileBox, tile.getAction(), tile.getId());
            tile.setTileBox(tileBox);


            grid.add(tileBox, col, row);
        }
        return grid;
    }

    public void decorateTileBox(VBox tileBox, TileAction tileAction, int tileId) {
        tileBox.setMinWidth(50);
        tileBox.setMinHeight(50);
        tileBox.setMaxHeight(50);
        tileBox.setMaxWidth(50);
        tileBox.getChildren().add(new Text(tileId+""));

        if(tileAction instanceof LadderAction){
            tileBox.setStyle("-fx-background-color: #21ac2c;-fx-border-color: #000000;");
        }
        else if(tileAction instanceof SnakeAction){
            tileBox.setStyle("-fx-background-color: #d33358;-fx-border-color: #0b0b0b;");
        }
        else if(tileAction instanceof PortalAction){
            tileBox.setStyle("-fx-background-color: #1781fb;-fx-border-color: #0b0b0b;");
        }
        /*


        else if(tileAction instanceof WinAction){
            tileBox.setStyle("-fx-background-color: #fff511;-fx-border-color: #0b0b0b;");
        } */
        else{
            tileBox.setStyle("-fx-background-color: #c0c0c1;-fx-border-color: #151515;");
        }
    }

    public Button getStartbutton() {
        return startbutton;
    }


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


    public VBox createRulesColumn(){
        Font customFontSubTitle = Font.loadFont(Objects.requireNonNull(getClass().getResource("/font/LuckiestGuy-Regular.ttf")).toExternalForm(),30);

        //Information on rules box to the left
        VBox rulesColumn = new VBox(20);
        rulesColumn.setPrefWidth(300);
        rulesColumn.setId("rulesColumn");
        rulesColumn.setPadding(new Insets(100,0,100,0));

        Text rulesTitle = new Text("Game Rules");
        rulesTitle.setFont(customFontSubTitle);
        rulesTitle.getStyleClass().add("subTitle");

        Text rule1 = createRuleText("1. Roll the dice to move when it's your turn.");
        Text rule2 = createRuleText("2. Land on dark green to climb, dark red to slide, dark blue to teleport.");
        Text rule3 = createRuleText("3. The first player at the finish is the winner.");

        rulesColumn.getChildren().addAll(rulesTitle, rule1, rule2, rule3);

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);
        rulesColumn.getChildren().add(spacer);

        return rulesColumn;
    }

    private Text createRuleText(String content) {
        Text rule = new Text(content);
        rule.setWrappingWidth(180);
        rule.getStyleClass().add("infoText");
        return rule;
    }

    public VBox createInfoColumn(PlayerHolder playerHolder) throws IOException {
        Font customFontSubTitle = Font.loadFont(Objects.requireNonNull(getClass().getResource("/font/LuckiestGuy-Regular.ttf")).toExternalForm(),30);

        VBox infoColumn = new VBox(10);
        infoColumn.setId("infoColumn");
        infoColumn.setAlignment(Pos.TOP_CENTER);

        infoColumn.setPadding(new Insets(100,0,200,0));

        Text infoTitle = new Text("Player Info");
        infoTitle.setFont(customFontSubTitle);
        infoTitle.getStyleClass().add("subTitle");

        infoColumn.getChildren().add(infoTitle);

        for(Player p: playerHolder.getPlayers()){
            HBox playerInfoBox = new HBox(10);
            playerInfoBox.setAlignment(Pos.CENTER);

            Text playerName = new Text(" " + p.getName());
            playerName.getStyleClass().add("infoText");
            playerInfoBox.getChildren().addAll(getPlayerImage(p), playerName);
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

    private ImageView getPlayerImage(Player p) {
        ImageView imageView = p.getImageView();
        imageView.setFitHeight(30);
        imageView.setPreserveRatio(true);
        return imageView;
    }


    public Button createStartButton(){
        Font customFontButton = Font.loadFont(
                Objects.requireNonNull(getClass().getResource("/font/LuckiestGuy-Regular.ttf")).toExternalForm(),15);

        startRoundButton.setFont(customFontButton);
        startRoundButton.setId("start-round-button");

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
        mainMenuButton.setId("main-menu-button");

        VBox.setMargin(mainMenuButton, new Insets(20,0,0,0));

        return mainMenuButton;
    }

    public VBox getDisplayInfoBox() {
        return displayInfoBox;
    }

    public VBox getDieBox() {
        return dieBox;
    }
}
