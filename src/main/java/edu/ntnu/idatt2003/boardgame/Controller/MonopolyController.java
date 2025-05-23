package edu.ntnu.idatt2003.boardgame.Controller;

import edu.ntnu.idatt2003.boardgame.Model.*;

import edu.ntnu.idatt2003.boardgame.Observer.BoardGameObserver;
import edu.ntnu.idatt2003.boardgame.View.MenuView;
import edu.ntnu.idatt2003.boardgame.View.MonopolyView;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Controls the flow and interactions of the Monopoly game.
 *
 *  <p>This class acts as the main controller for the Monopoly game, managing
 *  user inputs, game state updates, and interactions between the view and model.
 *  It listens for game state changes and ensures updates are properly reflected
 *  in the UI. Implements {@code BoardGameObserver} to track and respond to
 *  changes in the game state.
 */
public class MonopolyController implements BoardGameObserver {

    private final MonopolyView monopolyView;
    private final MenuView menuView;
    private final BoardGame boardGame;
    private Board board;
    private final PropertyHolder propertyHolder = new PropertyHolder();
    /**
     * Mapping of tiles to their associated house image.
     */
    private final Map<Tile, ImageView> houseViews = new HashMap<>();

    /**
     * Constructs a {@code MonopolyController} with the specified views and game instance.
     *
     * <p>This constructor initializes the controller by setting up the {@code MonopolyView},
     * {@code MenuView}, and {@code BoardGame} references. It then calls {@code initialize()}
     * on the {@code MonopolyView} and attaches event handlers to handle user interactions.
     *
     * @param monopolyView the main game view responsible for displaying the board and game elements
     * @param menuView the menu view for player setup and game options
     * @param boardGame the game logic handler managing players, tiles, and game mechanics
     */
    public MonopolyController(MonopolyView monopolyView, MenuView menuView ,BoardGame boardGame) {
        this.monopolyView = monopolyView;
        this.menuView = menuView;
        this.boardGame = boardGame;

        monopolyView.initialize();
        attachEventHandlers();
    }

    /**
     * Updates the game state based on the specified event.
     *
     * <p>This method is triggered whenever the game state changes, responding to various
     * events such as player movement, jail status, bankruptcy, or winning the game.
     * UI elements are dynamically updated based on the event, ensuring the correct visuals
     * and game flow are maintained.
     *
     * <p>Recognized event types:
     * <ul>
     *   <li>{@code "playerMoved"} - Updates the player's position on the board, processes fees, and adjusts buttons.</li>
     *   <li>{@code "inJail"} - Disables certain actions and enables jail-related options.</li>
     *   <li>{@code "usedUpTurns"} - Clears jail options and re-enables the round start button.</li>
     *   <li>{@code "release"} - Handles player release from jail and updates UI elements.</li>
     *   <li>{@code "bankrupt"} - Declares a player bankrupt.</li>
     *   <li>{@code "winner"} - Displays the winning player's details and disables gameplay.</li>
     *   <li>{@code "drewCard"} - Updates player position and displays a card alert.</li>
     * </ul>
     *
     * @param event the game event that has occurred
     * @param boardGame the game instance containing relevant player and board information
     */
    @Override
    public void update(String event, BoardGame boardGame){
        switch (event) {
            case "playerMoved":
                monopolyView.getHouseButtonsBox().getChildren().remove(monopolyView.getSellHouseButton());
                Player currentPlayer = boardGame.getPlayerHolder().getCurrentPlayer();
                int newTileId = currentPlayer.getCurrentTile().getId();
                addPlayerImageToNewTile(currentPlayer, newTileId);
                payAndGetFees();
                updateMoneyBox();
                updateBuyHouseButton();
                break;

            case "inJail":
                monopolyView.getHouseButtonsBox().getChildren().remove(monopolyView.getSellHouseButton());
                monopolyView.getBuyHouseButton().setDisable(true);
                monopolyView.getStartRoundButton().setDisable(true);
                monopolyView.getJailButtonsBox().getChildren().addAll(monopolyView.getRollForSixButton(),monopolyView.getPayFeeButton());
                break;

            case "usedUpTurns":
                monopolyView.getJailButtonsBox().getChildren().clear();
                monopolyView.getStartRoundButton().setDisable(false);
                break;

            case "release":
                monopolyView.getJailButtonsBox().getChildren().clear();
                monopolyView.getDiceBox().getChildren().clear();
                monopolyView.getDiceBox().getChildren().add(monopolyView.getReleaseText(boardGame.getPlayerHolder().getCurrentPlayer()));
                monopolyView.getStartRoundButton().setDisable(false);
                break;

            case "bankrupt":
                declareBankrupt();
                break;

            case "winner":
                monopolyView.getGameUpdates().getChildren().clear();
                monopolyView.getUpdatedMoneyBox().getChildren().clear();
                ImageView newImage = new ImageView(new Image("/images/winners/"+boardGame.getPlayerHolder().getPlayers().getFirst().getColor()+"_winner.png"));
                newImage.setPreserveRatio(true);
                newImage.setFitHeight(90);
                monopolyView.getGameUpdates().getChildren().addAll(newImage, monopolyView.getWinnerAnnouncement());
                menuView.playConfettiEffect();
                monopolyView.getStartRoundButton().setDisable(true);
                break;
            case "drewCard":
                update("playerMoved", boardGame);
                getAlert("Card",boardGame.getCardManager().sendAlert());
                updateMoneyBox();
                break;
        }
    }

