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
        if(name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null nor empty");
        }
        else if(color == null || color.trim().isEmpty()) {
            throw new IllegalArgumentException("Color cannot be null nor empty");
        }
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


    public void move(int steps){
        if(steps<0){
            throw new IllegalArgumentException("Steps cannot be negative");
        }
        int destTile = this.currentTile.getId() + steps;
        Board board = this.boardGame.getBoard();

        if(destTile <= board.getTiles().size() && board.getTiles().get(destTile - 1).getAction() != null){
            board.getTiles().get(destTile -1).getAction().perform(this);
        } else if(destTile > board.getTiles().size()){
            destTile = (2*board.getTiles().size() - destTile);
            this.placeOnTile(board, destTile);
            if(board.getTiles().get(destTile - 1).getAction() != null){
                board.getTiles().get(destTile - 1).getAction().perform(this);
            }
        }else{
            this.placeOnTile(board, destTile);
        }

    }

    public void setImageView(ImageView imageView){
        if(imageView.getImage() == null){
            throw new IllegalArgumentException("Image cannot be null");
        }
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