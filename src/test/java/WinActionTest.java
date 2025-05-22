import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idatt2003.boardgame.Model.Board;
import edu.ntnu.idatt2003.boardgame.Model.BoardGame;
import edu.ntnu.idatt2003.boardgame.Model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class WinActionTest {
    Player player;
    BoardGame boardGame;
    Board board;
    @BeforeEach
    public void setUp() {
        player = new Player("Lisa","Yellow");
        boardGame = new BoardGame();
        player.setBoardGame(boardGame);
        boardGame.initializeBoard("SnakesAndLadders", 50,"hardcodedBoards.json");
        board = boardGame.getBoard();
    }

    //------------------------Positive tests--------------------------------

    @Test
    public void testPerformWin() {
        player.placeOnTile(board,49);
        player.move(1);
        assertEquals(player, boardGame.getWinner());
    }
    //------------------------Negative tests--------------------------------
    @Test
    public void testPassFinish() {
        player.placeOnTile(board,49);
        player.move(10);
        assertNotEquals(player, boardGame.getWinner());
    }
}
