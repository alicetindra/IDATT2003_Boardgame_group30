package edu.ntnu.idatt2003.boardgame;

import java.util.List;

public class BoardGame {
    private Board board;
    private Player currentPlayer;
    private List<Player> players;
    private Dice dice;

   public void addPlayer(Player player) {
     if(players == null) {
       throw new IllegalArgumentException("Player cannot be null");
     }
       players.add(player);
   }
   public void createBoard(){
       board = new Board();
   }
   public void createDice(int numberOfDice){
       dice = new Dice(numberOfDice);
   }
   public void Play(){

   }
   public Player getWinner(){
       if(currentPlayer.getCurrentTile().getNextTile() == null){
           return currentPlayer;
       }
       return null;
   }
   public Board getBoard(){
       return board;
   }

}
