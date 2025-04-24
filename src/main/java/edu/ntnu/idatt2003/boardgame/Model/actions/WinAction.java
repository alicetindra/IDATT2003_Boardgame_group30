package edu.ntnu.idatt2003.boardgame.Model.actions;

import edu.ntnu.idatt2003.boardgame.Model.Player;
import java.util.logging.Logger;

public class WinAction implements TileAction {
    private static final Logger log = Logger.getLogger(WinAction.class.getName());
    private int destinationTileId;
    private String description;

    public WinAction(int destinationTileId, String description) {
        this.destinationTileId = destinationTileId;
        this.description = description;
    }

    @Override
    public void perform(Player player) {
        player.placeOnTile(player.getBoardGame().getBoard(), destinationTileId);
        player.getBoardGame().declareWinner(player);
        log.info("Win action performed.");
    }
}