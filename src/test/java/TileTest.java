import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idatt2003.boardgame.Board;
import edu.ntnu.idatt2003.boardgame.BoardGame;
import edu.ntnu.idatt2003.boardgame.Tile;
import edu.ntnu.idatt2003.boardgame.Player;
import org.junit.jupiter.api.Test;

public class TileTest {
    @Test
    public void testThatPlayerGetsPlacedOnCorrectTile(){
        Tile tile = new Tile(1);
        Tile tile2 = new Tile(2);
        Tile tile3 = new Tile(3);

        BoardGame game = new BoardGame();
        game.createBoard();
        game.getBoard().addTile(tile);
        game.getBoard().addTile(tile2);
        game.getBoard().addTile(tile3);

        Player player = new Player("Ludvig",game);
        player.setCurrentTile(tile);
        player.move(2);

        assertEquals(tile3, player.getCurrentTile());
    }
}
