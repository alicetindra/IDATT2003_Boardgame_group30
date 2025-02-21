import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idatt2003.boardgame.Board;
import edu.ntnu.idatt2003.boardgame.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BoardTest {
    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board();
        for(int i = 1; i<=100; i++){
            board.addTile(new Tile(i));
        }
    }
    @Test
    public void testFillABoardWithTiles(){
        assertEquals(board.getMap().size(),100);
    }
    @Test
    public void testGetATileFromBoard(){
        Tile tileInQuestion = board.getMap().get(3);

        assertEquals(board.getTile(3),tileInQuestion);
    }
    //
    @Test
    public void testAddANullTile(){
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> board.addTile(null));
        assertEquals("Tile cannot be null", exception.getMessage());
    }
    @Test
    public void testGetTileWithOutOfBoundsIndex(){
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> board.getTile(101));
        assertEquals("Tile id out of bounds", exception.getMessage());
    }
}
