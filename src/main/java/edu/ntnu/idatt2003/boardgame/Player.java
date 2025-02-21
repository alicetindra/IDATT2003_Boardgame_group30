package edu.ntnu.idatt2003.boardgame;

public class Player {

    private final BoardGame game;
    private final String name;
    private Tile currentTile;

    public Player(String name, BoardGame game) {
        if(name == null || name.isEmpty()){
            throw new IllegalArgumentException("Player name cannot be null or empty");
        }
        if(game == null){
            throw new IllegalArgumentException("BoardGame cannot be null");
        }
        this.name = name;
        this.game = game;
    }
    public void placeOnTile(Tile tile){

    }
    public void move(int steps){
        
    }
}
