package edu.ntnu.idatt2003.boardgame.Model.actions;

import edu.ntnu.idatt2003.boardgame.Model.Player;
import java.util.logging.Logger;

/**
 * Represents the action triggered when a player lands on a snake tile.
 * <p>
 * This action moves the player down to a specified destination tile,
 * simulating the effect of sliding down a snake in classic board games.
 */
public class SnakeAction implements TileAction {
    private static final Logger log = Logger.getLogger(SnakeAction.class.getName());
    private final int destinationTileId;
    private final String description;

    /**
     * Constructs a new {@code SnakeAction} with the given destination tile and description.
     *
     * @param destinationTile the tile ID where the player will be moved
     * @param description a description of this action, for logging
     */
    public SnakeAction(int destinationTile, String description) {
        this.destinationTileId = destinationTile;
        this.description = description;
    }

    /**
     * Returns the destination tile ID where the player will be moved.
     *
     * @return the destination tile ID
     */
    public int getDestinationTileId() {
        return destinationTileId;
    }

    /**
     * Performs the snake action by moving the player to the destination tile,
     * and logs the action.
     *
     * @param player the player on whom the action is performed
     */
    @Override
    public void perform(Player player) {
        player.placeOnTile(player.getBoardGame().getBoard(),destinationTileId);
        log.info("Snake action performed. Moved to Tile " + destinationTileId);
    }
}