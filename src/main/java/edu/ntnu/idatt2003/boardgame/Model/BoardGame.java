package edu.ntnu.idatt2003.boardgame.Model;

import com.google.gson.JsonObject;
import edu.ntnu.idatt2003.boardgame.Observer.BoardGameObserver;
import edu.ntnu.idatt2003.boardgame.readers.BoardFileReaderGson;
import edu.ntnu.idatt2003.boardgame.readers.ReadPlayers;
import edu.ntnu.idatt2003.boardgame.writers.BoardFactory;
import edu.ntnu.idatt2003.boardgame.writers.BoardFileWriterGson;
import edu.ntnu.idatt2003.boardgame.writers.WritePlayers;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BoardGame {
    private List<BoardGameObserver> observers = new ArrayList<>();
    private List<String> listOfPlayers = new ArrayList<>();

    private Board board;
    private Dice dice;
    private PlayerHolder playerHolder;
    private Player winner = null;
    private boolean customBoardLoaded = false;
    private String gameType;

    public BoardGame() {
    }

    public void addObserver(BoardGameObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(BoardGameObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers(String event) {
        for (BoardGameObserver observer : observers) {
            observer.update(event, this);
        }
    }

    public void initializeBoard(String chosenGame, int size, String filename){
        JsonObject boardData = BoardFactory.serializeTiles(chosenGame, size);
        BoardFileWriterGson writer = new BoardFileWriterGson();
        writer.writeJsonToFile(boardData, filename);

        BoardFileReaderGson reader = new BoardFileReaderGson();
        this.board = reader.readTilesFromFile(filename);
        this.gameType = chosenGame;
        System.out.println(chosenGame);
    }
    public String getGameType() {
        return gameType;
    }

    //Skips over chosen game, size and filename. Just loads the board it gets
    public void loadCustomBoard(String filePath) {
        BoardFileReaderGson reader = new BoardFileReaderGson();
        this.board = reader.readTilesFromFile(filePath);
        customBoardLoaded = true;
    }

    public void undoCustomBoardLoad(){
        customBoardLoaded = false;
    }

    public boolean isCustomBoardLoaded(){
        return customBoardLoaded;
    }


    public void initializeDice(int numberOfDice){
        this.dice = new Dice(numberOfDice);
    }

    //Players
    public void createPlayerHolder(String filename, List<String> playerString) throws IOException {
        WritePlayers.writePlayersToFile(filename, playerString);
        playerHolder = new PlayerHolder();
        playerHolder.setPlayers(ReadPlayers.readPlayersFromFile(filename));
    }

    public PlayerHolder getPlayerHolder() {
        return playerHolder;
    }

    public void alertRelease(){
        notifyObservers("release");
    }

    public void play() {

        playerHolder = getPlayerHolder();
        playerHolder.setCurrentPlayer(playerHolder.getPlayers().get(playerHolder.getNextPlayerIndex()));

        int totalEyes = dice.roll();


        if(playerHolder.getCurrentPlayer().isInJail()){
            notifyObservers("inJail");
            return;
        }

        playerHolder.getCurrentPlayer().move(totalEyes);
        notifyObservers("playerMoved");
    }

    public void addPlayer(String name, String color){
        listOfPlayers.add(name + "," + color);
    }
    public void removePlayer(Player player){
        listOfPlayers.remove(player.getName());
    }


    public List<String> getListOfPlayers(){
        return listOfPlayers;
    }

    public void declareWinner(Player winner) {
        this.winner = winner;
        notifyObservers("winnerDeclared");
    }

    public Player getWinner(){
        return winner;
    }

    public void undoWinner(Player winner){
        this.winner = null;
    }

    public Board getBoard() {
        return board;
    }

    public void clearBoard(){
        board = null;
        listOfPlayers.clear();
    }

    public Dice getDice() {
        return dice;
    }

}










