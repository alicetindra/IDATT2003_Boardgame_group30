package edu.ntnu.idatt2003.boardgame;

import java.util.List;

public class Board {
    private List<Tile> tiles;
    public List<Tile> getTiles(){
        return tiles;
    }
    public void setTiles(List<Tile> tiles) {
        this.tiles = tiles;
    }
}
