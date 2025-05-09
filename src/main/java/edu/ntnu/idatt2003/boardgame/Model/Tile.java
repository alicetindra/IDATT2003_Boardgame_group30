package edu.ntnu.idatt2003.boardgame.Model;

import edu.ntnu.idatt2003.boardgame.Model.actions.TileAction;
import javafx.scene.layout.VBox;

public class Tile {
    private int id;
    private TileAction action;
    private int next;
    private transient VBox tileBox;

    public Tile(int tileId){
        this.id = tileId;
    }

    public int getId(){
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


    @Override
    public String toString() {
        return "Tile{" +
                "id=" + id +
                ", action=" + action +
                '}';
    }


}