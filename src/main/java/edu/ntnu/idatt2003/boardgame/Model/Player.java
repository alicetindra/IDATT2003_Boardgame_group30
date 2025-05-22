package edu.ntnu.idatt2003.boardgame.Model;


import edu.ntnu.idatt2003.boardgame.Model.actions.PassStartAction;
import javafx.scene.image.ImageView;

/**
 * Represents a player in the board game.
 * Each player has a name, color, position on the board, money, and can be placed in jail.
 */
public class Player {
    private final String name;
    private final String color;
    private Tile currentTile;
    private BoardGame boardGame;
    private ImageView imageView;
    private int money = 0;
    private boolean inJail = false;
    private int jailTurnsLeft = 3;

    /**
     * Constructs a new Player with the given name and color.
     *
     * @param name the name of the player
     * @param color the color representing the player
     * @throws NullPointerException if name or color is null
     * @throws IllegalArgumentException if name or color is blank
     */
    public Player(String name, String color) {
        if(name == null || color == null) {
            throw new NullPointerException("Player name and color cannot be null");
        }
        else if(name.isBlank() || color.isBlank()){
            throw new IllegalArgumentException("Player name and color cannot be blank.");
        }
        this.name = name;
        this.color = color;
    }

    /**
     * Returns the name of the player.
     *
     * @return the player's name
     */
    public String getName(){
        return name;
    }

    /**
     * Returns the color representing the player.
     *
     * @return the player's color
     */
    public String getColor(){
        return color;
    }

    /**
     * Returns the current tile the player is on.
     *
     * @return the current tile
     */
    public Tile getCurrentTile(){
        return currentTile;
    }

    /**
     * Places the player on the tile with the given ID.
     *
     * @param board the game board
     * @param id the ID of the tile (1-indexed)
     */
    public void placeOnTile(Board board, int id){
        this.currentTile = board.getTiles().get(id-1);
    }

    /**
     * Modifies the player's money by the given value.
     * If money drops to or below zero, notifies observers of bankruptcy.
     *
     * @param value the amount to add or subtract from money
     */
    public void editMoney(int value){
        money += value;
        if(money <= 0){
            getBoardGame().notifyObservers("bankrupt");
        }
    }

    /**
     * Returns the current amount of money the player has.
     *
     * @return the player's money
     */
    public int getMoney(){
        return money;
    }

    /**
     * Sets the player's jail status.
     * If jailed, sets jail turns left to 3. Otherwise, resets to 0.
     *
     * @param inJail true if the player is in jail, false otherwise
     */
    public void setInJail(boolean inJail) {
        this.inJail = inJail;
        this.jailTurnsLeft = inJail ? 3 : 0;
    }

    /**
     * Returns whether the player is currently in jail.
     *
     * @return true if in jail, false otherwise
     */
    public boolean isInJail() {
        return inJail;
    }

    /**
     * Decrements the number of jail turns left.
     * If the player has used up all turns, releases them and notifies observers.
     */
    public void decrementJailTurn() {
        if (inJail && jailTurnsLeft > 0) {
            jailTurnsLeft--;
            if (jailTurnsLeft == 0) {
                boardGame.notifyObservers("usedUpTurns");
                jailTurnsLeft = 3;
            }
        }
    }

    /**
     * Releases the player from jail and alerts the game.
     */
    public void releaseFromJail() {
        this.inJail = false;
        this.jailTurnsLeft = 0;
        boardGame.alertRelease();
    }

    /**
     * Attempts to exit jail by rolling a die.
     * If the die roll is 6, the player is released. Otherwise, a jail turn is used.
     *
     * @param dieRoll the result of the die roll
     */
    public void attemptRollExit(int dieRoll) {
        if (dieRoll == 6) {
            releaseFromJail();
        } else {
            decrementJailTurn();
        }
    }

    /**
     * Pays 50 $ to exit jail and releases the player.
     */
    public void payToExit() {
        editMoney(-50);
        releaseFromJail();
    }

    /**
     * Moves the player forward by a given number of steps and triggers tile actions as necessary.
     * Handles logic for passing the start tile and landing on action tiles.
     *
     * @param steps the number of steps to move
     */
    public void move(int steps){
        int destTile = this.currentTile.getId() + steps;
        Board board = this.boardGame.getBoard();


        if(destTile <= board.getTiles().size() && board.getTiles().get(destTile - 1).getAction() != null){
            board.getTiles().get(destTile -1).getAction().perform(this);
        } else if(destTile > board.getTiles().size()){
            if( this.boardGame.getGameType().equals("Snakes and ladders")) {
                destTile = (2 * board.getTiles().size() - destTile);
                this.placeOnTile(board, destTile);

            }
            else{
                destTile = destTile - board.getTiles().size();
                PassStartAction passStartAction = new PassStartAction("Player passed start and gets 100 money");
                passStartAction.perform(this);
                this.placeOnTile(board, destTile);
            }
            if (board.getTiles().get(destTile - 1).getAction() != null) {
                board.getTiles().get(destTile - 1).getAction().perform(this);
            }
        }
        else{
            this.placeOnTile(board, destTile);
        }

    }

    /**
     * Sets the image view representing the player on the UI.
     *
     * @param imageView the ImageView to associate with the player
     */
    public void setImageView(ImageView imageView){
        this.imageView = imageView;
    }

    /**
     * Returns the image view associated with the player.
     *
     * @return the player's ImageView
     */
    public ImageView getImageView(){
        return imageView;
    }

    /**
     * Sets the board game instance the player is part of.
     *
     * @param boardGame the board game
     */
    public void setBoardGame(BoardGame boardGame){
        this.boardGame = boardGame;
    }

    /**
     * Returns the board game the player belongs to.
     *
     * @return the board game
     */
    public BoardGame getBoardGame(){
        return boardGame;
    }

}