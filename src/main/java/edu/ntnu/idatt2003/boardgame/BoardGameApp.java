package edu.ntnu.idatt2003.boardgame;

public class BoardGameApp {

  BoardGame boardGame = new BoardGame();

  public void init(){

    this.boardGame.createBoard();
    this.boardGame.createDice(2);

    this.boardGame.addPlayer(new Player("Tindra", this.boardGame));
    this.boardGame.addPlayer(new Player("Nicoline", this.boardGame));
  }

  public void start(){
    System.out.println("***SNAKES AND LADDERS***");

    System.out.println("Following players are playing");
    for(Player p: this.boardGame.getPlayers()){
      System.out.println("Name: " +p.getName());
    }
    System.out.println();

    //placing all players on first tile
    for(Player p: this.boardGame.getPlayers()){
      this.boardGame.setCurrentPlayer(p);
      p.placeOnTile(boardGame.getBoard().getTile(1));
    }

    int round = 1;
    while (this.boardGame.getWinner() == null){
      System.out.println("Round number: " + round++);
      this.boardGame.Play();
      System.out.println();
    }
    System.out.println("The winner is " + this.boardGame.getWinner().getName());


  }

}
