package edu.ntnu.idatt2003.boardgame.Controller;

import edu.ntnu.idatt2003.boardgame.Model.*;

import edu.ntnu.idatt2003.boardgame.Observer.BoardGameObserver;
import edu.ntnu.idatt2003.boardgame.View.MenuView;
import edu.ntnu.idatt2003.boardgame.View.MonopolyView;
import javafx.geometry.Pos;
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
    private final PropertyHolder propertyHolder = new PropertyHolder();

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
            monopolyView.getHouseButtonsBox().getChildren().remove(monopolyView.getSellHouseButton());
            Player currentPlayer = boardGame.getPlayerHolder().getCurrentPlayer();
            int newTileId = currentPlayer.getCurrentTile().getId();

            addPlayerImageToNewTile(currentPlayer, newTileId);

            updateFees();
            updateMoneyBox();
            updateBuyHouseButton();
        }
        if (event.equals("inJail")) {
            monopolyView.getStartRoundButton().setDisable(true);
            monopolyView.getJailButtonsBox().getChildren().addAll(monopolyView.getRollForSixButton(),monopolyView.getPayFeeButton());
            monopolyView.getBuyHouseButton().setDisable(true);  
        }
        if (event.equals("usedUpTurns")) {
            monopolyView.getJailButtonsBox().getChildren().clear();
            monopolyView.getStartRoundButton().setDisable(false);
        }
        if (event.equals("release")) {
            monopolyView.getJailButtonsBox().getChildren().clear();
            monopolyView.getDiceBox().getChildren().clear();
            monopolyView.getDiceBox().getChildren().add(new Text("You are released from jail"));
            monopolyView.getStartRoundButton().setDisable(false);
        }
        if(event.equals("Winner")) {
            monopolyView.getGameUpdates().getChildren().clear();
            monopolyView.getGameUpdates().getChildren().add(new Text(boardGame.getPlayerHolder().getPlayers().getFirst().getName()+" is the winner!"));
            monopolyView.getStartRoundButton().setDisable(true);
        }
    }

    private void updateFees() {
        Player currentPlayer = boardGame.getPlayerHolder().getCurrentPlayer();
        Tile t = boardGame.getPlayerHolder().getCurrentPlayer().getCurrentTile();

        if(propertyHolder.getProperties().get(t.getId())!=currentPlayer && propertyHolder.getProperties().get(t.getId())!=null){
            currentPlayer.editMoney(-propertyHolder.getFee(t.getId()));
        }
    }

    private void updateBuyHouseButton() {
        Tile tile = boardGame.getPlayerHolder().getCurrentPlayer().getCurrentTile();
        boolean housePresent = false;

        if(tile.getId() == 1 || tile.getId() == 8 || tile.getId() == 14) {
            monopolyView.getBuyHouseButton().setDisable(true);
        }

        if(propertyHolder.getProperties().get(tile.getId())!=null){
            housePresent = true;
            monopolyView.getBuyHouseButton().setDisable(true);
            if (propertyHolder.getProperties().get(tile.getId())== boardGame.getPlayerHolder().getCurrentPlayer()) {
                monopolyView.getHouseButtonsBox().getChildren().add(monopolyView.getSellHouseButton());
            }
        }
        if(!housePresent){
            monopolyView.getBuyHouseButton().setDisable(false);
        }
    }

    private void attachEventHandlers() {
        menuView.getMainMenuButton().setOnAction(e -> clearGame());
        monopolyView.getStartRoundButton().setOnAction(e -> startRound());
        monopolyView.getBuyHouseButton().setOnAction(e -> buyHouse());
        monopolyView.getSellHouseButton().setOnAction(e -> sellHouse());
        monopolyView.getPayFeeButton().setOnAction(e -> payReleaseFee());
        monopolyView.getRollForSixButton().setOnAction(e -> throwDieForRelease());
    }

    private void throwDieForRelease() {
        Player p = boardGame.getPlayerHolder().getCurrentPlayer();
        Die die = new Die();
        p.attemptRollExit(die.roll());
        monopolyView.getDiceBox().getChildren().add(new Text(die.getValue()+""));
    }

    private void payReleaseFee() {
        Player p = boardGame.getPlayerHolder().getCurrentPlayer();
        if(p.getMoney()<=200){
            getAlert("You dont have enough money to get out of jail");
            throw new IllegalArgumentException("You dont have enough money to get out of jail");
        }
        else{
            boardGame.getPlayerHolder().getCurrentPlayer().payToExit();
            updateMoneyBox();
        }
        monopolyView.getDiceBox().getChildren().remove(monopolyView.getPayFeeButton());
        displayDice();
    }

    private void buyHouse() {
        Tile tile = boardGame.getPlayerHolder().getCurrentPlayer().getCurrentTile();
        Player owner = boardGame.getPlayerHolder().getCurrentPlayer();

        if(owner.getMoney()<=propertyHolder.getPrice(tile.getId())){
            getAlert("You cant afford this house \n You have "+owner.getMoney()+" and need over "+propertyHolder.getPrice(tile.getId()));
            throw new IllegalArgumentException("Player cant afford this house");
        }

        propertyHolder.setOwner(tile.getId(), owner);
        owner.editMoney(-propertyHolder.getPrice(tile.getId()));

        monopolyView.getBuyHouseButton().setDisable(true);
        addHouseToTile(tile);
        updateMoneyBox();
    }

    private void sellHouse() {
        Player owner = boardGame.getPlayerHolder().getCurrentPlayer();
        Tile tile = owner.getCurrentTile();

        owner.editMoney(propertyHolder.getPrice(tile.getId()));
        propertyHolder.removeOwner(tile.getId());

        ImageView houseImage = houseViews.remove(tile);
        if (houseImage != null) {
            tile.getTileBox().getChildren().remove(houseImage);
        }

        updateMoneyBox();
    }



    public void addHouseToTile(Tile tile) {
        ImageView houseImage = propertyHolder.getImage(tile.getId());
        houseImage.setFitHeight(40);
        houseImage.setPreserveRatio(true);
        tile.getTileBox().getChildren().add(houseImage);
        houseViews.put(tile, houseImage);
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
            for (Integer key : propertyHolder.getProperties().keySet()) {
                Player owner = propertyHolder.getProperties().get(key);
                if (owner != null && owner.equals(player)) {
                    propertyHolder.removeOwner(key);

                    Tile tile = board.getTiles().get(key-1);
                    ImageView houseImage = houseViews.remove(tile);
                    if (houseImage != null) {
                        tile.getTileBox().getChildren().remove(houseImage);
                    }

                    monopolyView.getBankRuptcyBox().getChildren()
                            .addAll(monopolyView.getPlayerImage(player), new Text(" went bankrupt!"));
                }
            }

            boardGame.getPlayerHolder().getPlayers().remove(player);
        }

        if (boardGame.getPlayerHolder().getPlayers().size() == 1) {
            update("Winner", boardGame);
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

        propertyHolder.setProperties(boardGame.getBoard().getTiles().size());

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
        monopolyView.getDiceBox().getChildren().clear();
        monopolyView.getButtonBox().getChildren().clear();
        monopolyView.getMoneyBox().getChildren().clear();
        monopolyView.getGameUpdates().getChildren().clear();
        monopolyView.getHouseButtonsBox().getChildren().clear();
        monopolyView.getJailButtonsBox().getChildren().clear();

        menuView.getMButton().setSelected(false);
        menuView.getPlayerData().clear();
        menuView.setPlayerColorBox();
        menuView.getDiceField().clear();
        menuView.getBoardSizeMenu().setDisable(false);
        menuView.createMainMenu();
    }

    private void startRound() {
        boardGame.play();
        displayDice();
    }

    private void getAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Message");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void displayDice(){
        monopolyView.getDiceBox().getChildren().clear();
        Player p = boardGame.getPlayerHolder().getCurrentPlayer();
        monopolyView.getDiceBox().getChildren().add(new Text(p.getName()+", "+p.getColor()));
        if(p.isInJail()){
          monopolyView.getDiceBox().getChildren().add(new Text("\nYou are in jail. \n Roll for 6 in three tries \n or pay a 200 money fee"));
        }
         else{
            for(Die d : boardGame.getDice().getListOfDice()){
                ImageView die = new ImageView(new Image("/images/dice"+d.getValue()+".png"));
                die.setFitHeight(40);
                die.setFitWidth(40);
                monopolyView.getDiceBox().getChildren().add(die);
                monopolyView.getDiceBox().setAlignment(Pos.CENTER);
            }
        }

    }


}
