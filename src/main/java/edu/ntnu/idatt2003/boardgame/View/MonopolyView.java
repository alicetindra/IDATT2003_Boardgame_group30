package edu.ntnu.idatt2003.boardgame.View;

import edu.ntnu.idatt2003.boardgame.Model.Board;
import edu.ntnu.idatt2003.boardgame.Model.Player;
import edu.ntnu.idatt2003.boardgame.Model.Tile;
import edu.ntnu.idatt2003.boardgame.Model.actions.JailAction;
import edu.ntnu.idatt2003.boardgame.Model.actions.PassStartAction;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.Objects;


public class MonopolyView {
    private BorderPane monopolyLayout;
    private GridPane monopolyGrid;
    private StackPane rootLayout;
    private VBox moneyBox = new VBox();;
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

    public Button getStartRoundButton(){
        startRoundButton.getStyleClass().add("start-round-button");
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
        moneyBox.getChildren().clear();
        moneyBox.setAlignment(Pos.CENTER);
        moneyBox.getChildren().add(new Text("This is where the money will be displayed"));
    }

    public void createTitleBox(){
        titleBox.getChildren().clear();
        Text title = new Text("Monopoly");
        title.getStyleClass().add("title");
        title.setFont(customFont);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.getChildren().add(title);
    }

    public void createDiceBox(){
        diceBox.getChildren().clear();
        diceBox.setAlignment(Pos.CENTER);
        diceBox.getChildren().add(new Text("Dice and the current player can be here"));
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

    public ImageView getPlayerImage(Player p) {
            ImageView imageView = p.getImageView();
            imageView.setFitHeight(30);
            imageView.setPreserveRatio(true);
            return imageView;
    }








    public void styliseTileBox(Tile tile){
        tile.getTileBox().getChildren().add(new Text(tile.getId()+""));
        tile.getTileBox().getStyleClass().add("monopolyTileBox");

        if(tile.getAction() instanceof JailAction){
            tile.getTileBox().getStyleClass().add("jailBox");
        }
        if(tile.getAction() instanceof PassStartAction){
            tile.getTileBox().getStyleClass().add("startBox");
        }
    }
}

