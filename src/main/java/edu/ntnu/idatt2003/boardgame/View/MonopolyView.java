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


public class MonopolyView {
    private BorderPane monopolyLayout;
    private GridPane monopolyGrid;
    private StackPane rootLayout;

    //Boxes
    private final VBox moneyBox = new VBox(30);
    private final VBox updatedMoneyBox = new VBox(10);
    private final HBox titleBox = new HBox();
    private final VBox diceBox = new VBox(10);
    private final VBox gameUpdates = new VBox(10);
    private final VBox jailButtonsBox = new VBox(10);
    private final VBox houseButtonsBox = new VBox(10);
    private final Button startRoundButton = new Button("Roll dice");
    private final Button buyHouseButton = new Button("Buy house");
    private final Button sellHouseButton = new Button("Sell house");
    private final Button payFeeButton = new Button("Pay fee");
    private final Button rollForSixButton = new Button("Roll for six ");
    private final VBox bankRuptcyBox = new VBox(20);
    private final Text moneyHeader = new Text("Monopoly money");

    Font customFont = Font.loadFont(Objects.requireNonNull(getClass().getResource("/font/LuckiestGuy-Regular.ttf")).toExternalForm(),15);

    public void initialize() {
        rootLayout = new StackPane();
        monopolyLayout = new BorderPane();
        monopolyGrid = new GridPane();
    }
    public Text getReleaseText(Player player){
        Text releaseText = new Text(player.getName()+", "+player.getColor()+" is released from jail");
        releaseText.setId("releaseText");
        return releaseText;
    }

    public VBox getMoneyBox() {
        return moneyBox;
    }

    public VBox getUpdatedMoneyBox() {
        return updatedMoneyBox;
    }

    public VBox getBankRuptcyBox() {
        return bankRuptcyBox;
    }

    public StackPane getRootLayout() {
        return rootLayout;
    }

    public BorderPane getMonopolyLayout() {
        return monopolyLayout;
    }


    public VBox getDiceBox() {
        return diceBox;
    }

    public HBox getTitleBox() {
        return titleBox;
    }

    public VBox getGameUpdates() {
        return gameUpdates;
    }

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

    public void createMoneyBox(){
        moneyBox.getChildren().clear();

        moneyBox.setPrefWidth(300);
        moneyBox.setAlignment(Pos.TOP_CENTER);
        moneyBox.setId("moneyBox");
        moneyBox.setPadding(new Insets(100,0,100,0));

        VBox textBoxMoney = new VBox(20);
        textBoxMoney.setPadding(new Insets(20));
        textBoxMoney.setSpacing(10);
        textBoxMoney.setAlignment(Pos.CENTER);
        textBoxMoney.setId("textBoxMoney");

        moneyHeader.setFont(customFont);
        moneyHeader.getStyleClass().add("subTitle");
        textBoxMoney.getChildren().add(moneyHeader);

        updatedMoneyBox.setAlignment(Pos.CENTER);
        updatedMoneyBox.setId("updatedMoneyBox");

        textBoxMoney.getChildren().add(updatedMoneyBox);
        moneyBox.getChildren().add(textBoxMoney);

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);
        moneyBox.getChildren().add(spacer);


    }

    public void updateMoneyBox(String s){
        updatedMoneyBox.getChildren().clear();
        Text moneyText = new Text(s);
        moneyText.setId("moneyText");
        updatedMoneyBox.getChildren().addAll(moneyText, bankRuptcyBox);
    }

    public void createTitleBox(){
        Text title = new Text("Monopoly");
        title.getStyleClass().add("title");
        title.setFont(customFont);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.getChildren().add(title);
        titleBox.setPadding(new Insets(20));
    }

    public void createDiceBox(){
        diceBox.getChildren().clear();
        diceBox.setAlignment(Pos.CENTER);
        diceBox.setId("diceText");
        buyHouseButton.setDisable(true);
    }

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
        jailButtonsBox.setAlignment(Pos.CENTER);

        infoTextAndButtons.getChildren().add(diceBox);
        infoTextAndButtons.getChildren().add(houseButtonsBox);
        infoTextAndButtons.getChildren().add(jailButtonsBox);
        infoTextAndButtons.getChildren().add(startRoundButton);
        gameUpdates.setId("gameUpdatesBox");

        gameUpdates.getChildren().add(infoTextAndButtons);

    }

    public ImageView getPlayerImage(Player p) {
            ImageView imageView = p.getImageView();
            imageView.setFitHeight(30);
            imageView.setPreserveRatio(true);
            return imageView;
    }


    public void styliseTileBox(Tile tile){
        tile.getTileBox().getChildren().add(new Text(tile.getId()+""));
        tile.getTileBox().getStyleClass().add("monopolyTileBox");

        if(tile.getAction() instanceof DrawCardAction){
            tile.getTileBox().getStyleClass().add("drawCard");
            tile.getTileBox().getChildren().add(new Text("Draw card"));
        }

        if(tile.getId() == 1){
            tile.getTileBox().getStyleClass().add("startTile");
            tile.getTileBox().getChildren().add(new Text("Go!"));
        }
        if(tile.getId() == 2 || tile.getId() == 3){
            tile.getTileBox().getStyleClass().add("chocolateRiver");
        }
        if(tile.getId() == 5 || tile.getId() == 6 || tile.getId() == 7){
            tile.getTileBox().getStyleClass().add("cookieDessert");
        }
        if(tile.getId() == 8){
            tile.getTileBox().getStyleClass().add("jailTile");
            tile.getTileBox().getChildren().add(new Text("Jail"));
        }
        if(tile.getId() == 9 || tile.getId() == 10 || tile.getId() == 12){
            tile.getTileBox().getStyleClass().add("sugarTopMountain");
        }
        if(tile.getId() == 14){
            tile.getTileBox().getStyleClass().add("parkingTile");
            tile.getTileBox().getChildren().add(new Text("Parking"));
        }
        if(tile.getId() == 15 || tile.getId() == 16 || tile.getId() == 17){
            BackgroundImage cloudCastleBackground = new BackgroundImage(
                new Image("images/cloudCastle.PNG"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
            );
            tile.getTileBox().getStyleClass().add("cottonCandyStreet");
            tile.getTileBox().setBackground(new Background(cloudCastleBackground));
        }
        if(tile.getId() == 18 || tile.getId() == 19 || tile.getId() == 20){
            tile.getTileBox().getStyleClass().add("gummyBearForrest");
        }
        if(tile.getId() == 21){
            tile.getTileBox().getStyleClass().add("goToJailTile");
            tile.getTileBox().getChildren().add(new Text("Go to jail!"));
        }
        if(tile.getId() == 23 || tile.getId() == 24 || tile.getId() == 26){
            tile.getTileBox().getStyleClass().add("ocean");
        }

    }
    public Text getWinnerAnnouncement(){
        Text winnerText = new Text(" is the winner!");
        winnerText.setId("announcementTextWinner");
        return winnerText;
    }

    public Node getBankRuptcyText() {
        Text bankRuptcyText = new Text(" went bankrupt");
        bankRuptcyText.setId("bankRuptcyText");
        return bankRuptcyText;
    }
}

