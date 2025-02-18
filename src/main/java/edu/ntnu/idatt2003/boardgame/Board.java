package edu.ntnu.idatt2003.boardgame;

import java.util.HashMap;

public class Board {
    HashMap<Integer, Tile> map = new HashMap<>();

    public void addTile(Tile tile) {

    }
    public Tile getTile(int tileId){
        return map.get(tileId);
    }
}
