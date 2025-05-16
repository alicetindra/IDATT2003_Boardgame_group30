package edu.ntnu.idatt2003.boardgame.Model;


import edu.ntnu.idatt2003.boardgame.Model.actions.PassStartAction;
import javafx.scene.image.ImageView;

import java.util.Random;

public class Player {
    private final String name;
    private final String color;
    private Tile currentTile;
    private BoardGame boardGame;
    private ImageView imageView;
    private int money = 0;
    private boolean inJail = false;
    private int jailTurnsLeft = 3;

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

    public String getName(){
        return name;
    }

    public String getColor(){
        return color;
    }

    public Tile getCurrentTile(){
        return currentTile;
    }

    public void placeOnTile(Board board, int id){
        this.currentTile = board.getTiles().get(id-1);
    }

    public void editMoney(int value){
        money += value;
        if(money <= 0){
            getBoardGame().notifyObservers("bankrupt");
        }
    }
    public int getMoney(){
        return money;
    }


    public void setInJail(boolean inJail) {
        this.inJail = inJail;
        this.jailTurnsLeft = inJail ? 3 : 0;
    }

    public boolean isInJail() {
        return inJail;
    }

    public void decrementJailTurn() {
        if (inJail && jailTurnsLeft > 0) {
            jailTurnsLeft--;
            if (jailTurnsLeft == 0) {
                boardGame.notifyObservers("usedUpTurns");
                jailTurnsLeft = 3;
            }
        }
    }

    public void releaseFromJail() {
        this.inJail = false;
        this.jailTurnsLeft = 0;
        boardGame.alertRelease();
    }


    public void attemptRollExit(int dieRoll) {
        if (dieRoll == 6) {
            releaseFromJail();
        } else {
            decrementJailTurn();
        }
    }

    public void payToExit() {
        editMoney(-50);
        releaseFromJail();
    }

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

    public void setImageView(ImageView imageView){
        this.imageView = imageView;
    }
    public ImageView getImageView(){
        return imageView;
    }

    public void setBoardGame(BoardGame boardGame){
        this.boardGame = boardGame;
    }

    public BoardGame getBoardGame(){
        return boardGame;
    }

}