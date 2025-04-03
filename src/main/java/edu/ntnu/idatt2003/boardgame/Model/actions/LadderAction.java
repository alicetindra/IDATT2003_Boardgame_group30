package edu.ntnu.idatt2003.boardgame.Model.actions;

import edu.ntnu.idatt2003.boardgame.Model.Player;
import edu.ntnu.idatt2003.boardgame.Model.Tile;

public class LadderAction implements TileAction {
    private int destinationTileId;
    private String description;

    public LadderAction(int destinationTile, String description) {
        this.destinationTileId = destinationTile;
        this.description = description;
    }

    public int getDestinationTileId() {
        return destinationTileId;
    }

    @Override
    public void perform(Player player) {
        player.placeOnTile(player.getBoardGame().getBoard(), destinationTileId);
        System.out.println("Ladder action performed. Moved to Tile " + destinationTileId );
    }
}