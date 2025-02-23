package edu.ntnu.idatt2003.boardgame;

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

    //Placing all players at start
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