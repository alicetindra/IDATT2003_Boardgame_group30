package edu.ntnu.idatt2003.boardgame.Model.actions;

import edu.ntnu.idatt2003.boardgame.Model.Player;
import java.util.logging.Logger;

public class JailAction implements TileAction {
    private static final Logger log = Logger.getLogger(JailAction.class.getName());
    private String description;
    private int destination;

    public JailAction(int destination,String description) {
        this.description = description;
        this.destination = destination;
    }

    @Override
    public void perform(Player player) {
        log.info("Player is in jail");
        player.placeOnTile(player.getBoardGame().getBoard(), destination);
    }
}