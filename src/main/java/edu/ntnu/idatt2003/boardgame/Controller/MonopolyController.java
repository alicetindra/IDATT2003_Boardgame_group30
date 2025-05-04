package edu.ntnu.idatt2003.boardgame.Controller;

import edu.ntnu.idatt2003.boardgame.Model.Board;
import edu.ntnu.idatt2003.boardgame.Model.BoardGame;

import edu.ntnu.idatt2003.boardgame.Model.Player;
import edu.ntnu.idatt2003.boardgame.Model.Tile;
import edu.ntnu.idatt2003.boardgame.Observer.BoardGameObserver;
import edu.ntnu.idatt2003.boardgame.View.MenuView;
import edu.ntnu.idatt2003.boardgame.View.MonopolyView;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MonopolyController implements BoardGameObserver {

    private final MonopolyView monopolyView;
    private final MenuView menuView;
    private final BoardGame boardGame;
    private Board board;
    private final Map<Tile, ImageView> houseViews = new HashMap<>();

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
            monopolyView.getDiceBox().getChildren().remove(monopolyView.getSellHouseButton());
            Player currentPlayer = boardGame.getPlayerHolder().getCurrentPlayer();
            int newTileId = currentPlayer.getCurrentTile().getId();

            addPlayerImageToNewTile(currentPlayer, newTileId);

            updateFees();
            updateMoneyBox();
            updateBuyHouseButton();
        }
    }

    private void updateFees() {
        Player p = boardGame.getPlayerHolder().getCurrentPlayer();
        Tile t = boardGame.getPlayerHolder().getCurrentPlayer().getCurrentTile();

        if(t.getOwner() != null && !t.getOwner().equals(p)){
            p.editMoney(-t.getFee());
            t.getOwner().editMoney(t.getFee());
        }
    }

    private void updateBuyHouseButton() {
        Tile tile = boardGame.getPlayerHolder().getCurrentPlayer().getCurrentTile();
        if(tile.getOwner() == null){
            monopolyView.getBuyHouseButton().setDisable(false);
        }
        else if(tile.getOwner()!= null) {
            if (tile.getOwner().equals(boardGame.getPlayerHolder().getCurrentPlayer())) {
                monopolyView.getDiceBox().getChildren().add(monopolyView.getSellHouseButton());
            }
        }
    }

    private void attachEventHandlers() {
        menuView.getMainMenuButton().setOnAction(e -> clearGame());
        monopolyView.getStartRoundButton().setOnAction(e -> startRound());
        monopolyView.getBuyHouseButton().setOnAction(e -> buyHouse());
        monopolyView.getSellHouseButton().setOnAction(e -> sellHouse());
    }

    private void buyHouse() {
        Tile tile = boardGame.getPlayerHolder().getCurrentPlayer().getCurrentTile();
        Player owner = boardGame.getPlayerHolder().getCurrentPlayer();
        if(tile.getId()<16){
            tile.setFee(75);
            if(owner.getMoney()-225<=0){
                getAlert("You cant afford this house \n You have "+owner.getMoney()+" and need over "+225);
                throw new IllegalArgumentException("Player cant afford this house for 225");
            }
            owner.editMoney(-225);
        }
        else{
            tile.setFee(150);
            if(owner.getMoney()-450<=0){
                getAlert("You cant afford this house \n You have "+owner.getMoney()+" and need over "+450);
                throw new IllegalArgumentException("Player cant afford this house for 450");
            }
            owner.editMoney(-450);
        }


        tile.setOwner(owner);
        monopolyView.getBuyHouseButton().setDisable(true);
        addHouseToTile();
        updateMoneyBox();
    }

    private void sellHouse() {
        Player owner = boardGame.getPlayerHolder().getCurrentPlayer();
        Tile tile = owner.getCurrentTile();

        tile.setOwner(null);
        owner.editMoney(tile.getFee() * 3);
        tile.setFee(0);

        ImageView house = houseViews.remove(tile);
        if (house != null) {
            tile.getTileBox().getChildren().remove(house);
        }
        updateMoneyBox();
    }

    public void addHouseToTile(){
        Player p = boardGame.getPlayerHolder().getCurrentPlayer();
        Tile tile = p.getCurrentTile();

        ImageView house = new ImageView(new Image("images/" + p.getColor() + "house.png"));
        house.setFitHeight(30);
        house.setPreserveRatio(true);

        tile.getTileBox().getChildren().add(house);
        houseViews.put(tile, house);
    }


    public void initializeBoard(int width, int height) {
        boardGame.initializeBoard("monopoly", (width*2+height*2), "hardcodedBoards.json");
        board = boardGame.getBoard();
        monopolyView.createBoardGrid(width, height, board);
    }

    public void updateMoneyBox(){
        String s = "";
        for(Player player: boardGame.getPlayerHolder().getPlayers()){
            checkBankruptcy(player);
            s += player.getColor() +" : "+ player.getMoney() + "\n";
        }
        monopolyView.updateMoneyBox(s);
    }

    private void checkBankruptcy(Player player) {
        if (player.getMoney() <= 0) {
            for (Tile tile : boardGame.getBoard().getTiles()) {
                if (player.equals(tile.getOwner())) {
                    tile.setOwner(null);
                    tile.setFee(0);

                    ImageView house = houseViews.remove(tile);
                    if (house != null) {
                        tile.getTileBox().getChildren().remove(house);
                        monopolyView.getBankRuptcyBox().getChildren().addAll(monopolyView.getPlayerImage(player), new Text(" went bankrupt!"));
                    }
                }
            }

            boardGame.getPlayerHolder().getPlayers().remove(player);
        }
    }

    public void setUpMonopolyGame() throws IOException {
        boardGame.removeObserver(this);
        boardGame.addObserver(this);
        monopolyView.getDiceBox().getChildren().clear();
        monopolyView.getMoneyBox().getChildren().clear();
        monopolyView.getButtonBox().getChildren().clear();
        monopolyView.getMonopolyLayout().getChildren().clear();
        monopolyView.getRootLayout().getChildren().clear();

        initializeBoard(8,7);

        monopolyView.createMonopolyLayout();
        BorderPane gameLayout = monopolyView.getMonopolyLayout();
        monopolyView.getButtonBox().getChildren().addAll(menuView.getMainMenuButton(), monopolyView.getStartRoundButton());

        menuView.getMenuLayout().getChildren().clear();
        menuView.getMenuLayout().getChildren().add(gameLayout);


        boardGame.initializeDice(Integer.parseInt(menuView.getDiceField().getText()));


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
            p.editMoney(500);
        }
        updateMoneyBox();
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

    private void getAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Message");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


}
