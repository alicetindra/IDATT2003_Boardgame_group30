package edu.ntnu.idatt2003.boardgame.Model.actions;

import edu.ntnu.idatt2003.boardgame.Model.Player;
import java.util.logging.Logger;
/**
 * Represents an action that sends a player to jail.
 * <p>
 * When performed, this action moves the player to a specific tile on the board
 * and flags them as being in jail.
 * </p>
 */
public class JailAction implements TileAction {
    private static final Logger log = Logger.getLogger(JailAction.class.getName());
    private String description;
    private final int destination;

    /**
     * Constructs a {@code JailAction} that sends a player to the given destination tile.
     *
     * @param destination the index of the tile where the player is placed (typically the jail tile)
     * @param description a description of the jail action
     */
    public JailAction(int destination,String description) {
        this.description = description;
        this.destination = destination;
    }

    /**
     * Performs the jail action on the specified player.
     * <p>
     * The player is moved to the destination tile and marked as being in jail.
     * </p>
     *
     * @param player the player who will be sent to jail
     */
    @Override
    public void perform(Player player) {
        log.info("Player is in jail");
        player.placeOnTile(player.getBoardGame().getBoard(), destination);
        player.setInJail(true);
    }
}