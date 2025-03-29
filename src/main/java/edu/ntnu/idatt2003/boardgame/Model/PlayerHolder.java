package edu.ntnu.idatt2003.boardgame.Model;


import java.util.List;

public class PlayerHolder {
    private List<Player> players;
    Player currentPlayer;

    public List<Player> getPlayers(){
        return players;
    }
    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public void setCurrentPlayer(Player player){
        this.currentPlayer = player;
    }
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

}