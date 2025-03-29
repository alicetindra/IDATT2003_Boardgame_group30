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
        this.playerHolder = new PlayerHolder();
    }

    public void initializeBoard(String chosenGame, int size, String filename){
        JsonObject boardData = BoardFactory.serializeTiles(chosenGame, size);
        BoardFileWriterGson writer = new BoardFileWriterGson();
        writer.writeJsonToFile(boardData, filename);

        BoardFileReaderGson reader = new BoardFileReaderGson();
        this.board = reader.readTilesFromFile(filename);

        if (board != null && board.getTiles() != null && !board.getTiles().isEmpty()) {
            System.out.println("Board for " + chosenGame + " initialized with " + size + " tiles.");
        } else {
            System.out.println("Failed to initialize board for " + chosenGame + ".");
        }

    }

    public void initializeDice(int numberOfDice){
        this.dice = new Dice(numberOfDice);
        System.out.println("Initialized dice with " + numberOfDice + " dice.");
    }


    //Players
    public void loadOrCreatePlayersFromFile(String filename, List<String> playerString) {
        //Load players form file
        List<Player> players = ReadPlayers.readPlayersFromFile(filename);

        if(players.isEmpty() && playerString != null){
            players = new ArrayList<>();

            for(String playerData : playerString){
                String[] parts = playerData.split(",");
                if(parts.length == 2){
                    Player newPlayer = new Player(parts[0].trim(), parts[1].trim());
                    players.add(newPlayer);
                }
            }
            //save new player to file
            try{
                WritePlayers.writePlayersToFile(filename, playerString);
                System.out.println("New players saved to " + filename);
            } catch (IOException e) {
                System.out.println("Error saving players to file: " + filename);
            }
        }
        initializePlayers(players);
    }

    public void initializePlayers(List<Player> players) {
        this.playerHolder.setPlayers(players);

        if (!players.isEmpty()) {
            playerHolder.setCurrentPlayer(players.getFirst()); // Sätt första spelaren som startspelare
            System.out.println("Players initialized.");
        } else {
            System.out.println("No players found or created.");
        }
    }



    //Play
    public void playTurn() {
        //The next player in line is set to current player
        playerHolder = getPlayerHolder();
        playerHolder.setCurrentPlayer(playerHolder.getPlayers().get(playerHolder.getNextPlayerIndex()));

        System.out.println("current player: " + playerHolder.getCurrentPlayer().getName());
        //Current player rolls the dice
        int totalEyes = dice.roll();
        System.out.println("Total eyes: " + totalEyes);

        //Set current players new current tile
        int destTileId = playerHolder.getCurrentPlayer().getCurrentTile().getId() + totalEyes;

        System.out.println("Destination tile id: " + destTileId);
        //Handle special cases for destination tile
        if(destTileId <= getBoard().getTiles().size()-1 && board.getTiles().get(destTileId-1).getAction() != null) {
            board.getTiles().get(destTileId-1).getAction().perform(playerHolder.getCurrentPlayer());
        }
        else if (destTileId == board.getTiles().size()) {
            playerHolder.getCurrentPlayer().placeOnTile(board, destTileId);
            declareWinner(playerHolder.getCurrentPlayer());
            System.out.println("Player " + playerHolder.getCurrentPlayer().getColor() + " has won!");
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
        System.out.println("Congratulations! " + winner.getName() + " has won!");
    }

    public Player getWinner(){
        return winner;
    }

    public void undoWinner(Player winner){
        this.winner = null;
    }

    public PlayerHolder getPlayerHolder() {
        return playerHolder;
    }

    public Board getBoard() {
        if (board.getTiles() == null || board.getTiles().isEmpty()) {
            System.out.println("Error: Board tiles not initialized!");
        }
        return board;
    }

    public Dice getDice() {
        return dice;
    }

}










