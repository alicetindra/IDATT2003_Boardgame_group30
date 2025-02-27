package edu.ntnu.idatt2003.boardgame;

import java.util.Random;

public class PortalAction implements TileAction {
  String description;

  public PortalAction(String description) {
    this.description = description;
  }

  @Override
  public void perform(Player player) {
    int random = 0;
    while(random == player.getCurrentTile().getTileId() || random == 0){
      random = new Random().nextInt(player.getGame().getBoard().getMap().size());
    }
    player.placeOnTile(player.getGame().getBoard().getTile(random));
    System.out.println(player.getName()+description);
  }
}