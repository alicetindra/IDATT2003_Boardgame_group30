package edu.ntnu.idatt2003.boardgame;

public class Tile {
    private int id;
    private int next;
    private TileAction action;

    public Tile(){

    }
    public int getTileId(){
        return id;
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