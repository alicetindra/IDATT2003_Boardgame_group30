package edu.ntnu.idatt2003.boardgame;


import com.google.gson.JsonObject;

public class BoardGame {
     Board board;
     Dice dice;
     PlayerHolder playerHolder = new PlayerHolder();

    public BoardGame() {

    }

    //Board
    public void createBoard(int n, String filename){
        WriteBoard writeBoard = new WriteBoard();

            JsonObject tileJson = writeBoard.serializeTiles(n);
            writeBoard.writeJsonToFile(tileJson, filename);

            board = ReadBoard.readTilesFromFile(filename);
    }
    public Board getBoard(){
        return board;
    }


    //Dice
    public void createDice(int n){
        dice = new Dice(n);
    }
    public Dice getDice(){
        return dice;
    }


    //Players
    public void createPlayerHolder(String filename){
        playerHolder.setPlayers(ReadPlayers.readPlayersFromFile(filename));
    }
    public PlayerHolder getPlayerHolder(){
        return playerHolder;
    }

    //Play
    public void play(){
        //The next player in line is set to current player
        playerHolder = getPlayerHolder();
        playerHolder.setCurrentPlayer(playerHolder.getPlayers().get(playerHolder.getNextPlayerIndex()));

        //Current player rolls the dice
        int totalEyes = dice.roll();

        //Set current players new current tile
        int destTileId = playerHolder.getCurrentPlayer().getCurrentTile().getTileId()+totalEyes;

        if(destTileId == board.getTiles().getLast().getTileId()){
            //Declare the winner
            System.out.println("Player " + playerHolder.getCurrentPlayer().getColor() + " has won!");
        }
        else if(destTileId>getBoard().getTiles().size()-1){
            int newDestTileId = (2*getBoard().getTiles().size()-2)-(destTileId);
            playerHolder.getCurrentPlayer().setCurrentTile(board, newDestTileId);
        }
        else{
            playerHolder.getCurrentPlayer().setCurrentTile(board, destTileId);
        }    
        }

    }












