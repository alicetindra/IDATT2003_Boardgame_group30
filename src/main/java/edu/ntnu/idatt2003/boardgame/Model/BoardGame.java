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

/**
 * Represents the central logic and state of a board game.
 * Manages the board, dice, players, observers, and game flow.
 */
public class BoardGame {
    private final List<BoardGameObserver> observers = new ArrayList<>();
    private final List<String> listOfPlayers = new ArrayList<>();

    private Board board;
    private Dice dice;
    private PlayerHolder playerHolder;
    private Player winner = null;
    private boolean customBoardLoaded = false;
    private String gameType;
    private CardManager cardManager;

    /**
     * Constructs an empty BoardGame instance.
     */
    public BoardGame() {
    }

    /**
     * Sets the card manager for this game.
     *
     * @param cardManager the card manager to use
     */
    public void setCardManager(CardManager cardManager) {
        this.cardManager = cardManager;
    }

    /**
     * Returns the card manager for this game.
     *
     * @return the card manager
     */
    public CardManager getCardManager() {
        return cardManager;
    }

    /**
     * Adds an observer to the board game.
     *
     * @param observer the observer to add
     */
    public void addObserver(BoardGameObserver observer) {
        observers.add(observer);
    }

    /**
     * Removes an observer from the board game.
     *
     * @param observer the observer to remove
     */
    public void removeObserver(BoardGameObserver observer) {
        observers.remove(observer);
    }

    /**
     * Notifies all registered observers with the given event.
     *
     * @param event the event message
     */
    public void notifyObservers(String event) {
        for (BoardGameObserver observer : observers) {
            observer.update(event, this);
        }
    }

    /**
     * Initializes a board using the specified game type, size, and filename.
     *
     * @param chosenGame the type of game (e.g. "Monopoly")
     * @param size the size of the board
     * @param filename the file to save and load the board from
     */
    public void initializeBoard(String chosenGame, int size, String filename){
        JsonObject boardData = BoardFactory.serializeTiles(chosenGame, size);
        BoardFileWriterGson writer = new BoardFileWriterGson();
        writer.writeJsonToFile(boardData, filename);

        BoardFileReaderGson reader = new BoardFileReaderGson();
        this.board = reader.readTilesFromFile(filename);
        this.gameType = chosenGame;
        System.out.println(chosenGame);
    }

    /**
     * Returns the type of the current game.
     *
     * @return the game type
     */
    public String getGameType() {
        return gameType;
    }

    /**
     * Loads a custom board from a file.
     * Skips over chosen game, size and filename. Just loads the board it gets
     *
     * @param filePath the path to the custom board file
     */
    public void loadCustomBoard(String filePath) {
        BoardFileReaderGson reader = new BoardFileReaderGson();
        this.board = reader.readTilesFromFile(filePath);
        customBoardLoaded = true;
    }

    /**
     * Resets the custom board flag, effectively undoing the custom load.
     */
    public void undoCustomBoardLoad(){
        customBoardLoaded = false;
    }

    /**
     * Checks whether a custom board has been loaded.
     *
     * @return true if a custom board is loaded, false otherwise
     */
    public boolean isCustomBoardLoaded(){
        return customBoardLoaded;
    }

    /**
     * Initializes dice with the given number of dice.
     *
     * @param numberOfDice the number of dice to use
     */
    public void initializeDice(int numberOfDice){
        this.dice = new Dice(numberOfDice);
    }

    /**
     * Creates and initializes the player holder by writing and reading from a file.
     *
     * @param filename the filename to write to and read from
     * @param playerString a list of player name-color pairs
     * @throws IOException if an I/O error occurs
     */
    public void createPlayerHolder(String filename, List<String> playerString) throws IOException {
        WritePlayers.writePlayersToFile(filename, playerString);
        playerHolder = new PlayerHolder();
        playerHolder.setPlayers(ReadPlayers.readPlayersFromFile(filename));
    }

    /**
     * Returns the player holder managing the players.
     *
     * @return the player holder
     */
    public PlayerHolder getPlayerHolder() {
        return playerHolder;
    }

    /**
     * Alerts observers that a player has been released (e.g., from jail).
     */
    public void alertRelease(){
        notifyObservers("release");
    }

    /**
     * Executes a round of gameplay for the current player.
     * Rolls the dice, checks jail status, and moves the player.
     */
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

    /**
     * Adds a player to the list using their name and color.
     *
     * @param name the player's name
     * @param color the player's color
     */
    public void addPlayer(String name, String color){
        listOfPlayers.add(name + "," + color);
    }

    /**
     * Removes a player from the list.
     *
     * @param player the player to remove
     */
    public void removePlayer(Player player){
        listOfPlayers.remove(player.getName()+","+player.getColor());
    }

    /**
     * Returns the list of player name-color pairs.
     *
     * @return the list of players
     */
    public List<String> getListOfPlayers(){
        return listOfPlayers;
    }

    /**
     * Declares a winner and notifies observers.
     *
     * @param winner the winning player
     */
    public void declareWinner(Player winner) {
        this.winner = winner;
        notifyObservers("winnerDeclared");
    }

    /**
     * Returns the current winner of the game.
     *
     * @return the winner, or null if not set
     */
    public Player getWinner(){
        return winner;
    }

    /**
     * Resets the winner field, effectively undoing the declaration.
     *
     * @param winner the player who has won
     */
    public void undoWinner(Player winner){
        this.winner = null;
    }

    /**
     * Returns the game board.
     *
     * @return the board
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Clears the board and resets the list of players.
     */
    public void clearBoard(){
        board = null;
        listOfPlayers.clear();
    }

    /**
     * Returns the dice object used in the game.
     *
     * @return the dice
     */
    public Dice getDice() {
        return dice;
    }

}









