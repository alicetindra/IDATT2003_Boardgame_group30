package edu.ntnu.idatt2003.boardgame.componentHolders;


import com.google.gson.JsonObject;
import edu.ntnu.idatt2003.boardgame.components.Player;
import edu.ntnu.idatt2003.boardgame.readers.ReadBoard;
import edu.ntnu.idatt2003.boardgame.readers.ReadPlayers;
import edu.ntnu.idatt2003.boardgame.writers.WriteBoard;
import edu.ntnu.idatt2003.boardgame.writers.WritePlayers;

import java.io.IOException;
import java.util.List;

public class BoardGame {
    Board board;
    Dice dice;
    PlayerHolder playerHolder = new PlayerHolder();
    Player winner = null;

    public BoardGame() {

    }

    //Board, det verkar osm vi kan ta bort de 3 första raderna, vi behöver bara writeboard om vi ska ändra antalet tiles.
    public void createBoard(int n, String filename) {
        WriteBoard writeBoard = new WriteBoard();

        JsonObject tileJson = writeBoard.serializeTiles(n);
        writeBoard.writeJsonToFile(tileJson, filename);

        board = ReadBoard.readTilesFromFile(filename);
    }

    public Board getBoard() {
        return board;
    }


    //Dice
    public void createDice(int n) {
        dice = new Dice(n);
    }

    public Dice getDice() {
        return dice;
    }


    //Players
    public void createPlayerHolder(String filename, List<String> playerString) throws IOException {
        WritePlayers.writePlayersToFile(filename, playerString);

        playerHolder.setPlayers(ReadPlayers.readPlayersFromFile(filename));
        this.playerHolder = playerHolder;
    }

    public PlayerHolder getPlayerHolder() {
        return playerHolder;
    }

    //Play
    public void play() {
        //The next player in line is set to current player
        playerHolder = getPlayerHolder();
        playerHolder.setCurrentPlayer(playerHolder.getPlayers().get(playerHolder.getNextPlayerIndex()));

        //Current player rolls the dice
        int totalEyes = dice.roll();

        //Set current players new current tile
        int destTileId = playerHolder.getCurrentPlayer().getCurrentTile().getTileId() + totalEyes;

        //Handle special cases for destination tile
        if(destTileId <= getBoard().getTiles().size()-1 && board.getTiles().get(destTileId-1).getAction() != null) {
            board.getTiles().get(destTileId-1).getAction().perform(playerHolder.getCurrentPlayer());
        }
        else if (destTileId == board.getTiles().size()) {
            playerHolder.getCurrentPlayer().setCurrentTile(board, destTileId);
            declareWinner(playerHolder.getCurrentPlayer());
            System.out.println("Player " + playerHolder.getCurrentPlayer().getColor() + " has won!");
        }
        else if (destTileId > getBoard().getTiles().size()-1) {
            destTileId = (2 * getBoard().getTiles().size()) - (destTileId);
            playerHolder.getCurrentPlayer().setCurrentTile(board, destTileId);
            if(board.getTiles().get(destTileId-1).getAction() != null){
                board.getTiles().get(destTileId-1).getAction().perform(playerHolder.getCurrentPlayer());
            }
        }
        else{
            playerHolder.getCurrentPlayer().setCurrentTile(board, destTileId);
        }

    }

    public void declareWinner(Player winner) {
        this.winner = winner;
    }
    public Player getWinner(){
       return winner;
    }

    public void undoWinner(Player winner){
        this.winner = null;
    }
}











