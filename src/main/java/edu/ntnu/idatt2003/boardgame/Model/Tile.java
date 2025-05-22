package edu.ntnu.idatt2003.boardgame.Model;

import edu.ntnu.idatt2003.boardgame.Model.actions.TileAction;
import javafx.scene.layout.VBox;

/**
 * Represents a single tile on the board in the board game.
 * Each tile has a unique ID, an optional action that is executed
 * when a player lands on it, and an optional visual representation
 * using a JavaFX {@link VBox}.
 */
public class Tile {
    /** The unique identifier of the tile. */
    private final int id;
    /** The action to perform when a player lands on the tile. */
    private TileAction action;
    /**
     * The visual representation of the tile in the UI.
     * Marked as {@code transient} to avoid serialization issues.
     */
    private transient VBox tileBox;

    /**
     * Constructs a new {@code Tile} with the given ID.
     *
     * @param tileId the unique identifier of the tile
     */
    public Tile(int tileId){
        this.id = tileId;
    }

    /**
     * Returns the ID of the tile.
     *
     * @return the tile ID
     */
    public int getId(){
        return id;
    }

    /**
     * Returns the visual {@link VBox} representation of the tile.
     *
     * @return the tile's {@code VBox}, or {@code null} if not set
     */
    public VBox getTileBox(){
        return tileBox;
    }

    /**
     * Sets the visual {@link VBox} representation of the tile.
     *
     * @param tileBox the {@code VBox} to associate with the tile
     */
    public void setTileBox(VBox tileBox){
        this.tileBox = tileBox;
    }

    /**
     * Returns the action assigned to this tile.
     *
     * @return the tile's {@link TileAction}, or {@code null} if none
     */
    public TileAction getAction() {
        return action;
    }

    /**
     * Sets the action to be performed when a player lands on this tile.
     *
     * @param action the {@link TileAction} to assign to this tile
     */
    public void setAction(TileAction action) {
        this.action = action;
    }

    /**
     * Returns a string representation of the tile.
     *
     * @return a string containing the tile's ID and action
     */
    @Override
    public String toString() {
        return "Tile{" +
                "id=" + id +
                ", action=" + action +
                '}';
    }


}