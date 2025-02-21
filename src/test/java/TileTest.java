import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idatt2003.boardgame.Board;
import edu.ntnu.idatt2003.boardgame.BoardGame;
import edu.ntnu.idatt2003.boardgame.Tile;
import edu.ntnu.idatt2003.boardgame.Player;
import org.junit.jupiter.api.Test;

public class TileTest {
    @Test
    public void testMakeATile(){
        Tile tile = new Tile(1);


        assertEquals(1, tile.getTileId());
    }
    @Test
    public void testSetNextTile(){
        Tile tile = new Tile(1);
        Tile tile2 = new Tile(2);
        Tile tile3 = new Tile(3);
        Board board = new Board();
        board.addTile(tile);
        board.addTile(tile2);
        board.addTile(tile3);


        assertEquals(tile3, tile2.getNextTile());
    }
}
