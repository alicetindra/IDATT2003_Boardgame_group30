package edu.ntnu.idatt2003.boardgame.componentHolders;

import edu.ntnu.idatt2003.boardgame.components.Tile;

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
