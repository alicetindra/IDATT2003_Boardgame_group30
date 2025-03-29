package edu.ntnu.idatt2003.boardgame.View;

import edu.ntnu.idatt2003.boardgame.Model.*;
import edu.ntnu.idatt2003.boardgame.Model.actions.LadderAction;
import edu.ntnu.idatt2003.boardgame.Model.actions.PortalAction;
import edu.ntnu.idatt2003.boardgame.Model.actions.SnakeAction;
import edu.ntnu.idatt2003.boardgame.Model.actions.TileAction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
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
    //Buttons
    private final Button addPlayerButton = new Button("Add Player");

    private final Button makeGameButton = new Button("Start game");

    private final Button startRoundButton = new Button("Start");

    private final Button mainMenuButton = new Button("Main menu");

    //Radio buttons
    private final ToggleGroup toggleGroup = new ToggleGroup();

    private final RadioButton SLButton = new RadioButton("Snakes and ladders");

    private final RadioButton CLButton = new RadioButton("CandyLand");


    // Textfields
    private final TextField diceField = new TextField();

    private final TextField playerName = new TextField("Player name");


    //ComboBoxes
    private final ComboBox<Integer> boardSizeBox = new ComboBox<>();

    UnaryOperator<TextFormatter.Change> filter = change -> {
        String newText = change.getControlNewText();
        if (newText.matches("[1-6]?")) {
            return change;
        }
        return null;
    };

    public ComboBox<String> playerColorBox = new ComboBox<>();


    //Boxes
    private final VBox dieBox = new VBox();

    private final VBox rulesColumn = new VBox(20);

    private final VBox displayInfoBox = new VBox();

    private final VBox userinfoBox = new VBox();

    private HBox titleBox;

    private final VBox infoColumn = new VBox(10);

    //Panes
    private final GridPane grid = new GridPane();

    private final BorderPane layout = new BorderPane();


    //Methods
    public void initialize(){
        SLButton.setToggleGroup(toggleGroup);
        CLButton.setToggleGroup(toggleGroup);
        diceField.setTextFormatter(new TextFormatter<>(filter));
        userinfoBox.getChildren().addAll(SLButton, CLButton, boardSizeBox,diceField, playerName, playerColorBox, addPlayerButton, makeGameButton);
        layout.setCenter(userinfoBox);
        setBoardSizeBox();
        setPlayerColorBox();
        createStartButton();
        createMainMenuButton();
    }

    public BorderPane getLayout(){
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

    public void createMainLayout(GridPane boardGrid, HBox titleWithImage, VBox rulesColumn, VBox infoColumn){
        layout.setTop(titleWithImage);
        layout.setLeft(rulesColumn);
        layout.setCenter(boardGrid);
        layout.setRight(infoColumn);

        BorderPane.setMargin(rulesColumn, new Insets(0,20,0,20));
        BorderPane.setMargin(infoColumn, new Insets(0,20,0,20));

        layout.setId("mainLayout");
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

    public GridPane getGrid(){
        return grid;
    }

    public void decorateTileBox(VBox tileBox, TileAction tileAction, int tileId,List<Integer> snakeDestination, List<Integer> ladderDestination) {
        tileBox.setMinWidth(50);
        tileBox.setMinHeight(50);
        tileBox.setMaxHeight(50);
        tileBox.setMaxWidth(50);
        tileBox.getChildren().add(new Text(tileId+""));

        for(Integer i:snakeDestination){
            if(tileId == i){
                tileBox.getStyleClass().add("snake");
            }
        }
        for(Integer i:ladderDestination){
            if(tileId == i){
                tileBox.getStyleClass().add("ladder");
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
            tileBox.getStyleClass().add("portalBox");
        }
        else{
            tileBox.getStyleClass().add("tileBox");
        }
    }

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

    public void createRulesColumn(){
        Font customFontSubTitle = Font.loadFont(Objects.requireNonNull(getClass().getResource("/font/LuckiestGuy-Regular.ttf")).toExternalForm(),30);

        //Information on rules box to the left

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
    }

    private Text createRuleText(String content) {
        Text rule = new Text(content);
        rule.setWrappingWidth(180);
        rule.getStyleClass().add("infoText");
        return rule;
    }

    public VBox getRulesColumn(){
        return rulesColumn;
    }

    public void createInfoColumn(PlayerHolder playerHolder) throws IOException {
        Font customFontSubTitle = Font.loadFont(Objects.requireNonNull(getClass().getResource("/font/LuckiestGuy-Regular.ttf")).toExternalForm(),30);

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

        displayInfoBox.setAlignment(Pos.CENTER);

        infoColumn.getChildren().addAll(displayInfoBox);

    }

    public VBox getInfoColumn(){
        return infoColumn;
    }

    public ImageView getPlayerImage(Player p) {
        ImageView imageView = p.getImageView();
        imageView.setFitHeight(30);
        imageView.setPreserveRatio(true);
        return imageView;
    }

    public void createStartButton(){
        Font customFontButton = Font.loadFont(
                Objects.requireNonNull(getClass().getResource("/font/LuckiestGuy-Regular.ttf")).toExternalForm(),15);

        startRoundButton.setFont(customFontButton);
        startRoundButton.setId("start-round-button");

        VBox.setMargin(startRoundButton, new Insets(20,0,0,0));
    }

    public Button getStartRoundButton(){
        return startRoundButton;
    }

    public void createMainMenuButton(){
        Font customFontButton = Font.loadFont(Objects.requireNonNull(getClass().getResource("/font/LuckiestGuy-Regular.ttf")).toExternalForm(),15);

        //back to main
        mainMenuButton.setFont(customFontButton);
        mainMenuButton.setId("main-menu-button");

        VBox.setMargin(mainMenuButton, new Insets(20,0,0,0));

    }

    public Button getMainMenuButton(){
        return mainMenuButton;
    }

    public VBox getDisplayInfoBox() {
        return displayInfoBox;
    }

    public VBox getDieBox() {
        return dieBox;
    }

    public void createTitleBox() {
        Font customFontTitle = Font.loadFont(Objects.requireNonNull(getClass().getResource("/font/LuckiestGuy-Regular.ttf")).toExternalForm(),90);

        //Snake image
        ImageView snakeImageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/snake.png")).toExternalForm()));
        snakeImageView.setFitHeight(90);
        snakeImageView.setFitWidth(90);
        snakeImageView.setPreserveRatio(true);

        //Title
        Text title = new Text("Snakes and Ladders");
        title.setFont(customFontTitle);
        title.getStyleClass().add("title");

        //Ladder image
        Image ladderImage = new Image(Objects.requireNonNull(getClass().getResource("/images/ladders.png")).toExternalForm());
        ImageView ladderImageView = new ImageView(ladderImage);
        ladderImageView.setFitHeight(90);
        ladderImageView.setFitWidth(90);
        ladderImageView.setPreserveRatio(true);

        titleBox = new HBox(20, snakeImageView, title, ladderImageView);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setPadding(new Insets(50,0,0,0));
    }

    public HBox getTitleBox(){
        return titleBox;
    }
}
