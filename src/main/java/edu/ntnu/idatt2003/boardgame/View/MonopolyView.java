package edu.ntnu.idatt2003.boardgame.View;

import edu.ntnu.idatt2003.boardgame.Model.Board;
import edu.ntnu.idatt2003.boardgame.Model.Tile;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
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
        moneyBox.getChildren().add(moneyBoxText);
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
        diceBox.getChildren().add(diceText);
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
        monopolyLayout.setBottom(buttonBox);
        monopolyLayout.setRight(diceBox);

        rootLayout.getChildren().add(monopolyLayout);
    }



    public void styliseTileBox(Tile tile){
        tile.getTileBox().getChildren().add(new Text(tile.getId()+""));

        if(tile.getId() == 12 || tile.getId() == 14 || tile.getId() == 15){
            BackgroundImage cloudCastleBackground = new BackgroundImage(
                new Image("images/cloudCastle.png"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
            );
            tile.getTileBox().getStyleClass().add("cottonCandyStreet");
            tile.getTileBox().setBackground(new Background(cloudCastleBackground));
        }else{
            tile.getTileBox().getStyleClass().add("monopolyTileBox");
        }





        //Actions decorate the box here                                                                                                     
        //Start tile, jail tile etc.                                                                                                        
    }
}

