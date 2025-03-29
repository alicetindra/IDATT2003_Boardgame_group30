package edu.ntnu.idatt2003.boardgame.Model;


public class Player {
    private String name;
    private String color;
    private Tile currentTile;
    private BoardGame boardGame;

    public Player(String name, String color) {
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

    public void move(int steps, Board board){
        if(currentTile == null){
            throw new IllegalStateException("Player must be placed on tile before moving");
        }

        int currentTileId = this.currentTile.getId();
        int destinationTileId = currentTileId + steps;

        // Handle cases where the destination tile exceeds the board size
        if (destinationTileId > board.getTiles().size()) {
            // Bounce back if exceeding the last tile
            destinationTileId = (2 * board.getTiles().size()) - destinationTileId;
        }

        //Set players new position

        placeOnTile(board, destinationTileId);

        Tile destinationTile = board.getTiles().get(destinationTileId-1);
        //Perform action
        if(destinationTile.getAction() != null){
            destinationTile.getAction().perform(this);
        }
    }

    public void setBoardGame(BoardGame boardGame){
        this.boardGame = boardGame;
    }

    public BoardGame getBoardGame(){
        return boardGame;
    }

}