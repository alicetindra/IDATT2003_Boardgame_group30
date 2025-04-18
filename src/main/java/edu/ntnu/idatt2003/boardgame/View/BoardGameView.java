package edu.ntnu.idatt2003.boardgame.View;

import edu.ntnu.idatt2003.boardgame.Model.*;
import edu.ntnu.idatt2003.boardgame.Model.actions.*;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.*;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


import java.util.*;
import java.util.function.UnaryOperator;
import javafx.util.Duration;

public class BoardGameView {
    //Buttons
    private final Button addPlayerButton = new Button("Add Player");

    private final Button makeGameButton = new Button("Start game");

    private final Button startRoundButton = new Button("Roll dice");

    private final Button mainMenuButton = new Button("Main menu");

    private final Button restartGame = new Button("Restart");

    //Radio buttons
    private final ToggleGroup toggleGroup = new ToggleGroup();

    private final RadioButton SLButton = new RadioButton("Snakes and ladders");

    private final RadioButton CLButton = new RadioButton("CandyLand");


    //TextFields
    private final TextField diceField = new TextField();

    private final TextField playerName = new TextField();


    //ComboBoxes
    private final ComboBox<Integer> boardSizeMenu = new ComboBox<>();

    UnaryOperator<TextFormatter.Change> filter = change -> {
        String newText = change.getControlNewText();
        if (newText.matches("[1-6]?")) {
            return change;
        }
        return null;
    };

    public ComboBox<String> playerColorMenu = new ComboBox<>();


    //Boxes
    private final HBox dieBox = new HBox();

    private VBox rulesColumn = new VBox(30);

    private final VBox displayInfoBox = new VBox();

    private final VBox userinfoBox = new VBox(20);

    private HBox titleBox;

    private final VBox infoColumn = new VBox(30);

    private final VBox winnerOverlay = new VBox(10);


    //Panes
    private final GridPane grid = new GridPane();

    private BorderPane layout;

    private StackPane rootLayout;


    //Font
    Font customFont = Font.loadFont(Objects.requireNonNull(getClass().getResource("/font/LuckiestGuy-Regular.ttf")).toExternalForm(),15);


    //Methods
    public void initialize(){
        rootLayout = new StackPane();

        layout = new BorderPane();
        createMainMenu();
        setBoardSizeBox();
        setPlayerColorBox();
        createStartButton();
    }

    public BorderPane getLayout(){
        return layout;
    }

    public StackPane getRootLayout(){
        return rootLayout;
    }

    public void setBoardSizeBox(){
        ObservableList<Integer> options = FXCollections.observableArrayList();
        options.addAll(50,90,110);
        boardSizeMenu.setItems(options);
        boardSizeMenu.getSelectionModel().select(1);
        boardSizeMenu.getStyleClass().add("pullDownMenu");
    }

    public ComboBox<Integer> getBoardSizeMenu() {
        return boardSizeMenu;
    }

    public void setPlayerColorBox(){
        ObservableList<String> colors = FXCollections.observableArrayList();
        colors.addAll("blue","green","red","yellow","pink","black");
        playerColorMenu.setItems(colors);
        playerColorMenu.getSelectionModel().select(0);
        playerColorMenu.getStyleClass().add("pullDownMenu");
    }

    public ComboBox<String> getPlayerColorMenu() {
        return playerColorMenu;
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
        addPlayerButton.setId("addPlayerButton");
        return addPlayerButton;
    }

    public Button getRestartGameButton() {
        restartGame.setFont(customFont);
        restartGame.setId("restartGameButton");
        return restartGame;
    }

    public TextField getPlayerName() {
        return playerName;
    }

    public Button getMakeGameButton() {
        makeGameButton.setId("makeGameButton");
        return makeGameButton;
    }

    public BorderPane createMainLayout(GridPane boardGrid, HBox titleWithImage, VBox rulesColumn, VBox infoColumn){
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

    public GridPane getGrid(){
        return grid;
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
            tileBox.getStyleClass().add("portalBox");
            ImageView portalImage = new ImageView(new Image("images/portal.png"));
            portalImage.setFitHeight(65);
            portalImage.setFitWidth(65);
            tileBox.setAlignment(Pos.CENTER);
            tileBox.getChildren().add(portalImage);
        }
        else if(tileAction instanceof WinAction){
            tileBox.getStyleClass().add("winBox");
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
        rulesColumn.getChildren().add(title);


        Text ruleText = new Text("1. Roll the dice to move when it's your turn.\n\n2. Land on dark green to climb, dark red to slide, dark blue to teleport.\n\n3. The first player at the finish is the winner.");
        ruleText.setWrappingWidth(200);
        ruleText.getStyleClass().add("infoText");
        rulesColumn.getChildren().add(ruleText);

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);
        rulesColumn.getChildren().add(spacer);

        if (layout.getRight() != rulesColumn) {
            layout.setRight(rulesColumn);
        }
    }


    public VBox getRulesColumn(){
        return rulesColumn;
    }

    public void createInfoColumn(PlayerHolder playerHolder){
        infoColumn.getChildren().clear();
        infoColumn.setPrefWidth(300);
        infoColumn.setId("infoColumn");
        infoColumn.setAlignment(Pos.TOP_CENTER);
        infoColumn.setPadding(new Insets(100, 0, 200, 0));

        Text infoTitle = new Text("Player Info");
        infoTitle.setFont(customFont);
        infoTitle.getStyleClass().add("subTitle");

        infoColumn.getChildren().add(infoTitle);

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

            infoColumn.getChildren().add(playerInfoBox);
        }

