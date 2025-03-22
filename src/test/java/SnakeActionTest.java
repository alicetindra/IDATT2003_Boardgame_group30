import edu.ntnu.idatt2003.boardgame.actions.LadderAction;
import edu.ntnu.idatt2003.boardgame.actions.SnakeAction;
import edu.ntnu.idatt2003.boardgame.componentHolders.Board;
import edu.ntnu.idatt2003.boardgame.componentHolders.BoardGame;
import edu.ntnu.idatt2003.boardgame.components.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SnakeActionTest {
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
    void testSnakeAction() {
        player.setCurrentTile(board,8);
        SnakeAction snakeAction = new SnakeAction(3,"Snake action performed");
        snakeAction.perform(player);
        assertEquals(3,player.getCurrentTile().getTileId());
    }

}
