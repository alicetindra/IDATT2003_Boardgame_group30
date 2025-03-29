package edu.ntnu.idatt2003.boardgame.Model;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Player {
    private String name;
    private String color;
    private Tile currentTile;
    private BoardGame boardGame;
    private ImageView imageView;

    public Player(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public String getName(){
        return name;
    }

    public String getColor(){
        return color;
    }

    public Tile getCurrentTile(){
        return currentTile;
    }

    public void placeOnTile(Board board, int id){
        this.currentTile = board.getTiles().get(id-1);
    }

    public void setImageView(ImageView imageView){
        this.imageView = imageView;
    }
    public ImageView getImageView(){
        return imageView;
    }

    public void setBoardGame(BoardGame boardGame){
        this.boardGame = boardGame;
    }

    public BoardGame getBoardGame(){
        return boardGame;
    }

}