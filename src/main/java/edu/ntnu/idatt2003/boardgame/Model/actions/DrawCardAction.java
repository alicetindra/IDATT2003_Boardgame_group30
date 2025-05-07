package edu.ntnu.idatt2003.boardgame.Model.actions;

import edu.ntnu.idatt2003.boardgame.Model.Player;
import java.util.logging.Logger;

public class DrawCardAction implements TileAction {
    private static final Logger log = Logger.getLogger(JailAction.class.getName());
    private String description;

    public DrawCardAction(String description) {
        this.description = description;
    }

    @Override
    public void perform(Player player) {
        log.info(description);
        //Player gets a random card. eks: Pay 100 coin fine to the state. eks: You won 200 coins. etc
        //Cards can be in a file, like json or .txt ??
    }
}