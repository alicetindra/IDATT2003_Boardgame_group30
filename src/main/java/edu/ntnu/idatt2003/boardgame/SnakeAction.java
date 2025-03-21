package edu.ntnu.idatt2003.boardgame;

public class SnakeAction implements TileAction {
    int destinationTile;
    String description;

    public SnakeAction(int destinationTile, String description) {
        this.destinationTile = destinationTile;
        this.description = description;
    }

    @Override
    public void perform(Player player) {
        player.setCurrentTile(player.getBoardGame().getBoard(),destinationTile);
        System.out.println("Snake action performed.");
    }
}