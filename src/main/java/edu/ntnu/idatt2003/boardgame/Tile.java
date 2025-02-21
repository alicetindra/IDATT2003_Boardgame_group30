package edu.ntnu.idatt2003.boardgame;

public class Tile {
    private Tile nextTile;
    private int tileId;
    private TileAction landAction;

    public Tile(int tileId) {
        this.tileId = tileId;
    }

    public void landPlayer(Player player) {

    }
    public void leavePlayer(Player player) {

    }
    public void setNextTile(Tile tile){
        nextTile = tile;
    }
    public int getTileId(){
        return tileId;
    }
    public Tile getNextTile(){
        return nextTile;
    }

}
