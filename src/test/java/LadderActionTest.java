import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idatt2003.boardgame.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
public class LadderActionTest {
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

        game.getBoard().fillActionMap(2,5);

        player.placeOnTile(tile1);
    }
    @Test
    public void testValidLadderAction() {
        int prevTileSpot = player.getCurrentTile().getTileId();
        LadderAction ladderAction = new LadderAction(3,"Description");
        ladderAction.perform(player);
        int newTileSpot = player.getCurrentTile().getTileId();
        int expected = prevTileSpot + 2;
        assertEquals(expected,newTileSpot);
    }
    @Test
    public void testInvalidLadderAction() {
        LadderAction ladderAction = new LadderAction(-2,"Description");
        assertThrows(IllegalArgumentException.class, ()-> ladderAction.perform(player));

    }
    @Test
    public void testClimbALadder() {
        player.move(2);
        LadderAction ladderAction = new LadderAction(game.getBoard().getActionMap().get(2),"Description");
        ladderAction.perform(player);
        assertEquals(5,player.getCurrentTile().getTileId());
    }
}
