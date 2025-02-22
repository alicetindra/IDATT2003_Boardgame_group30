package edu.ntnu.idatt2003.boardgame;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Tile {
    private Tile nextTile;
    private int tileId;
    private TileAction landAction;
    private HashMap<Integer,Integer> tileAndDestMap = new HashMap<>();

    public Tile(int tileId) {
        this.tileId = tileId;
    }
    public void fillMap(HashMap<Integer,Integer> tileAndDestMap){
        tileAndDestMap.put(10,30);
        tileAndDestMap.put(18,40);
        tileAndDestMap.put(38,10);
        tileAndDestMap.put(5,17);
        tileAndDestMap.put(40,26);
        tileAndDestMap.put(60,46);
        tileAndDestMap.put(23,70);
    }


    public void landPlayer(Player player) {
        fillMap(tileAndDestMap);
        if(tileAndDestMap.containsKey(tileId)){
            int newId = tileAndDestMap.get(tileId);
            landAction = new LadderAction(newId,"You moved to tile "+newId);
            landAction.perform(player);
        }

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
