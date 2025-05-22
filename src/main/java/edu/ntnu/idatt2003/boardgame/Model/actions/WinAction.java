package edu.ntnu.idatt2003.boardgame.Model.actions;

import edu.ntnu.idatt2003.boardgame.Model.Player;
import java.util.logging.Logger;

/**
 * Represents the action of winning the game when a player lands on a specific tile.
 * <p>
 * When performed, this action moves the player to the designated destination tile and
 * declares the player as the winner of the game.
 */
public class WinAction implements TileAction {
    private static final Logger log = Logger.getLogger(WinAction.class.getName());
    private final int destinationTileId;
    private final String description;

    /**
     * Constructs a WinAction with the specified destination tile ID and description.
     *
     * @param destinationTileId the ID of the tile where the player will be placed upon winning
     * @param description a brief description of the win action
     */
    public WinAction(int destinationTileId, String description) {
        this.destinationTileId = destinationTileId;
        this.description = description;
    }

    /**
     * Performs the win action on the given player.
     * Moves the player to the destination tile and declares them the winner.
     *
     * @param player the player performing the action
     */
    @Override
    public void perform(Player player) {
        player.placeOnTile(player.getBoardGame().getBoard(), destinationTileId);
        player.getBoardGame().declareWinner(player);
        log.info("Win action performed.");
    }
}