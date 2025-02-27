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
        if(steps <= 0){
            throw new IllegalArgumentException("Steps must be greater than 1");
        }
        if(currentTile.getTileId()+steps > game.getBoard().getMap().size()){
            int newTileId = (2*game.getBoard().getMap().size())-(currentTile.getTileId()+steps);
            placeOnTile(game.getBoard().getTile(newTileId));
        }
        else if(currentTile.getTileId()+steps == game.getBoard().getMap().size()){
            game.getWinner();
        }
        else{
            placeOnTile(game.getBoard().getTile(currentTile.getTileId()+steps));
        }
        currentTile.landPlayer(this);
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
    public BoardGame getGame(){
        return game;
    }
    public String getName(){
        return name;
    }

}