import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idatt2003.boardgame.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
public class PortalActionTest {
    BoardGame game;
    Player player;
    @BeforeEach

    void setUp() {
        game = new BoardGame();
        player = new Player("Name",game);
        Tile tile1 = new Tile(1);
        Tile tile2 = new Tile(2);
        Tile tile3 = new Tile(3);
        Tile tile4 = new Tile(4);
        Tile tile5 = new Tile(5);

        game.createBoard();
        game.getBoard().addTile(tile1);
        game.getBoard().addTile(tile2);
        game.getBoard().addTile(tile3);
        game.getBoard().addTile(tile4);
        game.getBoard().addTile(tile5);

        player.placeOnTile(tile1);
    }
    @Test
    public void testPortalAction() {
        int prevTile = player.getCurrentTile().getTileId();
        PortalAction portalAction = new PortalAction("Description");
        portalAction.perform(player);
        int newTile = player.getCurrentTile().getTileId();
        assertNotEquals(prevTile, newTile);
    }

}
