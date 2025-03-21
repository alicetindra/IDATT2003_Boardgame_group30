package edu.ntnu.idatt2003.boardgame;

public class Tile {
    private int id;
    private Integer action;
    private int next;

    public Tile(){

    }
    public int getTileId(){
        return id;
    }

    public Integer getAction() {
        return action;
    }

    public void setAction(Integer action) {
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