package edu.ntnu.idatt2003.boardgame.Model.actions;

import edu.ntnu.idatt2003.boardgame.Model.Player;
import java.util.Random;
import java.util.logging.Logger;

/**
 * Represents a portal action that teleports a player to a random tile on the board.
 * <p>
 * When performed, this action selects a random destination tile different from the
 * player's current tile and moves the player there. If the new tile has an associated
 * action, that action is also performed.
 */
public class PortalAction implements TileAction {
    private static final Logger log = Logger.getLogger(PortalAction.class.getName());
    private int destinationTileId;

    /**
     * Constructs a new PortalAction.
     * The destination tile will be chosen randomly when the action is performed.
     */
    public PortalAction() {
    }

    /**
     * Performs the portal action by teleporting the player to a random tile on the board,
     * different from their current tile. If the new tile has an action, that action is executed.
     *
     * @param player the player to teleport
     */
    @Override
    public void perform(Player player) {
        Random random = new Random();

        while (destinationTileId == player.getCurrentTile().getId() || destinationTileId ==0){
            destinationTileId = random.nextInt(player.getBoardGame().getBoard().getTiles().size()-1);
        }
        player.placeOnTile(player.getBoardGame().getBoard(), destinationTileId);
        log.info("Portal action performed. Teleported to tile " + destinationTileId);

        if(player.getCurrentTile().getAction() != null){
            player.getCurrentTile().getAction().perform(player);
        }
    }
}