package edu.ntnu.idatt2003.boardgame.components;

import edu.ntnu.idatt2003.boardgame.componentHolders.Board;
import edu.ntnu.idatt2003.boardgame.componentHolders.BoardGame;

public class Player {
    private String name;
    private Tile currentTile;
    private String color;
    private BoardGame boardGame;

    public Player(String name, String color) {
        if(name == null || name.isEmpty() || color == null || color.isEmpty()) {
            throw new IllegalArgumentException("Name and color cannot be null or empty");
        }
         this.name = name;
         this.color = color;
    }
    public String getColor(){
        return color;
    }

    public String getName(){
        return name;
    }

    public void setCurrentTile(Board board, int id){
        id = id-1;
        if(id>board.getTiles().size()){
            throw new IllegalArgumentException("Tile id out of bounds");
        }
        this.currentTile = board.getTiles().get(id);
    }

    public Tile getCurrentTile(){
        return currentTile;
    }
    public void setBoardGame(BoardGame boardGame){
        this.boardGame = boardGame;
    }
    public BoardGame getBoardGame(){
        return boardGame;
    }

}
