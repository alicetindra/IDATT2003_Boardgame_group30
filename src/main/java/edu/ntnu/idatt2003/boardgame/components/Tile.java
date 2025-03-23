package edu.ntnu.idatt2003.boardgame.components;

import edu.ntnu.idatt2003.boardgame.actions.TileAction;
import javafx.scene.layout.VBox;

public class Tile {
    private int id;
    private int next;
    private TileAction action;
    private transient VBox tileBox;

    public Tile(){

    }
    public int getTileId(){
        return id;
    }


    public VBox getTileBox(){
        return tileBox;
    }

    public void setTileBox(VBox tileBox){
        this.tileBox = tileBox;
    }

    public TileAction getAction() {
        return action;
    }
    public void setAction(TileAction action) {
        this.action = action;
    }


    public void setNextTile(Integer next) {
       this.next = next;
    }
    public Integer getNextTile() {
        return next;
    }



    @Override
    public String toString() {
        return "Tile{" +
                "id=" + id +
                ", action=" + action +
                '}';
    }


}