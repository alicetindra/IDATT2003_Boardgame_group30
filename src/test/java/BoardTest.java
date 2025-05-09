import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idatt2003.boardgame.Model.Board;
import edu.ntnu.idatt2003.boardgame.Model.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class BoardTest {
    Board board;

    @BeforeEach
    public void setUp() {
        board = new Board();
    }

    //------------------------Positive tests--------------------------------

    @Test
    public void testGetBoardAsList() {
        assertInstanceOf(List.class, board.getTiles());
    }

    @Test
    public void testGetBoardAsListOfTiles() {
        List<?> tiles = board.getTiles();
        for (Object tile : tiles) {
            assertInstanceOf(Tile.class, tile);
        }
    }

    //--------------------------Negative tests-------------------------

    @Test
    public void testGetTilesNotNull() {
        assertNotNull(board.getTiles(), "getTiles() should not return null");
    }







}
