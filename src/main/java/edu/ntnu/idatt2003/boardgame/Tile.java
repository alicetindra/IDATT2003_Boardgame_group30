package edu.ntnu.idatt2003.boardgame;

public class Tile {
    private int id;
    private Integer action;

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

    @Override
    public String toString() {
        return "Tile{" +
                "id=" + id +
                ", action=" + action +
                '}';
    }


}