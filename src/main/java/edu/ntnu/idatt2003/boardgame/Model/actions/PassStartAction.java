package edu.ntnu.idatt2003.boardgame.Model.actions;

import edu.ntnu.idatt2003.boardgame.Model.Player;
import java.util.logging.Logger;

public class PassStartAction implements TileAction {
    private static final Logger log = Logger.getLogger(PassStartAction.class.getName());
    private String description;

    public PassStartAction(String description) {
        this.description = description;
    }

    @Override
    public void perform(Player player) {
        player.editMoney(100);
        log.info(description);
    }
}