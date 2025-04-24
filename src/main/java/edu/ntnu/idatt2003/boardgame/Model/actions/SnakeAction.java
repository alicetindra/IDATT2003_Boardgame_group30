package edu.ntnu.idatt2003.boardgame.Model.actions;

import edu.ntnu.idatt2003.boardgame.Model.Player;
import edu.ntnu.idatt2003.boardgame.Model.Tile;
import java.util.logging.Logger;

public class SnakeAction implements TileAction {
    private static final Logger log = Logger.getLogger(SnakeAction.class.getName());
    private int destinationTileId;
    private String description;

    public SnakeAction(int destinationTile, String description) {
        this.destinationTileId = destinationTile;
        this.description = description;
    }

    public int getDestinationTileId() {
        return destinationTileId;
    }

    @Override
    public void perform(Player player) {
        player.placeOnTile(player.getBoardGame().getBoard(),destinationTileId);
        log.info("Snake action performed. Moved to Tile " + destinationTileId);
    }
}