import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idatt2003.boardgame.actions.LadderAction;
import edu.ntnu.idatt2003.boardgame.componentHolders.Board;
import edu.ntnu.idatt2003.boardgame.componentHolders.BoardGame;
import edu.ntnu.idatt2003.boardgame.components.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LadderActionTest {
    BoardGame boardGame;
    Board board;
    Player player;
    @BeforeEach
    void setUp() {
        boardGame = new BoardGame();
        boardGame.createBoard(10,"src/test/resources/boardGameInfoTest.json");
        board = boardGame.getBoard();
        player = new Player("Walter","White");
        player.setBoardGame(boardGame);

    }
    @Test
    void testLadderAction() {
        player.setCurrentTile(board,3);
        LadderAction ladderAction = new LadderAction(8,"Ladder action performed");
        ladderAction.perform(player);
        assertEquals(8,player.getCurrentTile().getTileId());
    }
    @Test
    void testLadderActionOut() {
        player.setCurrentTile(board,3);
        LadderAction ladderAction = new LadderAction(20,"Ladder action performed");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,() -> ladderAction.perform(player));
        assertEquals("Destination-tile is not on the board",exception.getMessage());
    }
}
