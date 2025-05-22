package edu.ntnu.idatt2003.boardgame.Model.actions;

import edu.ntnu.idatt2003.boardgame.Model.Player;
import java.util.logging.Logger;

/**
 * Represents an action that occurs when a player passes the starting tile.
 * <p>
 * Typically used in board games like Monopoly, this action rewards the player
 * with a predefined amount of money (e.g., 100 currency units) when they pass the start tile.
 */
public class PassStartAction implements TileAction {
    private static final Logger log = Logger.getLogger(PassStartAction.class.getName());
    private final String description;

    /**
     * Constructs a new {@code PassStartAction} with a given description.
     *
     * @param description a description of the action
     */
    public PassStartAction(String description) {
        this.description = description;
    }

    /**
     * Performs the action by giving the player 100 currency units and logging the event.
     *
     * @param player the player who passed the start tile
     */
    @Override
    public void perform(Player player) {
        player.editMoney(100);
        log.info(description);
    }
}