package edu.ntnu.idatt2003.boardgame;

import java.util.ArrayList;
import java.util.List;

public class BoardGame {
    private Board board;
    private Player currentPlayer;
    private List<Player> players = new ArrayList<>();
    private Dice dice;

   public void addPlayer(Player player) {
     if(player == null) {
       throw new IllegalArgumentException("Player cannot be null");
     }
       players.add(player);
   }

   public void createBoard(){
       board = new Board();

       for(int i = 0; i<=90; i++){
       board.addTile(new Tile(i));
       }
       //Adding snakes, ladders and portals
     board.fillActionMap(6, 26);
     board.fillActionMap(18, 21);
     board.fillActionMap(55, 73);

     board.fillActionMap(28, 8);
     board.fillActionMap(42, 24);
     board.fillActionMap(70, 47);
     board.fillActionMap(87, 75);

     board.fillActionMap(49, 0);
     board.fillActionMap(63, 0);
   }

   public void createDice(int numberOfDice){
     if(numberOfDice <=0) {
       throw new IllegalArgumentException("Number of dice must be greater than 0");
     }
       dice = new Dice(numberOfDice);
   }

   public void Play(){
     for(Player player : players) {
       setCurrentPlayer(player);

       int diceRoll = dice.roll();
       player.move(diceRoll);

       System.out.println(player.getName() + " on tile " + player.getCurrentTile().getTileId());

       if(getWinner() != null) {
         break;
       }
     }

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

   public Dice getDice(){
     return dice;
   }

    public List<Player> getPlayers(){
        return players;
    }

    public Player getCurrentPlayer(){
        return currentPlayer;
    }

    public void setCurrentPlayer(Player player){
        currentPlayer = player;
    }

}
