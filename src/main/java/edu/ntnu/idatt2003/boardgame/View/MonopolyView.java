package edu.ntnu.idatt2003.boardgame.View;

import edu.ntnu.idatt2003.boardgame.Model.Board;
import edu.ntnu.idatt2003.boardgame.Model.Player;
import edu.ntnu.idatt2003.boardgame.Model.Tile;
import edu.ntnu.idatt2003.boardgame.Model.actions.DrawCardAction;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.Objects;

/**
 * The {@code MonopolyView} class is responsible for handling the visual
 * representation of the Monopoly-style game board and in-game user interface.
 * <p>
 * It displays the current state of the board, including tiles, players,
 * dice rolls, and actions. This class forms the View component in the
 * Model-View-Controller (MVC) design pattern and focuses solely on rendering
 * output without managing game logic.
 * </p>
 *
 * <p>
 * Key responsibilities include:
 *   <li>Displaying the Monopoly board and its tiles</li>
 *   <li>Updating player positions visually as the game progresses</li>
 *   <li>Rendering dice roll outcomes and other animations</li>
 *   <li>Showing information about player turns, actions, and tile effects</li>
 * </p>
 */
public class MonopolyView {

    /**
     * Panes used in monopoly game.
     */
    private BorderPane monopolyLayout;
    private GridPane monopolyGrid;
    private StackPane rootLayout;

    /**
     * Boxes used in monopoly view.
     */
    private final VBox moneyBox = new VBox(30);
    private final VBox updatedMoneyBox = new VBox(10);
    private final HBox titleBox = new HBox();
    private final VBox diceBox = new VBox(10);
    private final VBox nextPlayerInfoBox = new VBox(10);
    private final VBox gameUpdates = new VBox(10);
    private final VBox jailButtonsBox = new VBox(10);
    private final VBox houseButtonsBox = new VBox(10);
    private final VBox bankruptcyBox = new VBox(20);

    /**
     * Buttons used in the monopoly view.
     */
    private final Button startRoundButton = new Button("Roll dice");
    private final Button buyHouseButton = new Button("Buy house");
    private final Button sellHouseButton = new Button("Sell house");
    private final Button payFeeButton = new Button("Pay fee");
    private final Button rollForSixButton = new Button("Roll for six ");

    /**
     * Texts in monopoly view
     */
    private final Text moneyHeader = new Text("Monopoly money");
    private final Text bankruptcyHeader = new Text("Bankrupt players:");

    /**
     * Our custom font used in titles.
     */
    private final Font customFont = Font.loadFont(Objects.requireNonNull(getClass().getResource("/font/LuckiestGuy-Regular.ttf")).toExternalForm(),15);

    /**
     * Initializes the monopoly view by creating the different panes and layouts.
     */
    public void initialize() {
        rootLayout = new StackPane();
        monopolyLayout = new BorderPane();
        monopolyGrid = new GridPane();
        monopolyLayout.getStyleClass().add("rootSL");
    }

    /**
     * Getter for the release text displaying when a player is released from jail.
     * @param player who is released from jail.
     * @return text with information.
     */
    public Text getReleaseText(Player player){
        Text releaseText = new Text(player.getName()+", "+player.getColor()+" is released from jail");
        releaseText.setId("releaseText");
        return releaseText;
    }

    /**
     * Getter for boxes
     * @return each box.
     */
    public VBox getMoneyBox() {
        return moneyBox;
    }
    public VBox getUpdatedMoneyBox() {
        return updatedMoneyBox;
    }
    public VBox getBankruptcyBox() {
        return bankruptcyBox;
    }
    public VBox getDiceBox() {
        return diceBox;
    }
    public VBox getNextPlayerInfoBox(){
        nextPlayerInfoBox.setId("nextPlayerInfoBox");
        return nextPlayerInfoBox;
    }
    public HBox getTitleBox() {
        return titleBox;
    }
    public VBox getGameUpdates() {
        return gameUpdates;
    }

    /**
     * Getter for panes, layouts.
     * @return layouts.
     */
    public StackPane getRootLayout() {
        return rootLayout;
    }
    public BorderPane getMonopolyLayout() {
        return monopolyLayout;
    }


    /**
     * Getter for all buttons in monopoly view.
     * @return each button.
     */
    public Button getStartRoundButton(){
        startRoundButton.setId("monopolyButtons");
        startRoundButton.setFont(customFont);
        startRoundButton.setAlignment(Pos.CENTER);
        return startRoundButton;
    }
    public Button getBuyHouseButton(){
        buyHouseButton.setId("monopolyButtons");
        buyHouseButton.setFont(customFont);
        buyHouseButton.setAlignment(Pos.CENTER);
        return buyHouseButton;
    }
    public Button getSellHouseButton(){
        sellHouseButton.setId("monopolyButtons");
        sellHouseButton.setFont(customFont);
        return sellHouseButton;
    }

