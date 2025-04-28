package edu.ntnu.idatt2003.boardgame.Controller;

import edu.ntnu.idatt2003.boardgame.Model.Board;
import edu.ntnu.idatt2003.boardgame.Model.BoardGame;

import edu.ntnu.idatt2003.boardgame.Model.Player;
import edu.ntnu.idatt2003.boardgame.Model.Tile;
import edu.ntnu.idatt2003.boardgame.Observer.BoardGameObserver;
import edu.ntnu.idatt2003.boardgame.View.MenuView;
import edu.ntnu.idatt2003.boardgame.View.MonopolyView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class MonopolyController implements BoardGameObserver {

    private final MonopolyView monopolyView;
    private final MenuView menuView;
    private final BoardGame boardGame;
    private Board board;

    public MonopolyController(MonopolyView monopolyView, MenuView menuView ,BoardGame boardGame) {
        this.monopolyView = monopolyView;
        this.menuView = menuView;
        this.boardGame = boardGame;

        monopolyView.initialize();
        attachEventHandlers();
    }

    @Override
    public void update(String event, BoardGame boardGame){
        if (event.equals("playerMoved")) {
            Player currentPlayer = boardGame.getPlayerHolder().getCurrentPlayer();
            int newTileId = currentPlayer.getCurrentTile().getId();

            addPlayerImageToNewTile(currentPlayer, newTileId);
        }
    }

    private void attachEventHandlers() {
        menuView.getMainMenuButton().setOnAction(e -> clearGame());
        monopolyView.getStartRoundButton().setOnAction(e -> startRound());
    }


    public void initializeBoard(int width, int height) {
        boardGame.initializeBoard("monopoly", (width*2+height*2), "hardcodedBoards.json");
        board = boardGame.getBoard();
        monopolyView.createBoardGrid(width, height, board);
    }

    public void setUpMonopolyGame() throws IOException {
        boardGame.removeObserver(this);
        boardGame.addObserver(this);
        monopolyView.getMonopolyLayout().getChildren().clear();
        monopolyView.getRootLayout().getChildren().clear();

        //need to take in user input, this is just for testing
        initializeBoard(8,6);

        monopolyView.createMonopolyLayout();
        BorderPane gameLayout = monopolyView.getMonopolyLayout();
        monopolyView.getButtonBox().getChildren().addAll(menuView.getMainMenuButton(), monopolyView.getStartRoundButton());

        menuView.getMenuLayout().getChildren().clear();
        menuView.getMenuLayout().getChildren().add(gameLayout);


        boardGame.initializeDice(Integer.parseInt(menuView.getDiceField().getText()));


        //Setting up the players and placing them at start
        try {
            boardGame.createPlayerHolder("src/main/resources/players.csv",boardGame.getListOfPlayers());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        for(Player p : boardGame.getPlayerHolder().getPlayers()){
            ImageView imageView = new ImageView(new Image("images/"+p.getColor()+".png"));
            p.setImageView(imageView);
            p.setBoardGame(boardGame);
            p.placeOnTile(board,1);
            addPlayerImageToNewTile(p, 1);
        }
    }

    private void addPlayerImageToNewTile(Player player, int newTileId){
        for(Tile t : board.getTiles()){
            if(t.getId() == newTileId){
                t.getTileBox().getChildren().add(monopolyView.getPlayerImage(player));
            }
        }
    }
    public void clearGame() {
        boardGame.undoWinner(boardGame.getWinner());
        boardGame.clearBoard();
        boardGame.undoCustomBoardLoad();

        monopolyView.getMonopolyLayout().getChildren().clear();

        menuView.getMButton().setSelected(false);
        menuView.getSLButton().setSelected(false);
        menuView.getPlayerData().clear();
        menuView.setPlayerColorBox();
        menuView.getDiceField().clear();
        menuView.getBoardSizeMenu().setDisable(false);
        menuView.createMainMenu();
    }
    private void startRound() {
        boardGame.play();
    }


}
