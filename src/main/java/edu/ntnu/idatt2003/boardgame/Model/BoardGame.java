package edu.ntnu.idatt2003.boardgame.Model;

import com.google.gson.JsonObject;
import edu.ntnu.idatt2003.boardgame.readers.BoardFileReaderGson;
import edu.ntnu.idatt2003.boardgame.readers.ReadPlayers;
import edu.ntnu.idatt2003.boardgame.writers.BoardFactory;
import edu.ntnu.idatt2003.boardgame.writers.BoardFileWriterGson;
import edu.ntnu.idatt2003.boardgame.writers.WritePlayers;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BoardGame {
    private Board board;
    private Dice dice;
    private PlayerHolder playerHolder;
    private Player winner = null;

    public BoardGame() {
    }

    public void initializeBoard(String chosenGame, int size, String filename){
        JsonObject boardData = BoardFactory.serializeTiles(chosenGame, size);
        BoardFileWriterGson writer = new BoardFileWriterGson();
        writer.writeJsonToFile(boardData, filename);

        BoardFileReaderGson reader = new BoardFileReaderGson();
        this.board = reader.readTilesFromFile(filename);
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

    public void play() {

        playerHolder = getPlayerHolder();
        playerHolder.setCurrentPlayer(playerHolder.getPlayers().get(playerHolder.getNextPlayerIndex()));


        int totalEyes = dice.roll();

        int destTileId = playerHolder.getCurrentPlayer().getCurrentTile().getId() + totalEyes;

        if(destTileId <= getBoard().getTiles().size() && board.getTiles().get(destTileId-1).getAction() != null) {
            board.getTiles().get(destTileId-1).getAction().perform(playerHolder.getCurrentPlayer());
        }
        else if (destTileId > getBoard().getTiles().size()-1) {
            destTileId = (2 * getBoard().getTiles().size()) - (destTileId);
            playerHolder.getCurrentPlayer().placeOnTile(board, destTileId);
            if(board.getTiles().get(destTileId-1).getAction() != null){
                board.getTiles().get(destTileId-1).getAction().perform(playerHolder.getCurrentPlayer());
            }
        }
        else{
            playerHolder.getCurrentPlayer().placeOnTile(board, destTileId);
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

    public Board getBoard() {
        return board;
    }

    public Dice getDice() {
        return dice;
    }

}










