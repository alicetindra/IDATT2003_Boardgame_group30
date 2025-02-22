package edu.ntnu.idatt2003.boardgame;

import java.util.HashMap;

public class Main {

  public static void main(String[] args) {


    BoardGame game = new BoardGame();

    Player player = new Player("Nicoline", game);
    Player player2 = new Player("Tindra", game);
    game.addPlayer(player);
    game.addPlayer(player2);

    game.createDice(2);

    game.createBoard();
    Board board = game.getBoard();

    for(int i = 0; i<91; i++){
      board.addTile(new Tile(i));
    }

    for(Player p: game.getPlayers()){
      p.placeOnTile(board.getTile(1));
    }

    System.out.println("***SNAKES AND LADDERS***");
    game.setCurrentPlayer(player);
    int i = 1;
    while(game.getWinner() == null){
      System.out.println("\n*Round "+i+"*");
      game.Play();
      i++;
    }
    System.out.println("\n***End of game***");
    System.out.println("The winner is: "+game.getWinner().getName()+"!");



  }
}