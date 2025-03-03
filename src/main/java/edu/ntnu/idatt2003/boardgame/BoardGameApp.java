package edu.ntnu.idatt2003.boardgame;


import javax.swing.*;


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
    JFrame frame = new JFrame("Boardgame app");
    frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLayout(null);




    JLabel label = new JLabel("SNAKES AND LADDERS");
    label.setBounds(120, 0, 500, 30);


    frame.add(label);
    frame.add(new JLabel("The following players are playing"));
    int i = 25;
    for(Player p: this.boardGame.getPlayers()){
      JLabel playerLabel = new JLabel(p.getName());
      frame.add(playerLabel);
      i += 20;
      playerLabel.setBounds(1500, i, 100, 10);
    }


    for(Player p: this.boardGame.getPlayers()){
      this.boardGame.setCurrentPlayer(p);
      p.placeOnTile(boardGame.getBoard().getTile(1));
    }


    int round = 1;
    int row = 40;
    int column = 20;


    while (this.boardGame.getWinner() == null){
      JLabel roundLable = new JLabel("Round nr "+round+":");
      frame.add(roundLable);
      roundLable.setBounds(column, row, 100, 10);
      row += 20;
      this.boardGame.Play();
      for(Player p: this.boardGame.getPlayers()){
        JLabel resultLabel = new JLabel("  "+p.getName() + " on tile " + p.getCurrentTile().getTileId());
        frame.add(resultLabel);
        resultLabel.setBounds(column, row, 150, 10);
        row += 20;
      }
      round++;
      if(row==400 || row==440){
        column += 200;
        row = 40;
      }
    }
    JLabel winnerLabel = new JLabel("The winner is " + this.boardGame.getWinner().getName());
    frame.add(winnerLabel);
    winnerLabel.setBounds(250, 600, 300, 30);




    frame.setVisible(true);




  }


}
