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
