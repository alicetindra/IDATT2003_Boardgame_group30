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

import javafx.util.Duration;
import java.util.logging.Logger;

public class SnakesAndLaddersView {
    private static final Logger log = Logger.getLogger(SnakesAndLaddersView.class.getName());
    //Buttons
    private final Button addPlayerButton = new Button("Add Player");
    private final Button makeGameButton = new Button("Start game");
    private final Button startRoundButton = new Button("Roll dice");
    private final Button mainMenuButton = new Button("Main menu");
    private final Button restartGame = new Button("Restart");
    private final Button loadCustomBoardButton = new Button("Load Custom Board");
    private final Button plusOneButton = new Button("+");
    private final Button minusOneButton = new Button("-");
    //Radio buttons
    private final ToggleGroup toggleGroup = new ToggleGroup();
    private final RadioButton SLButton = new RadioButton("Snakes and ladders");
    private final RadioButton CLButton = new RadioButton("CandyLand");
    private final RadioButton customButton = new RadioButton("Custom game");
    //TextFields
    private final TextField diceField = new TextField();
    private final TextField playerName = new TextField();
    //ComboBoxes
    private final ComboBox<Integer> boardSizeMenu = new ComboBox<>();
    public ComboBox<String> playerColorMenu = new ComboBox<>();

    //Boxes
    private final HBox dieBox = new HBox();
    private VBox rulesColumn = new VBox(30);
    private final VBox displayInfoBox = new VBox();
    private final VBox userinfoBox = new VBox(20);
    private HBox titleBox;
    private final VBox infoColumn = new VBox(30);
    private final VBox winnerOverlay = new VBox(10);
    private final HBox diceImagesBox = new HBox(10);
    VBox boardBox = new VBox(10);

    //player list
    private ObservableList<String> playerData = FXCollections.observableArrayList();
    private ListView<String> playerList;

    //Panes
    private final GridPane grid = new GridPane();
    private BorderPane layout;
    private StackPane rootLayout;

    //Font
    Font customFont = Font.loadFont(Objects.requireNonNull(getClass().getResource("/font/LuckiestGuy-Regular.ttf")).toExternalForm(),15);

    public void initialize(){
        rootLayout = new StackPane();

        layout = new BorderPane();
        layout.getStyleClass().add("rootSL");
        createMainMenu();
        setBoardSizeBox();
        setPlayerColorBox();
        createStartButton();
        initializePlayerList();
    }

    //Get methods
    public Button getMainMenuButton(){
        return mainMenuButton;
    }
    public Button getPlusOneButton() {
        plusOneButton.setId("plusOneButton");
        return plusOneButton;
    }
    public Button getMinusOneButton() {
        plusOneButton.setId("minusOneButton");
        return minusOneButton;
    }
    public Button getLoadCustomBoardButton() {
        loadCustomBoardButton.setId("loadCustomBoardButton");
        return loadCustomBoardButton;
    }
    public Button getRestartGameButton() {
        restartGame.setFont(customFont);
        restartGame.setId("restartGameButton");
        return restartGame;
    }
    public Button getAddPlayerButton() {
        addPlayerButton.setId("addPlayerButton");
        return addPlayerButton;
    }
    public Button getMakeGameButton() {
        makeGameButton.setId("makeGameButton");
        return makeGameButton;
    }
    public Button getStartRoundButton(){
        return startRoundButton;
    }

    public RadioButton getCustomRadioButton(){
        return customButton;
    }
    public RadioButton getSLButton() {
        return SLButton;
    }

    public BorderPane getLayout(){
        return layout;
    }
    public StackPane getRootLayout(){
        return rootLayout;
    }
    public GridPane getGrid(){
        return grid;
    }

    public ComboBox<Integer> getBoardSizeMenu() {
        return boardSizeMenu;
    }
    public ComboBox<String> getPlayerColorMenu() {
        return playerColorMenu;
    }

    public TextField getPlayerName() {
        return playerName;
    }
    public TextField getDiceField() {
        return diceField;
    }

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

    public String getGameName(){
        Toggle selectedToggle = toggleGroup.getSelectedToggle();
        RadioButton selectedRadioButton = (RadioButton) selectedToggle;
        return selectedRadioButton.getText();
    }

    public ListView<String> getPlayerList() {
        return playerList;
    }

    public ObservableList<String> getPlayerData(){
        return playerData;
    }

