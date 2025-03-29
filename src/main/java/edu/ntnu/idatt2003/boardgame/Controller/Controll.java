package edu.ntnu.idatt2003.boardgame.Controller;

import edu.ntnu.idatt2003.boardgame.Model.*;
import edu.ntnu.idatt2003.boardgame.View.BoardGameView;
import edu.ntnu.idatt2003.boardgame.View.BoardView;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Controll {
    private BoardGameView view;
    private List<String> listOfPlayers = new ArrayList<>();
    private BoardGame game = new BoardGame();
    private PlayerHolder playerHolder;
    private Board board;


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


    public void handleSnakesAndLadders(){
        int selectedSize = view.getBoardSizeBox().getValue();
        try{
            game.initializeBoard("Snakes and ladders", selectedSize, "src/main/resources/hardcodedBoards.json");
            board = game.getBoard();

            view.setBoardBox(board);

            for(Player p: playerHolder.getPlayers()){
                p.placeOnTile(p.getBoardGame().getBoard(),1);
                Tile t = game.getBoard().getTiles().getFirst();
                t.getTileBox().getChildren().add(p.getImageView());
            }
            playerHolder.setCurrentPlayer(playerHolder.getPlayers().getLast());

            HBox titleWithImage = view.createTitle();
            VBox rulesColumn = view.createRulesColumn();
            VBox infoColumn = view.createInfoColumn(playerHolder);
            GridPane boardGrid = view.createGridBoard(board.getTiles().size(),board);
            Button startRoundButton = view.createStartButton();
            Button mainMenuButton = view.createMainMenuButton();

            startRoundButton.setOnAction(this::handleStartRoundButton);


            infoColumn.getChildren().add(startRoundButton);
            rulesColumn.getChildren().add(mainMenuButton);

            BorderPane gameLayout = view.createMainLayout(boardGrid, titleWithImage, rulesColumn, infoColumn);


            view.getLayout().getChildren().clear();
            view.getLayout().getChildren().add(gameLayout);
            StackPane.setAlignment(gameLayout, Pos.CENTER);

        }catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to initialize game layout ");
        }
        // Add logic for starting the game
    }

    private void handleStartRoundButton(ActionEvent event) {
        view.getDisplayInfoBox().getChildren().clear();

        game.play();

        displayDice();

        Player currentPlayer = playerHolder.getCurrentPlayer();
        int newTileId = currentPlayer.getCurrentTile().getId();

        //Remove current player image from board
        removePlayerImageFromOldTile(currentPlayer);

        //Add current player image on new tile
        addPlayerImageToNewTile(currentPlayer, newTileId);

        //view.updateInfoBox(currentPlayer, newTileId);

        //checkForWinner();
    }

    /**
     * Display dice box
     */
    public void displayDice(){
        view.getDieBox().getChildren().clear();
        view.getDieBox().setAlignment(Pos.CENTER);
        view.getDieBox().setSpacing(10);

        for(Die d : game.getDice().getListOfDice()){
            ImageView die = new ImageView(new Image("/images/dice"+d.getValue()+".png"));
            die.setFitHeight(40);
            die.setFitWidth(40);
            view.getDieBox().getChildren().add(die);
        }
        view.getDisplayInfoBox().getChildren().add(view.getDieBox());
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
