package edu.ntnu.idatt2003.boardgame.Model.actions;

import edu.ntnu.idatt2003.boardgame.Model.Player;

public class WinAction implements TileAction {
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
        System.out.println("Win action performed.");
    }
}