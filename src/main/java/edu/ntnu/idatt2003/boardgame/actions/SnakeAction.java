package edu.ntnu.idatt2003.boardgame.actions;

import edu.ntnu.idatt2003.boardgame.components.Player;

public class SnakeAction implements TileAction {
    int destinationTile;
    String description;

    public SnakeAction(int destinationTile, String description) {
        this.destinationTile = destinationTile;
        this.description = description;
    }

    public int getDestinationTile() {
        return destinationTile;
    }

    @Override
    public void perform(Player player) {
        player.setCurrentTile(player.getBoardGame().getBoard(),destinationTile);
        System.out.println("Snake action performed.");
    }
}