    /**
     * Creates monopoly board grid.
     * @param width width of grid.
     * @param height height of grid.
     * @param board The board created for the game.
     */
    public void createBoardGrid(int width, int height, Board board) {
        monopolyGrid.setGridLinesVisible(true);
        monopolyGrid.getChildren().clear();

        int row = height - 1;
        int col = width - 1;

        int tileCount = (width + width + height + height) - 4;

        for (int tileIndex = 0; tileIndex < tileCount; tileIndex++) {
            VBox box = new VBox();
            Tile tile = board.getTiles().get(tileIndex);
            tile.setTileBox(box);
            styliseTileBox(tile);
            monopolyGrid.add(tile.getTileBox(), col, row);

            if (row == height - 1 && col > 0) {
                col--;
            } else if (col == 0 && row > 0) {
                row--;
            } else if (row == 0 && col < width - 1) {
                col++;
            } else if (col == width - 1 && row < height - 1) {
                row++;
            }
        }
        monopolyGrid.setAlignment(Pos.CENTER);
    }

    /**
     * Creates money box. Including information about the player and their money. As well as bankrupt players.
     */
    public void createMoneyBox(){
        moneyBox.getChildren().clear();

        moneyBox.setPrefWidth(300);
        moneyBox.setAlignment(Pos.TOP_CENTER);
        //moneyBox.setId("moneyBox");
        moneyBox.setPadding(new Insets(100,0,100,0));

        VBox textBoxMoney = new VBox(10);
        textBoxMoney.setPadding(new Insets(10));
        textBoxMoney.setSpacing(10);
        textBoxMoney.setAlignment(Pos.TOP_CENTER);
        textBoxMoney.setId("textBoxMoney");

        textBoxMoney.setPrefHeight(300);


        moneyHeader.setFont(customFont);
        moneyHeader.getStyleClass().add("subTitle");
        textBoxMoney.getChildren().add(moneyHeader);

        updatedMoneyBox.setAlignment(Pos.TOP_CENTER);
        updatedMoneyBox.setId("updatedMoneyBox");

        textBoxMoney.getChildren().add(updatedMoneyBox);
        moneyBox.getChildren().add(textBoxMoney);

        bankruptcyBox.setAlignment(Pos.CENTER);
        bankruptcyBox.setId("bankruptcyBox");
        bankruptcyBox.setPadding(new Insets(20));
        bankruptcyBox.setSpacing(10);
        bankruptcyBox.getChildren().add(getBankruptcyText());
        moneyBox.getChildren().add(bankruptcyBox);


    }

    /**
     * Creates updated money box when players' money is changed.
     * @param s the sting with the new correct balance for the player's money.
     */
    public void updateMoneyBox(String s){
        updatedMoneyBox.getChildren().clear();
        Text moneyText = new Text(s);
        moneyText.setId("moneyText");
        moneyText.setWrappingWidth(200);
        updatedMoneyBox.getChildren().addAll(moneyText);
    }

    /**
     * Creates header for monopoly game.
     */
    public void createTitleBox(){
        Text title = new Text("Monopoly");
        title.getStyleClass().add("title");
        title.setFont(customFont);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.getChildren().add(title);
        titleBox.setPadding(new Insets(20));
    }

    /**
     * Creates dice box and disables buy house button.
     */
    public void createDiceBox(){
        diceBox.getChildren().clear();
        diceBox.setAlignment(Pos.CENTER);
        diceBox.setId("diceText");
        buyHouseButton.setDisable(true);
    }

    /**
     * getters for buttons
     * @return each button in monopoly view.
     */
    public Button getPayFeeButton(){
        payFeeButton.setId("monopolyButtons");
        payFeeButton.setFont(customFont);
        payFeeButton.setAlignment(Pos.CENTER);
        return payFeeButton;
    }
    public Button getRollForSixButton(){
        rollForSixButton.setId("monopolyButtons");
        rollForSixButton.setFont(customFont);
        rollForSixButton.setAlignment(Pos.CENTER);
        return rollForSixButton;
    }
    public VBox getJailButtonsBox(){
        return jailButtonsBox;
    }
    public VBox getHouseButtonsBox(){
        return houseButtonsBox;
    }

    /**
     * Creates the monopoly layout with all it's components.
     */
    public void createMonopolyLayout(){
        monopolyLayout.getChildren().clear();

        createTitleBox();
        createMoneyBox();
        createDiceBox();
        createGameUpdatesBox();

        monopolyLayout.setTop(titleBox);
        monopolyLayout.setCenter(monopolyGrid);
        monopolyLayout.setLeft(moneyBox);
        monopolyLayout.setRight(gameUpdates);

        BorderPane.setMargin(moneyBox, new Insets(0,20,0,20));
        BorderPane.setMargin(gameUpdates, new Insets(0,20,0,20));

        rootLayout.getChildren().add(monopolyLayout);
    }

