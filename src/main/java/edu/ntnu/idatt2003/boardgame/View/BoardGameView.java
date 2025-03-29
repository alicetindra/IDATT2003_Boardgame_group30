package edu.ntnu.idatt2003.boardgame.View;

import edu.ntnu.idatt2003.boardgame.Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javafx.scene.text.Text;


import java.util.function.UnaryOperator;

public class BoardGameView {
    private final ToggleGroup toggleGroup = new ToggleGroup();
    private final RadioButton SLButton = new RadioButton("Snakes and ladders");
    private final RadioButton CLButton = new RadioButton("CandyLand");

    private final ComboBox<Integer> boardSizeBox = new ComboBox<>();

    private final TextField diceField = new TextField();

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

    private final VBox boardBox = new VBox();

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
            decorateTileBox(tileBox, t.getAction(),t.getTileId());
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
        else if(tileAction instanceof WinAction){
            tileBox.setStyle("-fx-background-color: #fff511;-fx-border-color: #0b0b0b;");
        }
        else{
            tileBox.setStyle("-fx-background-color: #c0c0c1;-fx-border-color: #151515;");
        }
    }

    public Button getStartbutton() {
        return startbutton;
    }

}
