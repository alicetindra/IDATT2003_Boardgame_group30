package edu.ntnu.idatt2003.boardgame.actions;

import edu.ntnu.idatt2003.boardgame.components.Player;

import java.util.Random;

public class PortalAction implements TileAction {
    int destinationTile;
    String description;

    public PortalAction(int destinationTile, String description) {
        this.destinationTile = destinationTile;
        this.description = description;
    }

    @Override
    public void perform(Player player) {
        int random = 0;
        while(random == player.getCurrentTile().getTileId() || random == 0){
            random = new Random().nextInt(player.getBoardGame().getBoard().getTiles().size());
        }
        player.setCurrentTile(player.getBoardGame().getBoard(),random);
        System.out.println("Portal action performed.");
        if(player.getCurrentTile().getAction() != null){
            player.getCurrentTile().getAction().perform(player);
        }
    }
}