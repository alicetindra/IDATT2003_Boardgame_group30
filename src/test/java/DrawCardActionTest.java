import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idatt2003.boardgame.Model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class DrawCardActionTest {
    Player player;
    BoardGame boardGame;
    Board board;
    CardManager cardManager;
    @BeforeEach
    public void setUp() throws IOException {
        player = new Player("Lisa","Yellow");
        List<String> players = new ArrayList<>();
        players.add(player.getName()+","+player.getColor());
        boardGame = new BoardGame();
        player.setBoardGame(boardGame);
        boardGame.initializeBoard("monopoly", 30,"hardcodedBoards.json");
        board = boardGame.getBoard();
        boardGame.initializeDice(1);
        boardGame.setCardManager(new CardManager("src/main/resources/cardtest.json"));
        boardGame.createPlayerHolder("players.csv",players);
        cardManager = boardGame.getCardManager();
    }

    //------------------------Positive tests--------------------------------

    @Test
    public void testPerform() {
        player.placeOnTile(board,3);
        player.editMoney(0);
        int moneyBefore = player.getMoney();

        player.move(1); //activates the action
        int moneyAfter = player.getMoney();

        assertEquals(moneyBefore+150,moneyAfter);

        assertEquals(player.getCurrentTile().getId(), 10);
    }
}
