package edu.ntnu.idatt2003.boardgame;

import java.util.HashMap;

public class Board {
    HashMap<Integer, Tile> map = new HashMap<>();

    public void addTile(Tile tile) {
        if(tile == null) {
            throw new IllegalArgumentException("Tile cannot be null");
        }
        map.put(tile.getTileId(),tile);
    }
    public Tile getTile(int tileId){
        if(tileId < 0 || tileId > map.size()) {
            throw new IllegalArgumentException("Tile id out of bounds");
        }
        return map.get(tileId);
    }

    public HashMap<Integer, Tile> getMap() {
        return map;
    }
}
