package edu.ntnu.idatt2003.boardgame.Model.actions;

import edu.ntnu.idatt2003.boardgame.Model.Player;
import java.util.logging.Logger;

public class DrawCardAction implements TileAction {
    private static final Logger log = Logger.getLogger(JailAction.class.getName());
    private String description;

    public DrawCardAction(int nrOfCards, String description) {
        this.description = description;
    }

    @Override
    public void perform(Player player) {
        log.info(description);
        player.placeOnTile(player.getBoardGame().getBoard(),player.getBoardGame().getDice().getTotalSumOfEyes()+player.getCurrentTile().getId());
        player.getBoardGame().getCardManager().drawCard();
        player.getBoardGame().notifyObservers("drewCard");
        player.getBoardGame().getCardManager().applyCard(player.getBoardGame().getCardManager().getLastDrawnCard(), player);

    }
}