    public ImageView getPlayerImage(Player p) {
        ImageView imageView = p.getImageView();
        imageView.setFitHeight(30);
        imageView.setPreserveRatio(true);
        return imageView;
    }


    //Create methods
    public void setBoardSizeBox(){
        ObservableList<Integer> options = FXCollections.observableArrayList();
        options.addAll(50,90,110);
        boardSizeMenu.setItems(options);
        boardSizeMenu.getSelectionModel().select(1);
        boardSizeMenu.getStyleClass().add("pullDownMenu");
    }

    public void setPlayerColorBox(){
        ObservableList<String> colors = FXCollections.observableArrayList();
        colors.addAll("blue","green","red","yellow","pink","black");
        playerColorMenu.setItems(colors);
        playerColorMenu.getSelectionModel().select(0);
        playerColorMenu.getStyleClass().add("pullDownMenu");
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
        startRoundButton.setId("start-round-button");

        VBox.setMargin(startRoundButton, new Insets(20,0,0,0));
    }

    public void createMainMenuButton(){
        mainMenuButton.setFont(customFont);
        mainMenuButton.setId("main-menu-button");

        VBox.setMargin(mainMenuButton, new Insets(20,0,0,0));

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

    public void createMainMenu(){
        Text titleText = new Text("Choose your game");
        titleText.setFont(customFont);
        titleText.getStyleClass().add("title");
        VBox titleBox = new VBox(20);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.getChildren().addAll(titleText);
        titleBox.setPadding(new Insets(20,0,20,0));

        SLButton.setToggleGroup(toggleGroup);
        SLButton.setId("SLButton");
        CLButton.setToggleGroup(toggleGroup);
        CLButton.setId("CLButton");
        customButton.setToggleGroup(toggleGroup);
        customButton.setId("customButton");

        HBox radioButtonBox = new HBox(20);
        radioButtonBox.setId("radioButtonBox");
        radioButtonBox.getChildren().addAll(SLButton, CLButton, customButton);
        radioButtonBox.setAlignment(Pos.CENTER);

        userinfoBox.getChildren().clear();
        userinfoBox.getChildren().addAll(titleBox, radioButtonBox);
        userinfoBox.setAlignment(Pos.CENTER);
        userinfoBox.setId("userInfoBox");

        rootLayout.getChildren().clear();
        rootLayout.getChildren().addAll(userinfoBox);
        rootLayout.getStyleClass().add("rootMainMenu");

    }

    public void createSLMenu(){
        Text boardText = new Text("Board size");
        boardText.setId("boardSizeText");
        boardBox.getChildren().clear();
        boardBox.getChildren().addAll(boardText, boardSizeMenu);
        createUserMenu();
    }

    public void createCustomMenu(){
        Text fileText = new Text("Upload board");
        fileText.setId("fileText");
        loadCustomBoardButton.setId("loadCustomBoardButton");
        boardBox.getChildren().clear();
        boardBox.getChildren().addAll(fileText, loadCustomBoardButton);
        createUserMenu();
    }

    public void createUserMenu(){
        userinfoBox.getChildren().clear();
        diceField.setDisable(true);
        diceField.setText("2");

        //Title
        Text titleText = new Text("Snakes and Ladders");
        titleText.setFont(customFont);
        titleText.getStyleClass().add("title");
        VBox titleBox = new VBox(20);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.getChildren().addAll(titleText);
        titleBox.setPadding(new Insets(20,0,20,0));

        //Left column
        VBox leftColumn = new VBox(15);
        leftColumn.setAlignment(Pos.TOP_LEFT);

        //Dice section
        Text diceText = new Text("Dice");
        diceText.setId("diceText");
        minusOneButton.setId("minusOneButton");
        plusOneButton.setId("plusOneButton");
        HBox diceHBox = new HBox(10);
        diceHBox.getChildren().addAll(minusOneButton,diceField,plusOneButton);
        VBox diceSection = new VBox(10);
        diceSection.getChildren().addAll(diceText, diceHBox);

        //Player name section
        Text nameText = new Text("Player name");
        nameText.setId("nameText");
        playerName.setMinWidth(120);
        VBox playerNameSection = new VBox(10);
        playerNameSection.getChildren().addAll(nameText, playerName);

        //Player color section
        Text colorText = new Text("Player color");
        colorText.setId("colorText");
        playerColorMenu.setMinWidth(120);
        VBox playerColorSection = new VBox(10);
        playerColorSection.getChildren().addAll(colorText, playerColorMenu);

        //Add addPlayerButton to left column
        addPlayerButton.setMinWidth(150);
        addPlayerButton.setFont(customFont);
        addPlayerButton.setId("addPlayerButton");
        leftColumn.getChildren().addAll(boardBox, diceSection, playerNameSection, playerColorSection, addPlayerButton);

        //Right column player list + dice images
        VBox rightColumn = new VBox(15);
        rightColumn.setPrefWidth(300);
        rightColumn.setAlignment(Pos.TOP_RIGHT);

        //Player list section
        Text playerTitle = new Text("Players");
        playerTitle.setFont(customFont);
        playerTitle.getStyleClass().add("subTitle");

        //Insert list
        ListView<String> playerList = createPlayerList();

        // Dice Images Section
        Text diceImagesTitle = new Text("Dice");
        diceImagesTitle.setFont(customFont);
        diceImagesTitle.getStyleClass().add("subTitle");
        diceImagesBox.setAlignment(Pos.CENTER);
        placeDice();

        //right column
        rightColumn.setAlignment(Pos.TOP_CENTER);
        rightColumn.setSpacing(15);
        rightColumn.getChildren().addAll(playerTitle, playerList, diceImagesTitle, diceImagesBox);

        HBox mainLayout = new HBox(100);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.getChildren().addAll(leftColumn, rightColumn);

        //Start game or go back to main bix
        HBox startOrMain = new HBox(20);
        startOrMain.setAlignment(Pos.CENTER);
        startOrMain.getChildren().addAll(mainMenuButton, makeGameButton);
        makeGameButton.setFont(customFont);
        mainMenuButton.setFont(customFont);
        mainMenuButton.setId("menuButtonBack");

        //Apply layout
        userinfoBox.getChildren().clear();
        userinfoBox.getChildren().addAll(titleBox, mainLayout, startOrMain);

        rootLayout.getChildren().clear();
        rootLayout.getChildren().add(userinfoBox);
        StackPane.setAlignment(userinfoBox, Pos.CENTER);
    }

    private void initializePlayerList(){
        playerList = createPlayerList();
    }

    private ListView<String> createPlayerList(){
        ListView<String> playerList = new ListView<>(playerData);
        playerList.setId("playerList");

        playerList.setFixedCellSize(30);
        playerList.setPrefHeight(6*30+3);

        //Create list with 6 empty rows
        for (int i = 0; i < 6; i++){
            playerData.add("");
        }

        //Alternative row different color
        playerList.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(String player, boolean empty) {
                super.updateItem(player, empty);
                if (empty || player == null) {
                    setText(null);
                    setStyle(""); //
                } else {
                    setText(player.isBlank() ? "" : player);

                    // alternating row different color
                    if (getIndex() % 2 == 0) {
                        setStyle("-fx-background-color: rgba(176,197,221,0.75);");
                    } else {
                        setStyle("-fx-background-color: rgba(255,255,255,0.7);");
                    }
                }
            }
        });

