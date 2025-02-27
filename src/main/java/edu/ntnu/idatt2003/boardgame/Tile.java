package edu.ntnu.idatt2003.boardgame;

public class Tile {
    private Tile nextTile;
    private int tileId;
    private TileAction landAction;

    public Tile(int tileId) {
        this.tileId = tileId;
    }

    public void landPlayer(Player player) {
        if(player.getGame().getBoard().getActionMap().containsKey(tileId)){
            if(player.getGame().getBoard().getActionMap().get(tileId) == 0){
                landAction = new PortalAction(" took a portal!");
                landAction.perform(player);
            }
            else if(player.getGame().getBoard().getActionMap().get(tileId) > player.getCurrentTile().getTileId()){
                int newId = player.getGame().getBoard().getActionMap().get(tileId);
                landAction = new LadderAction(newId," climbed a ladder!");
                landAction.perform(player);
            }
            else if(player.getGame().getBoard().getActionMap().get(tileId) < player.getCurrentTile().getTileId()){
                int newId = player.getGame().getBoard().getActionMap().get(tileId);
                landAction = new LadderAction(newId," slid down a snake!");
                landAction.perform(player);
            }
        }
    }
    public void setNextTile(Tile tile){
        nextTile = tile;
    }
    public int getTileId(){
        return tileId;
    }
    public Tile getNextTile(){
        return nextTile;
    }

}