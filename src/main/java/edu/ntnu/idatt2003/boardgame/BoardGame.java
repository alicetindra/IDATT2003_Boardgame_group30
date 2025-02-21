package edu.ntnu.idatt2003.boardgame;

import java.util.List;

public class BoardGame {
    Board board;
    Player currentPlayer;
    List<Player> players;
    Dice dice;

   public void addPlayer(Player player) {
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
       return null;
   }

   public Board getBoard(){
       return board;
   }
}
