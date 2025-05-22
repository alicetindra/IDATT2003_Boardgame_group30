import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idatt2003.boardgame.Model.Board;
import edu.ntnu.idatt2003.boardgame.Model.BoardGame;
import edu.ntnu.idatt2003.boardgame.Model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class PassStartActionTest {
    Player player;
    BoardGame boardGame;
    Board board;
    @BeforeEach
    public void setUp() {
        player = new Player("Lisa","Yellow");
        boardGame = new BoardGame();
        player.setBoardGame(boardGame);
        boardGame.initializeBoard("monopoly", 50,"hardcodedBoards.json");
        board = boardGame.getBoard();
    }

    //------------------------Positive tests--------------------------------

    @Test
    public void testSetPlayerPastStart() {
        player.placeOnTile(board,22);
        player.move(10);
        assertTrue(player.getCurrentTile().getId()< board.getTiles().size());
    }

    @Test
    public void testGivePlayerMoney() {
        player.placeOnTile(board,22);
        int moneyBefore = player.getMoney();
        player.move(10);
        int moneyAfter = player.getMoney();
        assertEquals(moneyAfter - 100, moneyBefore);
    }
    //------------------------Negative tests--------------------------------

    @Test
    public void testDoNotGivePassStartByCards() {
        player.placeOnTile(board,22);
        int moneyBefore = player.getMoney();
        player.placeOnTile(board,6); //What a card does then it places a player
        int moneyAfter = player.getMoney();
        assertEquals(moneyAfter, moneyBefore);
    }
}
