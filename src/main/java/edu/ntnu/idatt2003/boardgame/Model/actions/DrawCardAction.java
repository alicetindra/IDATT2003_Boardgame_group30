package edu.ntnu.idatt2003.boardgame.Model.actions;

import edu.ntnu.idatt2003.boardgame.Model.Player;
import java.util.logging.Logger;

/**
 * Represents an action where a player draws a card when landing on a tile.
 * Implements the {@link TileAction} interface.
 */
public class DrawCardAction implements TileAction {
    private static final Logger log = Logger.getLogger(DrawCardAction.class.getName());
    private final String description;

    /**
     * Constructs a DrawCardAction with a textual description.
     * The {@code nrOfCards} parameter is currently unused.
     *
     * @param nrOfCards   the number of cards to draw
     * @param description a description of the action for logging purposes
     */
    public DrawCardAction(int nrOfCards, String description) {
        this.description = description;
    }

    /**
     * Performs the draw card action on the given player.
     * <p>
     * Steps include:
     * <ul>
     *   <li>Logging the action description</li>
     *   <li>Repositioning the player based on dice roll and current tile</li>
     *   <li>Drawing and applying a card from the game's card manager</li>
     *   <li>Notifying observers about the card draw</li>
     * </ul>
     *
     * @param player the player performing the action
     */
    @Override
    public void perform(Player player) {
        log.info(description);
        player.placeOnTile(player.getBoardGame().getBoard(),player.getBoardGame().getDice().getTotalSumOfEyes()+player.getCurrentTile().getId());
        player.getBoardGame().getCardManager().drawCard();
        player.getBoardGame().notifyObservers("drewCard");
        player.getBoardGame().getCardManager().applyCard(player.getBoardGame().getCardManager().getLastDrawnCard(), player);

    }
}