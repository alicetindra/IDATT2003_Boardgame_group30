package edu.ntnu.idatt2003.boardgame;

public class Player {

    private final BoardGame game;
    private final String name;
    private Tile currentTile;

    public Player(String name, BoardGame game) {
        if(name == null || name.trim().isEmpty()){
            throw new IllegalArgumentException("Player name cannot be null or empty");
        }
        if(game == null){
            throw new IllegalArgumentException("BoardGame cannot be null");
        }
        this.name = name;
        this.game = game;
    }
    public void placeOnTile(Tile tile){
        if(tile == null){
            throw new IllegalArgumentException("Tile cannot be null");
        }
        this.currentTile = tile;
    }
    public void move(int steps){
        //If the player gets past the goal-tile, it should go the opposite
        // way the correct number of tiles
        if(steps <= 0){
            throw new IllegalArgumentException("Steps must be greater than 1");
        }
        if(currentTile.getTileId()+steps > game.board.getMap().size()){
            placeOnTile(game.board.getTile(steps-(game.board.getMap().size()-currentTile.getTileId())));
        }else{
            placeOnTile(game.board.getTile(currentTile.getTileId()+steps));
        }
    }
    public void setCurrentTile(Tile tile){
        if(tile == null){
            throw new IllegalArgumentException("Tile cannot be null");
        }
        this.currentTile = tile;
    }
    public Tile getCurrentTile(){
        return currentTile;
    }
}
