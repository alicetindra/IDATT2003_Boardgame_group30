package edu.ntnu.idatt2003.boardgame.View;

import edu.ntnu.idatt2003.boardgame.Model.Board;
import edu.ntnu.idatt2003.boardgame.Model.Player;
import edu.ntnu.idatt2003.boardgame.Model.Tile;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
    private VBox moneyBox;
    private HBox buttonBox = new HBox();
    private HBox titleBox = new HBox();
    private VBox diceBox = new VBox();
    private final Button startRoundButton = new Button("Roll dice");


    Font customFont = Font.loadFont(Objects.requireNonNull(getClass().getResource("/font/LuckiestGuy-Regular.ttf")).toExternalForm(),15);

    public void initialize() {
        rootLayout = new StackPane();
        monopolyLayout = new BorderPane();
        monopolyGrid = new GridPane();
    }

    public VBox getMoneyBox() {
        return moneyBox;
    }

    public StackPane getRootLayout() {
        return rootLayout;
    }

    public BorderPane getMonopolyLayout() {
        return monopolyLayout;
    }

    public HBox getButtonBox(){
        return buttonBox;
    }

    public VBox getDiceBox() {
        return diceBox;
    }

    public GridPane getMonopolyGrid() {
        return monopolyGrid;
    }

    public HBox getTitleBox() {
        return titleBox;
    }

    public Button getStartRoundButton(){
        startRoundButton.setFont(customFont);
        startRoundButton.setId("start-round-button-monopoly");
        VBox.setMargin(startRoundButton, new Insets(20,0,0,0));
        return startRoundButton;
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
        Text moneyBoxText = new Text("This is where the money will be displayed");
        moneyBoxText.setId("moneyBoxText");
        moneyBox = new VBox();
        moneyBox.setAlignment(Pos.CENTER);
        moneyBox.getChildren().addAll(moneyBoxText);
    }

    public void createTitleBox(){
        Text title = new Text("Monopoly");
        title.getStyleClass().add("title");
        title.setFont(customFont);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.getChildren().add(title);
    }

    public void createDiceBox(){
        Text diceText = new Text("Dice and the current player can be here");
        diceText.setId("diceTextM");
        diceBox.getChildren().clear();
        diceBox.setAlignment(Pos.CENTER);
        diceBox.getChildren().addAll(diceText, startRoundButton);
    }

    public void createMonopolyLayout(){
        monopolyLayout.getChildren().clear();
        buttonBox.setAlignment(Pos.CENTER);

        createTitleBox();
        createMoneyBox();
        createDiceBox();

        monopolyLayout.setTop(titleBox);
        monopolyLayout.setCenter(monopolyGrid);
        monopolyLayout.setLeft(moneyBox);
        monopolyLayout.setRight(diceBox);

        rootLayout.getChildren().add(monopolyLayout);
    }

    public ImageView getPlayerImage(Player p) {
        ImageView imageView = p.getImageView();
        imageView.setFitHeight(30);
        imageView.setPreserveRatio(true);
        return imageView;
    }


    public void styliseTileBox(Tile tile){
        tile.getTileBox().getChildren().add(new Text(tile.getId()+""));

        if(tile.getId() == 1){
            tile.getTileBox().getStyleClass().add("startBox");
        }
        else if(tile.getId() == 2 || tile.getId() == 3){
            tile.getTileBox().getStyleClass().add("chocolateRiver");
        }
        else if(tile.getId() == 5 || tile.getId() == 6 || tile.getId() == 7){
            tile.getTileBox().getStyleClass().add("cookieDessert");
        } else if (tile.getId() == 8) {
            tile.getTileBox().getStyleClass().add("goToJail");
        } else if(tile.getId() == 9 || tile.getId() == 10 || tile.getId() == 12){
            tile.getTileBox().getStyleClass().add("sugarTopMountain");
        } else if (tile.getId()==13) {
            tile.getTileBox().getStyleClass().add("parkBox");
        } else if(tile.getId() == 14 || tile.getId() == 15 || tile.getId() == 17){
            BackgroundImage cloudCastleBackground = new BackgroundImage(
                new Image("images/cloudCastle.png"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
            );
            tile.getTileBox().getStyleClass().add("cottonCandyStreet");
            tile.getTileBox().setBackground(new Background(cloudCastleBackground));
        }
        else if(tile.getId() == 18 || tile.getId() == 19 || tile.getId() == 21){
            tile.getTileBox().getStyleClass().add("gummyBearForrest");
        } else if (tile.getId() == 20) {
            tile.getTileBox().getStyleClass().add("jail");
        } else if(tile.getId() == 23 || tile.getId() == 24){
            tile.getTileBox().getStyleClass().add("ocean");
        }
        else if(tile.getId() == 4 || tile.getId() == 11 || tile.getId() == 16 || tile.getId() == 22){
            tile.getTileBox().getStyleClass().add("cardBoxes");
        }
        else{
            tile.getTileBox().getStyleClass().add("monopolyTileBox");
        }





        //Actions decorate the box here                                                                                                     
        //Start tile, jail tile etc.                                                                                                        
    }
}

