package edu.ntnu.idatt2003.boardgame.actions;

import edu.ntnu.idatt2003.boardgame.components.Player;

public class LadderAction implements TileAction {
    int destinationTile;
    String description;

    public LadderAction(int destinationTile, String description) {
        this.destinationTile = destinationTile;
        this.description = description;
    }

    @Override
    public void perform(Player player) {
        if(destinationTile < 0 || destinationTile > player.getBoardGame().getBoard().getTiles().size() - 1) {
            throw new IllegalArgumentException("Destination-tile is not on the board");
        }
        player.setCurrentTile(player.getBoardGame().getBoard(),destinationTile);
        System.out.println(description);
    }
}