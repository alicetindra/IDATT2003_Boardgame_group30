package edu.ntnu.idatt2003.boardgame.Model;

import edu.ntnu.idatt2003.boardgame.Model.actions.TileAction;
import javafx.scene.layout.VBox;

public class Tile {
    private int id;
    private TileAction action;
    private int next;
    private transient VBox tileBox;
    private int fee = 0;
    private Player houseOwner = null;

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

    public void setFee(int value){
        this.fee = value;
    }

    public int getFee() {
        return fee;
    }

    public void setOwner(Player owner){
        this.houseOwner = owner;
    }
    public Player getOwner(){
        return houseOwner;
    }


    @Override
    public String toString() {
        return "Tile{" +
                "id=" + id +
                ", action=" + action +
                '}';
    }


}