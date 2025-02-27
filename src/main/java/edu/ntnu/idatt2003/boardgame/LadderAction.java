package edu.ntnu.idatt2003.boardgame;

public class LadderAction implements TileAction {
    int destinationTile;
    String description;

    public LadderAction(int destinationTile, String description) {
        this.destinationTile = destinationTile;
        this.description = description;
    }

    @Override
    public void perform(Player player) {
        player.placeOnTile(player.getGame().getBoard().getTile(destinationTile));
        System.out.println(player.getName()+description);
    }
}