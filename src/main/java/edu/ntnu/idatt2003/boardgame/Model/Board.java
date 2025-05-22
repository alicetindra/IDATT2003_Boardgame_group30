package edu.ntnu.idatt2003.boardgame.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the game board consisting of a list of tiles.
 * <p>
 * This class provides access to the collection of {@link Tile} objects
 * that make up the board. Tiles define the layout and structure
 * of the game board, including movement and actions for players.
 * </p>
 */
public class Board {
    private final List<Tile> tiles = new ArrayList<>();

    /**
     * Returns the list of tiles that make up the board.
     *
     * @return a list of {@link Tile} objects representing the board layout
     */
    public List<Tile> getTiles(){
        return tiles;
    }

}