        return playerList;
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
                getRootLayout().getChildren().remove(winnerOverlay);
                log.info("Winner overlay dismissed with Enter key!");
            }
        });
        // Request focus for the winnerOverlay so it can receive key events
        winnerOverlay.requestFocus();

    }

    private ImageView createWinnerImage(String color){
        String winnerImagePath = "/images/" + color.toLowerCase() + "_winner.png";

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

    public void placeDice(){
        diceImagesBox.getChildren().clear();
        for(int i = 1; i<= Integer.parseInt(diceField.getText()); i++){
            ImageView diceImageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/dice"+i+".png")).toExternalForm()));
            diceImageView.setFitHeight(40);
            diceImageView.setPreserveRatio(true);
            diceImagesBox.getChildren().add(diceImageView);
        }
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
            /*
            tileBox.getStyleClass().add("portalBox");
            ImageView portalImage = new ImageView(new Image("images/portal.png"));
            portalImage.setFitHeight(65);
            portalImage.setFitWidth(65);
            tileBox.setAlignment(Pos.CENTER);
            tileBox.getChildren().add(portalImage);
             */
            BackgroundImage backgroundImagePortal = new BackgroundImage(
                new Image("images/portal.png"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
            );
            tileBox.setBackground(new Background(backgroundImagePortal));
        }
        else if(tileAction instanceof WinAction){
            BackgroundImage backgroundImageWin = new BackgroundImage(
                new Image("images/finish.png"),
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
