package edu.ntnu.idatt2003.boardgame.Controller;

import edu.ntnu.idatt2003.boardgame.Model.BoardGame;
import edu.ntnu.idatt2003.boardgame.Model.Player;
import edu.ntnu.idatt2003.boardgame.Model.PlayerHolder;
import edu.ntnu.idatt2003.boardgame.Model.Tile;
import edu.ntnu.idatt2003.boardgame.View.BoardGameView;
import edu.ntnu.idatt2003.boardgame.View.BoardView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Controll {
    private BoardGameView view;
    private List<String> listOfPlayers = new ArrayList<>();
    private BoardGame game = new BoardGame();
    private PlayerHolder playerHolder;


    public Controll(BoardGameView view) {
        this.view = view;
        view.initialize();
        attachEventHandlers();
    }

    private void attachEventHandlers() {
        view.getAddPlayerButton().setOnAction(e -> {
            listOfPlayers.add(view.getPlayerName().getText()+","+view.getPlayerColorBox().getSelectionModel().getSelectedItem());
        });

        view.getMakeGameButton().setOnAction(e -> {
            try {
                handleAddPlayers();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            handleAddDice(Integer.parseInt(view.getDiceField().getText()));

            handleChooseGame();

            view.getLayout().getChildren().clear();
            handleDisplayBoard();

            placeOnStart();
            view.getLayout().getChildren().add(view.getStartbutton());
        });

        view.getStartbutton().setOnAction(e -> {
            game.play();
            updatePositions();
        });

    }

    private void updatePositions() {
        Player currentPlayer = playerHolder.getCurrentPlayer();
        removePlayerImageFromOldTile(currentPlayer);
        addPlayerImageToNewTile(currentPlayer,currentPlayer.getCurrentTile().getId());

    }

    private void removePlayerImageFromOldTile(Player player){
        for(Tile t : game.getBoard().getTiles()){
            t.getTileBox().getChildren().removeIf(node ->
                    node instanceof ImageView && matchesPlayerImage(player));
        }
    }


    private boolean matchesPlayerImage(Player player){
        String imagePath = player.getImageView().getImage().getUrl();
        return imagePath != null && imagePath.contains(player.getColor().toLowerCase());
    }


    private void addPlayerImageToNewTile(Player player, int newTileId){
        for(Tile t : game.getBoard().getTiles()){
            if(t.getId() == newTileId){
                t.getTileBox().getChildren().add(player.getImageView());
            }
        }
    }

    private void placeOnStart() {
        for(Player p: playerHolder.getPlayers()) {
            p.placeOnTile(game.getBoard(),1);
        }
    }

    private void handleDisplayBoard() {
        view.setBoardBox(game.getBoard());
        view.getLayout().getChildren().add(view.getBoardBox());
    }

    private void handleChooseGame() {
        String gameName = view.getGameName();
        System.out.println(Integer.parseInt(view.getBoardSizeBox().getSelectionModel().getSelectedItem().toString()));
        game.initializeBoard(gameName,Integer.parseInt(view.getBoardSizeBox().getSelectionModel().getSelectedItem().toString()),"src/main/resources/hardcodedBoards.json");
    }

    private void handleAddDice(int n) {
        game.initializeDice(n);
    }

    private void handleAddPlayers() throws IOException {
        game.createPlayerHolder("src/main/resources/players.csv", listOfPlayers);
        playerHolder = game.getPlayerHolder();
        for(Player p: playerHolder.getPlayers()) {
            p.setBoardGame(game);
            p.setImageView(new ImageView(new Image(getClass().getResource("/images/" + p.getColor() + ".png").toExternalForm())));

        }
    }
}
