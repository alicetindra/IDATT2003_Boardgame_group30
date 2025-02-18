package edu.ntnu.idatt2003.boardgame;

public class Tile {
    Tile nextTile;
    int tileId;
    TileAction landAction;

    public Tile(int tileId) {
        this.tileId = tileId;
    }
    public void landPlayer(Player player) {

    }
    public void leavePlayer(Player player) {

    }
    public void setNextTile(Tile tile){

    }
}