    /**
     * Method handles the action of paying fees from the deck of cards with actions.
     */
    private void payAndGetFees() {
        Player currentPlayer = boardGame.getPlayerHolder().getCurrentPlayer();
        Tile tile = boardGame.getPlayerHolder().getCurrentPlayer().getCurrentTile();
        int fee = propertyHolder.getFee(tile.getId());

        if(propertyHolder.getProperties().get(tile.getId())!=currentPlayer && propertyHolder.getProperties().get(tile.getId())!=null){
            currentPlayer.editMoney(-fee);
            propertyHolder.getProperties().get(tile.getId()).editMoney(fee);
        }
    }

    /**
     * This method handles the state of the 'Buy House' button. Disabling it on certain tiles, if the player on the tile already owns a house there, and if a house already exists on the tile.
     */
    private void updateBuyHouseButton() {
        Tile tile = boardGame.getPlayerHolder().getCurrentPlayer().getCurrentTile();
        boolean housePresent = false;

        if(tile.getId() == 1 || tile.getId() == 8 || tile.getId() == 14 || tile.getAction() !=  null) {
            monopolyView.getBuyHouseButton().setDisable(true);
            return;
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

    /**
     * This method handles the actions of the buttons in the monopoly game. Set on action for each occurring button.
     */
    private void attachEventHandlers() {
        menuView.getMainMenuButton().setOnAction(e -> clearGame());
        monopolyView.getStartRoundButton().setOnAction(e -> startRound());
        monopolyView.getBuyHouseButton().setOnAction(e -> buyHouse());
        monopolyView.getSellHouseButton().setOnAction(e -> sellHouse());
        monopolyView.getPayFeeButton().setOnAction(e -> payReleaseFee());
        monopolyView.getRollForSixButton().setOnAction(e -> throwDieForRelease());
    }

    /**
     * This method handles the action of rolling the dice to get out of jail.
     */
    private void throwDieForRelease() {
        Player p = boardGame.getPlayerHolder().getCurrentPlayer();
        Die die = new Die();
        p.attemptRollExit(die.roll());
        ImageView diceImage = new ImageView(new Image("/images/dice"+die.getValue()+".png"));
        diceImage.setFitHeight(40);
        diceImage.setPreserveRatio(true);
        monopolyView.getDiceBox().getChildren().add(diceImage);
    }

    /**
     * This method handles the action of paying the fee to get out of jail.
     */
    private void payReleaseFee() {
        Player p = boardGame.getPlayerHolder().getCurrentPlayer();
        if(p.getMoney()<50){
            getAlert("Message","You dont have enough money to buy out of jail");
            throw new IllegalArgumentException("You dont have enough money to get out of jail");
        }
        else{
            boardGame.getPlayerHolder().getCurrentPlayer().payToExit();
            updateMoneyBox();
        }
        monopolyView.getDiceBox().getChildren().remove(monopolyView.getPayFeeButton());
        displayDice();
    }

    /**
     * This method handles the functionality of buying a house.
     * @throws IllegalArgumentException if the player cannot afford the house.
     */
    private void buyHouse() {
        Tile tile = boardGame.getPlayerHolder().getCurrentPlayer().getCurrentTile();
        Player owner = boardGame.getPlayerHolder().getCurrentPlayer();


        if(owner.getMoney()<=propertyHolder.getPrice(tile.getId())){
            getAlert("Message","You cant afford this house \n You have "+owner.getMoney()+" $, and need over "+propertyHolder.getPrice(tile.getId())+" $");
            throw new IllegalArgumentException("Player cant afford this house");
        }

        propertyHolder.setOwner(tile.getId(), owner);
        owner.editMoney(-propertyHolder.getPrice(tile.getId()));

        monopolyView.getBuyHouseButton().setDisable(true);
        addHouseToTile(tile);
        updateMoneyBox();
    }

    /**
     * This method handles the function of selling a house the player owns.
     */
    private void sellHouse() {
        Player owner = boardGame.getPlayerHolder().getCurrentPlayer();
        Tile tile = owner.getCurrentTile();

        owner.editMoney(propertyHolder.getPrice(tile.getId()));
        propertyHolder.removeOwner(tile.getId());

        ImageView houseImage = houseViews.remove(tile);
        if (houseImage != null) {
            tile.getTileBox().getChildren().remove(houseImage);
        }
        monopolyView.getSellHouseButton().setDisable(true);
        updateMoneyBox();
    }

    /**
     * This method adds an image of the house with the current player color to the designated tile provided.
     * @param tile is the tile the image is added to.
     */
    public void addHouseToTile(Tile tile) {
        ImageView houseImage = propertyHolder.getImage(tile.getId());
        houseImage.setFitHeight(35);
        houseImage.setPreserveRatio(true);
        tile.getTileBox().getChildren().add(houseImage);
        houseViews.put(tile, houseImage);
    }

    /**
     * Initializes the game board with the specified dimensions.
     *
     * <p>This method sets up the Monopoly board by initializing it in the {@code BoardGame}
     * instance with a predefined configuration. It calculates the total number of tiles
     * based on the given width and height, retrieves the board, and then instructs the
     * {@code MonopolyView} to generate the board grid accordingly.
     *
     * @param width the width of the board
     * @param height the height of the board
     */
    public void initializeBoard(int width, int height) {
        boardGame.initializeBoard("monopoly", (width*2+height*2), "hardcodedBoards.json");
        board = boardGame.getBoard();
        monopolyView.createBoardGrid(width, height, board);
    }

    /**
     * Updates the text in the money box, with the correct amount of money for each player.
     */
    public void updateMoneyBox(){
        StringBuilder s = new StringBuilder();
        for(Player player: boardGame.getPlayerHolder().getPlayers()){
            s.append("Player ").append(player.getName()).append(", ").append(player.getColor())
                .append(" : ").append(player.getMoney()).append(" $\n");
        }
        monopolyView.updateMoneyBox(s.toString());
    }

    /**
     * Handles the functionality of declaring bankruptcy for a player. Adding the player image to
     * the bankruptcy box. Also declaring winner id there is only one player left after the current
     * player has gone bankrupt.
     */
    private void declareBankrupt() {
        ArrayList<Player> toRemove = new ArrayList<>();

        for (Player player : boardGame.getPlayerHolder().getPlayers()) {
            if (player.getMoney() <= 0) {
                for (Integer key : propertyHolder.getProperties().keySet()) {
                    Player owner = propertyHolder.getProperties().get(key);
                    if (owner != null && owner.equals(player)) {
                        propertyHolder.removeOwner(key);

                        Tile tile = board.getTiles().get(key - 1);
                        ImageView houseImage = houseViews.remove(tile);
                        if (houseImage != null) {
                            tile.getTileBox().getChildren().remove(houseImage);
                        }
                    }
                }
                ImageView newImage = new ImageView(new Image("/images/players/"+player.getColor()+".png"));
                newImage.setFitHeight(20);
                newImage.setPreserveRatio(true);
                monopolyView.getBankruptcyBox().getChildren().addAll(newImage);
                toRemove.add(player);
            }
        }

        for (Player player : toRemove) {
            boardGame.getPlayerHolder().getPlayers().remove(player);
            boardGame.removePlayer(player);
        }

        if (boardGame.getPlayerHolder().getPlayers().size() == 1 || boardGame.getListOfPlayers().size() == 1) {
            update("winner", boardGame);
        }
    }

    /**
     * Sets up and initializes the Monopoly-style board game environment.
     *
     * @throws IOException if there is an error reading player or card resources from file
     */
    public void setUpMonopolyGame() throws IOException {
        boardGame.removeObserver(this);
        boardGame.addObserver(this);
        monopolyView.getDiceBox().getChildren().clear();
        monopolyView.getMoneyBox().getChildren().clear();
        monopolyView.getMonopolyLayout().getChildren().clear();
        monopolyView.getRootLayout().getChildren().clear();

        initializeBoard(8,7);

        monopolyView.createMonopolyLayout();
        BorderPane gameLayout = monopolyView.getMonopolyLayout();
        monopolyView.getMoneyBox().getChildren().add(menuView.getMainMenuButton());

        menuView.getMenuLayout().getChildren().clear();
        menuView.getMenuLayout().getChildren().add(gameLayout);


        boardGame.initializeDice(Integer.parseInt(menuView.getDiceField().getText()));


        try {
            boardGame.createPlayerHolder("src/main/resources/players.csv",boardGame.getListOfPlayers());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        for(Player p : boardGame.getPlayerHolder().getPlayers()){
            ImageView imageView = new ImageView(new Image("images/players/"+p.getColor()+".png"));
            p.setImageView(imageView);
            p.setBoardGame(boardGame);
            p.placeOnTile(board,1);
            addPlayerImageToNewTile(p, 1);
            p.editMoney(500);
        }

        propertyHolder.setProperties(boardGame.getBoard().getTiles().size());
        boardGame.setCardManager(new CardManager("src/main/resources/cards.json"));

        updateMoneyBox();
    }

    /**
     * Adds the player's image to the tile with the specified ID on the game board.
     * <p>
     * If the tile exists and does not already contain the player's image, the image is added
     * to the tile's visual component.
     *
     * @param player    the player whose image should be added to the tile
     * @param newTileId the ID of the tile to place the player's image on
     */
    private void addPlayerImageToNewTile(Player player, int newTileId){
        for (Tile t : board.getTiles()) {
            if (t.getId() == newTileId) {
                Node playerImage = monopolyView.getPlayerImage(player);
                if (!t.getTileBox().getChildren().contains(playerImage)) {
                    t.getTileBox().getChildren().add(playerImage);
                }
            }
        }
    }


    /**
     * Clears the game monopoly and all it's components. Removes winner. Clears board. Disable and removes buttons. And Create the main menu.
     */
    public void clearGame() {
        boardGame.undoWinner(boardGame.getWinner());
        boardGame.clearBoard();
        boardGame.undoCustomBoardLoad();

        monopolyView.getMonopolyLayout().getChildren().clear();
        monopolyView.getDiceBox().getChildren().clear();
        monopolyView.getUpdatedMoneyBox().getChildren().clear();
        monopolyView.getStartRoundButton().setDisable(false);


        monopolyView.getNextPlayerInfoBox().getChildren().clear();
        monopolyView.getMoneyBox().getChildren().clear();
        monopolyView.getGameUpdates().getChildren().clear();
        monopolyView.getHouseButtonsBox().getChildren().clear();
        monopolyView.getBankruptcyBox().getChildren().clear();
        monopolyView.getTitleBox().getChildren().clear();
        monopolyView.getJailButtonsBox().getChildren().clear();

        menuView.getMButton().setSelected(false);
        menuView.getPlayerData().clear();
        menuView.setPlayerColorBox();
        menuView.getDiceField().clear();
        menuView.getDiceSection().getChildren().clear();
        menuView.getBoardSizeMenu().setDisable(false);
        menuView.createMainMenu();
    }

    /**
     * Starts round for the current player in monopoly. Calls for the play method in the BoardGame class.
     */
    private void startRound() {
        boardGame.play();
        displayDice();
    }

    /**
     * Displays a styled error alert dialog with the given title and message. The alert represents the chance card in the monopoly game.
     *
     * @param title   the title of the alert dialog (also used to determine styling)
     * @param message the message to be displayed in the alert content
     */
    private void getAlert(String title,String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.setGraphic(null);
        if(title.equals("Card")){
            alert.getDialogPane().setStyle("-fx-background-color: #32649e; -fx-font-size: 24px; -fx-pref-width: 250px;-fx-pref-height: 350px");
        }
        else{
            alert.getDialogPane().setStyle("-fx-background-color: #934343; -fx-font-size: 24px; -fx-pref-width: 250px;-fx-pref-height: 350px");
        }
        alert.showAndWait();
    }

    /**
     * Displays the dice box with information text. Including what the current player rolled, information about jail, and who the next player is.
     */
    public void displayDice(){
        monopolyView.getDiceBox().getChildren().clear();
        monopolyView.getNextPlayerInfoBox().getChildren().clear();
        Player p = boardGame.getPlayerHolder().getCurrentPlayer();
        String nextPlayerName = boardGame.getPlayerHolder().getPlayerNameWithIndex(boardGame.getPlayerHolder().getNextPlayerIndex());
        Text roleInfo = new Text("Player '"+p.getName()+", "+p.getColor() + "' \n threw a");
        roleInfo.setWrappingWidth(200);
        monopolyView.getDiceBox().getChildren().add(roleInfo);
        if(p.isInJail()){
            monopolyView.getDiceBox().getChildren().add(new Text("\nYou are in jail. \n Roll for 6 in three tries \n or pay a 50$ fee"));
        }
         else{
            for(Die d : boardGame.getDice().getListOfDice()){
                ImageView die = new ImageView(new Image("/images/dice/dice"+d.getValue()+".png"));
                die.setFitHeight(40);
                die.setFitWidth(40);
                monopolyView.getDiceBox().getChildren().add(die);
                monopolyView.getDiceBox().setAlignment(Pos.CENTER);
            }
        }
         Text nextPlayerText = new Text("Next player to roll dice: " + nextPlayerName);
         nextPlayerText.setWrappingWidth(200);
         monopolyView.getNextPlayerInfoBox().getChildren().add(nextPlayerText);

    }


}
