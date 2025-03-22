import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idatt2003.boardgame.actions.LadderAction;
import edu.ntnu.idatt2003.boardgame.actions.TileAction;
import edu.ntnu.idatt2003.boardgame.componentHolders.Board;
import edu.ntnu.idatt2003.boardgame.componentHolders.BoardGame;
import edu.ntnu.idatt2003.boardgame.components.Player;
import edu.ntnu.idatt2003.boardgame.components.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TileTest {
    BoardGame boardGame;
    Board board;
    @BeforeEach
    void setUp() {
        boardGame = new BoardGame();
        boardGame.createBoard(20,"src/test/resources/boardGameInfoTest.json");
        board = boardGame.getBoard();
    }

    @Test
    public void getTileIdTest(){
        Tile tile12 = board.getTiles().get(11);
        assertEquals(12, tile12.getTileId());
    }
    @Test
    public void getActionTest(){
        TileAction tileAction = board.getTiles().get(4).getAction();
        assertEquals(tileAction.getClass(), LadderAction.class);
    }

    @Test
    public void getnextTileTest(){
        assertEquals(board.getTiles().get(2).getTileId(), board.getTiles().get(1).getNextTile());
    }

    @Test
    public void getnextTileOnLastTileTest(){
        assertEquals(board.getTiles().get(19).getNextTile(), board.getTiles().get(19).getNextTile());
    }
}