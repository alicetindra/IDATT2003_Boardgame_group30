package edu.ntnu.idatt2003.boardgame.Model.actions;

import edu.ntnu.idatt2003.boardgame.Model.Player;
import java.util.logging.Logger;

/**
 * Represents an action where the player climbs a ladder to a higher-numbered tile.
 * <p>
 * This action is typically used in games like *Snakes and Ladders*, where landing on a ladder tile
 * moves the player forward to a specified destination tile.
 */
public class LadderAction implements TileAction {
    private static final Logger log = Logger.getLogger(LadderAction.class.getName());
    private final int destinationTileId;
    private final String description;

    /**
     * Constructs a new {@code LadderAction}.
     *
     * @param destinationTile the tile ID the player should move to
     * @param description a description of the action
     */
    public LadderAction(int destinationTile, String description) {
        this.destinationTileId = destinationTile;
        this.description = description;
    }

    /**
     * Returns the destination tile ID.
     *
     * @return the tile ID the player will move to
     */
    public int getDestinationTileId() {
        return destinationTileId;
    }

    /**
     * Executes the ladder action by moving the player to the specified destination tile.
     *
     * @param player the player performing the action
     */
    @Override
    public void perform(Player player) {
        player.placeOnTile(player.getBoardGame().getBoard(), destinationTileId);
        log.info("Ladder action performed. Moved to Tile " + destinationTileId );
    }
}