    /**
     * Creates game updates box. Including information about the current round.
     */
    private void createGameUpdatesBox() {
        gameUpdates.getChildren().clear();
        gameUpdates.setPrefWidth(300);
        gameUpdates.setAlignment(Pos.TOP_CENTER);
        gameUpdates.setPadding(new Insets(100,0,100,0));

        VBox infoTextAndButtons = new VBox(20);
        infoTextAndButtons.setPadding(new Insets(20));
        infoTextAndButtons.setSpacing(10);
        infoTextAndButtons.setAlignment(Pos.CENTER);
        infoTextAndButtons.setId("infoTextAndButtons");

        Text infoText = new Text("Round Info");
        infoText.setFont(customFont);
        infoText.getStyleClass().add("subTitle");

        infoTextAndButtons.getChildren().add(infoText);

        houseButtonsBox.getChildren().add(buyHouseButton);
        houseButtonsBox.setAlignment(Pos.CENTER);
        nextPlayerInfoBox.setAlignment(Pos.CENTER);
        jailButtonsBox.setAlignment(Pos.CENTER);

        infoTextAndButtons.getChildren().add(diceBox);
        infoTextAndButtons.getChildren().add(jailButtonsBox);
        infoTextAndButtons.getChildren().add(houseButtonsBox);

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);
        infoTextAndButtons.getChildren().add(spacer);

        infoTextAndButtons.getChildren().add(nextPlayerInfoBox);
        infoTextAndButtons.getChildren().add(startRoundButton);
        gameUpdates.setId("gameUpdatesBox");

        gameUpdates.getChildren().add(infoTextAndButtons);

    }

    /**
     * Fetches player image.
     * @param p the player whose image is fetched.
     * @return Imageview of the player image.
     */
    public ImageView getPlayerImage(Player p) {
            ImageView imageView = p.getImageView();
            imageView.setFitHeight(30);
            imageView.setPreserveRatio(true);
            return imageView;
    }

    /**
     * Set the styles for each tile in the monopoly board.
     * @param tile that is changed.
     */
    public void styliseTileBox(Tile tile){
        tile.getTileBox().getChildren().add(new Text(tile.getId()+""));
        tile.getTileBox().getStyleClass().add("monopolyTileBox");

        if(tile.getAction() instanceof DrawCardAction){
            BackgroundImage cardBackground = new BackgroundImage(
                new Image("images/tiles/card.png"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
            );
            tile.getTileBox().setBackground(new Background(cardBackground));
        }

        if(tile.getId() == 1){
            BackgroundImage startBackground = new BackgroundImage(
                new Image("images/tiles/Start.png"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
            );
            tile.getTileBox().setBackground(new Background(startBackground));
        }
        if(tile.getId() == 2 || tile.getId() == 3){
            BackgroundImage chocolateBackground = new BackgroundImage(
                new Image("images/tiles/chocolate.png"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
            );
            tile.getTileBox().setBackground(new Background(chocolateBackground));
        }
        if(tile.getId() == 5 || tile.getId() == 6 || tile.getId() == 7){
            BackgroundImage cookieBackground = new BackgroundImage(
                new Image("images/tiles/dessert.png"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
            );
            tile.getTileBox().setBackground(new Background(cookieBackground));
        }
        if(tile.getId() == 8){
            BackgroundImage jailBackground = new BackgroundImage(
                new Image("images/tiles/jailTile.png"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
            );
            tile.getTileBox().setBackground(new Background(jailBackground));
        }
        if(tile.getId() == 9 || tile.getId() == 10 || tile.getId() == 12){
            BackgroundImage mountainBackground = new BackgroundImage(
                new Image("images/tiles/sugar_mountain.png"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
            );
            tile.getTileBox().setBackground(new Background(mountainBackground));
        }
        if(tile.getId() == 14){
            BackgroundImage parkBackground = new BackgroundImage(
                new Image("images/tiles/Park.png"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
            );
            tile.getTileBox().setBackground(new Background(parkBackground));
        }
        if(tile.getId() == 13 || tile.getId() == 15 || tile.getId() == 16){
            BackgroundImage cloudCastleBackground = new BackgroundImage(
                new Image("images/tiles/cotton.png"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
            );
            tile.getTileBox().setBackground(new Background(cloudCastleBackground));
        }
        if(tile.getId() == 18 || tile.getId() == 19 || tile.getId() == 20){
            BackgroundImage forestBackground = new BackgroundImage(
                new Image("images/tiles/forest.png"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
            );
            tile.getTileBox().setBackground(new Background(forestBackground));
        }
        if(tile.getId() == 21){
            BackgroundImage goToJailBackground = new BackgroundImage(
                new Image("images/tiles/ToJail.png"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
            );
            tile.getTileBox().setBackground(new Background(goToJailBackground));
        }
        if(tile.getId() == 23 || tile.getId() == 24 || tile.getId() == 26){
            BackgroundImage oceanBackground = new BackgroundImage(
                new Image("images/tiles/ocean.png"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
            );
            tile.getTileBox().setBackground(new Background(oceanBackground));
        }

    }

    /**
     * Getter for winner announcement text.
     * @return text with winner announcement.
     */
    public Text getWinnerAnnouncement(){
        Text winnerText = new Text(" Winner!");
        winnerText.setId("announcementTextWinner");
        return winnerText;
    }

    /**
     * Gets node for bankruptcy text.
     * @return node bankruptcy header.
     */
    public Node getBankruptcyText() {
        bankruptcyHeader.setFont(customFont);
        bankruptcyHeader.getStyleClass().add("subTitle");
        return bankruptcyHeader;
    }
}

