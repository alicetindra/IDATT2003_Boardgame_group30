package edu.ntnu.idatt2003.boardgame.Controller;

import edu.ntnu.idatt2003.boardgame.BoardGameApp;
import edu.ntnu.idatt2003.boardgame.Model.*;
import edu.ntnu.idatt2003.boardgame.View.BoardGameView;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GameController {
    BoardGameView view;
    List<String> listOfPlayers = new ArrayList<>();
    BoardGame boardGame = new BoardGame();
    Board board;

    public GameController(BoardGameView view) {
        this.view = view;
        view.initialize();
        attachEventHandlers();
    }

    private void attachEventHandlers() {
        view.getAddPlayerButton().setOnAction(e -> {
            listOfPlayers.add(view.getPlayerName().getText()+","+view.getPlayerColorMenu().getSelectionModel().getSelectedItem());
        });

        view.getMakeGameButton().setOnAction(actionEvent -> {
            try {
                handleMakeGame(actionEvent);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        view.getStartRoundButton().setOnAction(this::handleStartRound);

        view.getMainMenuButton().setOnAction(e -> restartGame());


    }


    private void handleMakeGame(ActionEvent actionEvent) throws IOException {
        view.getLayout().getChildren().clear();
        try {
            boardGame.createPlayerHolder("src/main/resources/players.csv",listOfPlayers);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        for(Player p : boardGame.getPlayerHolder().getPlayers()){
            ImageView imageView = new ImageView(new Image("images/"+p.getColor()+".png"));
            p.setImageView(imageView);
            p.setBoardGame(boardGame);
        }
        boardGame.initializeBoard(view.getGameName(),view.getBoardSizeMenu().getValue(),"src/main/resources/hardcodedBoards.json");
        board = boardGame.getBoard();

        boardGame.initializeDice(Integer.parseInt(view.getDiceField().getText()));

        view.createGridBoard(board);
        view.createTitleBox();
        view.createRulesColumn();
        view.createStartButton();
        view.createMainMenuButton();
        view.createInfoColumn(boardGame.getPlayerHolder());

        view.getInfoColumn().getChildren().add(view.getStartRoundButton());
        view.getRulesColumn().getChildren().add(view.getMainMenuButton());

        view.createMainLayout(view.getGrid(),view.getTitleBox(),view.getRulesColumn(),view.getInfoColumn());

        for(Player p : boardGame.getPlayerHolder().getPlayers()){
            p.placeOnTile(board,1);
        }
    }

    private void restartGame() {
        boardGame.undoWinner(boardGame.getWinner());
        view.getStartRoundButton().setDisable(false);
        view.getInfoColumn().getChildren().clear();
        view.getLayout().getChildren().clear();

        listOfPlayers.clear();
        boardGame = new BoardGame();
        board = null;

        view.getLayout().getChildren().clear();

        view.createMainMenu();

        attachEventHandlers();
    }
    private void handleStartRound(ActionEvent actionEvent) {
        view.getDisplayInfoBox().getChildren().clear();

        boardGame.play();

        displayDice();

        Player currentPlayer = boardGame.getPlayerHolder().getCurrentPlayer();
        int newTileId = currentPlayer.getCurrentTile().getId();

        removePlayerImageFromOldTile(currentPlayer);

        addPlayerImageToNewTile(currentPlayer, newTileId);

        checkForWinner();
    }

    public void displayDice(){
        view.getDieBox().getChildren().clear();
        for(Die d : boardGame.getDice().getListOfDice()){
            ImageView die = new ImageView(new Image("/images/dice"+d.getValue()+".png"));
            die.setFitHeight(40);
            die.setFitWidth(40);
            view.getDieBox().getChildren().add(die);
        }
        view.getDisplayInfoBox().getChildren().add(view.getDieBox());
    }

    private void removePlayerImageFromOldTile(Player player){
        for(Tile t : board.getTiles()){
            t.getTileBox().getChildren().removeIf(node ->
                    node instanceof ImageView && matchesPlayerImage((ImageView) node, player.getColor()));
        }
    }

    private boolean matchesPlayerImage(ImageView imageView, String playerColor){
        String imagePath = imageView.getImage().getUrl();
        return imagePath != null && imagePath.contains(playerColor.toLowerCase());
    }

    private void addPlayerImageToNewTile(Player player, int newTileId){
        for(Tile t : board.getTiles()){
            if(t.getId() == newTileId){
                t.getTileBox().getChildren().add(view.getPlayerImage(player));
            }
        }
    }

    private void checkForWinner(){
        if(boardGame.getWinner()!=null){
            String winnerName = boardGame.getWinner().getName();
            String winnerColor = boardGame.getWinner().getColor();

            view.makeWinnerBox(winnerName, winnerColor);
            view.getStartRoundButton().setDisable(true);
            displayWinnerMessage();
        }

    }

    private void displayWinnerMessage(){
        view.getLayout().getChildren().clear();
        view.getLayout().getChildren().add(view.getWinnerBox());
    }




}
