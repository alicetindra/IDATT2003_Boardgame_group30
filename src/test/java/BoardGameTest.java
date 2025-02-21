import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idatt2003.boardgame.BoardGame;
import edu.ntnu.idatt2003.boardgame.Board;
import edu.ntnu.idatt2003.boardgame.Tile;
import edu.ntnu.idatt2003.boardgame.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
public class BoardGameTest {
    private BoardGame game;
    @BeforeEach
    void setUp() {
        game = new BoardGame();
    }

    @Test
    public void testAddPlayers() {
        Player player = new Player("Name",game);
        Player player2 = new Player("Other name",game);
        game.addPlayer(player);
        game.addPlayer(player2);

        assertEquals(game.getPlayers().size(), 2);
    }

    @Test
    public void testCreateDice() {
        game.createDice(4);
        assertEquals(4, game.getDice().getListOfDice().size());
    }

    //------------------Negative tests-------------------------------

    @Test
    public void testAddNullPlayer() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,() -> game.addPlayer(null));
        assertEquals("Player cannot be null",exception.getMessage());
    }

    @Test
    public void testCreateBoard() {
        game.createBoard();
        assertInstanceOf(Board.class, game.getBoard());
    }
    @Test
    public void testGetWinner() {
        game.createBoard();
        Player player = new Player("Name",game);
        game.addPlayer(player);
        game.setCurrentPlayer(player);
        game.getBoard().addTile(new Tile(1));
        game.getBoard().addTile(new Tile(2));

        game.getCurrentPlayer().placeOnTile(game.getBoard().getTile(2));
        assertEquals(game.getWinner(), game.getCurrentPlayer());
    }

    @Test
    public void testCreateDiceLessThanOne() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,() -> game.createDice(0));
        assertEquals("Number of dice must be greater than 0",exception.getMessage());
    }


}
