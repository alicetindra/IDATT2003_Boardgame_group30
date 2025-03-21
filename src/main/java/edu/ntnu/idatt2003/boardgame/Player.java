package edu.ntnu.idatt2003.boardgame;

public class Player {
    private String name;
    private Tile currentTile;
    private String color;

    public Player(String name, String color) {
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
        this.currentTile = board.getTiles().get(id);
    }

    public Tile getCurrentTile(){
        return currentTile;
    }

}
