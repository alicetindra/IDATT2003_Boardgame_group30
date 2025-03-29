package edu.ntnu.idatt2003.boardgame.Model;

import edu.ntnu.idatt2003.boardgame.Model.actions.LadderAction;
import edu.ntnu.idatt2003.boardgame.Model.actions.SnakeAction;
import java.util.ArrayList;
import java.util.List;

public class Board {
    private List<Tile> tiles = new ArrayList<>();

    public List<Tile> getTiles(){
        return tiles;
    }

    public void setTiles(List<Tile> tiles) {
        this.tiles = tiles;
    }
}
