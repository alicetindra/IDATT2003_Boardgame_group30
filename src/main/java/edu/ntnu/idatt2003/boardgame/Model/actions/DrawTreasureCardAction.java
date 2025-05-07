package edu.ntnu.idatt2003.boardgame.Model.actions;

import edu.ntnu.idatt2003.boardgame.Model.Player;
import java.util.logging.Logger;

public class DrawTreasureCardAction implements TileAction {
    private static final Logger log = Logger.getLogger(JailAction.class.getName());
    private String description;

    public DrawTreasureCardAction(String description) {
        this.description = description;
    }

    @Override
    public void perform(Player player) {
        log.info(description);
        player.drawRandomTreasureCard();
    }
}