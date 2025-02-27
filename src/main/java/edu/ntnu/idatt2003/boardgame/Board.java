package edu.ntnu.idatt2003.boardgame;

import java.util.HashMap;

public class Board {
    HashMap<Integer, Tile> map = new HashMap<>();
    HashMap<Integer, Integer> actionMap = new HashMap<>();

    public void addTile(Tile tile) {
        if(tile == null) {
            throw new IllegalArgumentException("Tile cannot be null");
        }
        map.put(tile.getTileId(),tile);
        if(tile.getTileId()>1) {
            map.get(tile.getTileId() - 1).setNextTile(tile);
        }
    }
    public Tile getTile(int tileId){
        if(tileId < 0 || tileId > map.size()) {
            throw new IllegalArgumentException("Tile id out of bounds");
        }
        return map.get(tileId);
    }

    public void fillActionMap(int tile, int dest){
        actionMap.put(tile,dest);
    }

    public HashMap<Integer, Integer> getActionMap() {
        return actionMap;
    }

    public HashMap<Integer, Tile> getMap() {
        return map;
    }


}
