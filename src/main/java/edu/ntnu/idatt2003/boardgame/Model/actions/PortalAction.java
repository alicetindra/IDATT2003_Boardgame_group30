package edu.ntnu.idatt2003.boardgame.Model.actions;

import edu.ntnu.idatt2003.boardgame.Model.Player;
import java.util.Random;

public class PortalAction implements TileAction {
    private int destinationTileId;

    public PortalAction() {
    }

    @Override
    public void perform(Player player) {
        Random random = new Random();

        while (destinationTileId == player.getCurrentTile().getId() || destinationTileId ==0){
            destinationTileId = random.nextInt(player.getBoardGame().getBoard().getTiles().size()-1);
        }

        player.placeOnTile(player.getBoardGame().getBoard(), destinationTileId);
        System.out.println("Portal action performed. Teleported to tile " + destinationTileId);

        if(player.getCurrentTile().getAction() != null){
            player.getCurrentTile().getAction().perform(player);
        }
    }
}