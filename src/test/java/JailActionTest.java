import edu.ntnu.idatt2003.boardgame.Model.*;
import edu.ntnu.idatt2003.boardgame.Model.actions.JailAction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JailActionTest {
    Player player;
    BoardGame game;
    Board board;
    List<String> players;
    JailAction action;

    @BeforeEach
    public void setUp() {
        action = new JailAction(8,"Go to jail");
        player = new Player("Player", "Yellow");
        game = new BoardGame();
        game.addPlayer("Player", "Yellow");
        players = game.getListOfPlayers();
        game.initializeBoard("monopoly", (30), "hardcodedBoards.json");
        board = game.getBoard();
        player.setBoardGame(game);
        player.placeOnTile(board,1);
    }


    @Test
    public void testPerformSetInJail() {
        action.perform(player);
        assertTrue(player.isInJail());
    }

    @Test
    public void testPerformMovePlayer() {
        action.perform(player);
        assertEquals(player.getCurrentTile(), board.getTiles().get(7));
    }

    @Test
    public void testNotInJail() {
        action.perform(player);
        player.payToExit();
        assertFalse(player.isInJail());
    }

}