        Region SpacerAboveDisplayBox = new Region();
        VBox.setVgrow(SpacerAboveDisplayBox, Priority.ALWAYS);
        infoColumn.getChildren().add(SpacerAboveDisplayBox);

        displayInfoBox.setAlignment(Pos.CENTER);
        displayInfoBox.getStyleClass().add("infoBox");

        infoColumn.getChildren().addAll(displayInfoBox);

        if (layout.getRight() != infoColumn) {
            layout.setRight(infoColumn);
        }
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

        startRoundButton.setFont(customFont);
        startRoundButton.setId("start-round-button");

        VBox.setMargin(startRoundButton, new Insets(20,0,0,0));
    }

    public Button getStartRoundButton(){
        return startRoundButton;
    }

    public void createMainMenuButton(){
        mainMenuButton.setFont(customFont);
        mainMenuButton.setId("main-menu-button");

        VBox.setMargin(mainMenuButton, new Insets(20,0,0,0));

    }

    public Button getMainMenuButton(){
        return mainMenuButton;
    }

    public VBox getDisplayInfoBox() {
        return displayInfoBox;
    }

    public void updateInfoBox(String message){
        Text text = new Text(message);
        text.setStyle("-fx-font-size: 14;"
            + "-fx-font-family: Georgia;");
        text.setWrappingWidth(200);

        displayInfoBox.getChildren().add(text);
    }

    public HBox getDieBox() {
        return dieBox;
    }

    public void createTitleBox() {
        //Snake image
        ImageView snakeImageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/snake.png")).toExternalForm()));
        snakeImageView.setFitHeight(90);
        snakeImageView.setFitWidth(90);
        snakeImageView.setPreserveRatio(true);

        //Title
        Text title = new Text("Snakes and Ladders");
        title.setFont(customFont);
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

    public void createMainMenu(){
        SLButton.setToggleGroup(toggleGroup);
        CLButton.setToggleGroup(toggleGroup);
        SLButton.setSelected(true);
        HBox radioButtonBox = new HBox(20);
        radioButtonBox.getChildren().addAll(SLButton, CLButton);
        radioButtonBox.setAlignment(Pos.CENTER);

        diceField.setTextFormatter(new TextFormatter<>(filter));
        diceField.setPromptText("2");

        Text diceText = new Text("Nr of dice (1-6)");
        Text boardText = new Text("Board size");
        Text nameText = new Text("Player name");
        Text colorText = new Text("Player color");

        VBox boardBox = new VBox(10);
        boardBox.getChildren().addAll(boardText, boardSizeMenu);

        VBox diceBox = new VBox(10);
        diceBox.getChildren().addAll(diceText, diceField);

        HBox boardDiceBox = new HBox(30);
        boardDiceBox.setAlignment(Pos.CENTER);
        boardDiceBox.getChildren().addAll(boardBox, diceBox);

        VBox playerBox = new VBox(10);
        playerBox.getChildren().addAll(nameText, playerName);

        VBox colorBox = new VBox(10);
        colorBox.getChildren().addAll(colorText, playerColorMenu);

        HBox nameColorBox = new HBox(30);
        nameColorBox.setAlignment(Pos.CENTER);
        nameColorBox.getChildren().addAll(playerBox, colorBox);

        userinfoBox.getChildren().clear();
        userinfoBox.getChildren().addAll(radioButtonBox, boardDiceBox,nameColorBox, addPlayerButton, new Text(),makeGameButton);
        userinfoBox.setAlignment(Pos.CENTER);
        userinfoBox.setId("userInfoBox");
        //layout.setCenter(userinfoBox);
        rootLayout.getChildren().clear();
        rootLayout.getChildren().add(userinfoBox);
        StackPane.setAlignment(userinfoBox, Pos.CENTER);
    }

    public void makeWinnerBox(String winnerName, String winnerColor){
        winnerOverlay.getChildren().clear();
        winnerOverlay.setAlignment(Pos.CENTER);
        winnerOverlay.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7);");
        winnerOverlay.setPadding(new Insets(50));

        ImageView winnerImage = createWinnerImage(winnerColor);

        Text winnerText = new Text(winnerName + " is the Winner!");
        winnerText.setFont(customFont);
        winnerText.setId("winnerText");

        Text quitMessage = new Text("Press space to continue");
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
                getRootLayout().getChildren().remove(winnerOverlay);
                System.out.println("Winner overlay dismissed with Enter key!");
            }
        });
        // Request focus for the winnerOverlay so it can receive key events
        winnerOverlay.requestFocus();

    }

    public VBox getWinnerBox(){
        return winnerOverlay;
    }

    private ImageView createWinnerImage(String color){
        String winnerImagePath = "/images/" + color.toLowerCase() + "_winner.png";

        Image winnerImage = new Image(Objects.requireNonNull(getClass().getResource(winnerImagePath)).toExternalForm());
        ImageView winnerImageView = new ImageView(winnerImage);
        winnerImageView.setFitHeight(250);
        winnerImageView.setPreserveRatio(true);

        return winnerImageView;
    }


    //confetti
    public void playConfettiEffect() {
        // Skapa en grupp för konfetti
        Group confettiGroup = new Group();

        // Lägg till konfetti i rootLayout
        rootLayout.getChildren().add(confettiGroup);

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
            new KeyFrame(Duration.seconds(5), e -> rootLayout.getChildren().remove(confettiGroup))
        ).play();
    }

}
