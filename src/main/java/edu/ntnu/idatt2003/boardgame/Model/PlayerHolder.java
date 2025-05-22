package edu.ntnu.idatt2003.boardgame.Model;

import java.util.List;

/**
 * Manages the list of players and tracks the current player in the board game.
 * Provides utility methods for accessing player data and managing player turns.
 */
public class PlayerHolder {
    /** The list of players participating in the game. */
    private List<Player> players;

    /** The player whose turn is currently active. */
    private Player currentPlayer;

    /**
     * Returns the list of players.
     *
     * @return a list of {@link Player} objects
     */
    public List<Player> getPlayers(){
        return players;
    }

    /**
     * Sets the list of players.
     *
     * @param players a list of {@link Player} objects
     */
    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    /**
     * Sets the current player.
     *
     * @param player the {@link Player} to set as the current player
     */
    public void setCurrentPlayer(Player player){
        this.currentPlayer = player;
    }

    /**
     * Returns the current player.
     *
     * @return the {@link Player} whose turn it is
     */
    public Player getCurrentPlayer(){
        return currentPlayer;
    }


    public int getNextPlayerIndex(){
        int i = players.indexOf(currentPlayer);
        if(i<players.size()-1){
            return i+1;
        }else{
            return 0;
        }
    }

    /**
     * Returns the name of the player at the specified index.
     *
     * @param index the index of the player in the list
     * @return the name of the player
     */
    public String getPlayerNameWithIndex(int index){
        return players.get(index).getName();
    }

}