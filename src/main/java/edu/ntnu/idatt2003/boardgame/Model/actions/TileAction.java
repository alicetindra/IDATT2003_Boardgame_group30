package edu.ntnu.idatt2003.boardgame.Model.actions;

import edu.ntnu.idatt2003.boardgame.Model.Player;
/**
 * Represents an action that can be performed on a board tile by a player.
 */
public interface TileAction {
    /**
     * Performs the action for the specified player.
     *
     * @param player the player on whom the action is performed
     */
    void perform(Player player);
}
