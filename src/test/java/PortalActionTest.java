import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idatt2003.boardgame.actions.LadderAction;
import edu.ntnu.idatt2003.boardgame.actions.PortalAction;
import edu.ntnu.idatt2003.boardgame.componentHolders.Board;
import edu.ntnu.idatt2003.boardgame.componentHolders.BoardGame;
import edu.ntnu.idatt2003.boardgame.components.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PortalActionTest {
    BoardGame boardGame;
    Board board;
    Player player;
    @BeforeEach
    void setUp() {
        boardGame = new BoardGame();
        boardGame.createBoard(90,"src/test/resources/boardGameInfoTest.json");
        board = boardGame.getBoard();
        player = new Player("Walter","White");
        player.setBoardGame(boardGame);

    }
    @Test
    void testPortalAction() {
        player.setCurrentTile(board,3);
        PortalAction portalAction = new PortalAction(3,"Portal action performed");
        portalAction.perform(player);
        assertNotEquals(3,player.getCurrentTile().getTileId());
    }
}
