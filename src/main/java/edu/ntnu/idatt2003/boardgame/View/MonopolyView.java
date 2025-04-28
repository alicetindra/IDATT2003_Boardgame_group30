package edu.ntnu.idatt2003.boardgame.View;

import edu.ntnu.idatt2003.boardgame.Model.Board;
import edu.ntnu.idatt2003.boardgame.Model.Tile;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;



public class MonopolyView {
    private BorderPane monopolyLayout;
    private GridPane monopolyGrid;
    private StackPane rootLayout;


    private Text title;

    public void initialize() {
        rootLayout = new StackPane();
        monopolyLayout = new BorderPane();
        monopolyGrid = new GridPane();
        createMonopolyLayout();
    }

    public StackPane getRootLayout() {
        return rootLayout;
    }

    public BorderPane getMonopolyLayout() {
        return monopolyLayout;
    }

    public GridPane getGridPane() {
        return monopolyGrid;
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
    }


    public void styliseTileBox(Tile tile){
        tile.getTileBox().getChildren().add(new Text(tile.getId()+""));
        tile.getTileBox().setStyle("-fx-max-height: 50px; -fx-min-height: 50px; -fx-max-width: 50px; -fx-min-width: 50px;-fx-border-color: black; -fx-border-width: 1px;");
        //ACtions decorate the box here
    }


    public void createTitle(){
        title = new Text("Monopoly");
    }

    public void createMonopolyLayout(){
        createTitle();
        monopolyLayout.setTop(title);
        monopolyLayout.setCenter(monopolyGrid);
        rootLayout.getChildren().add(monopolyLayout);
        StackPane.setAlignment(title, Pos.CENTER);
    }

}

