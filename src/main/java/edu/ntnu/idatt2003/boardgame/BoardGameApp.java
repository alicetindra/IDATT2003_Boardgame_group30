package edu.ntnu.idatt2003.boardgame;

public class BoardGameApp {

  BoardGame boardGame = new BoardGame();

  public void init(){

    this.boardGame.createBoard();
    Board board = boardGame.getBoard();
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
    this.boardGame.createDice(2);

    this.boardGame.addPlayer(new Player("Tindra", this.boardGame));
    this.boardGame.addPlayer(new Player("Nicoline", this.boardGame));
    this.boardGame.addPlayer(new Player("Mark", this.boardGame));



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
