import edu.ntnu.idatt2003.boardgame.Model.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BoardGameTest {

    private BoardGame boardGame;

    @BeforeEach
    void setUp() {
        boardGame = new BoardGame();
    }

    @Test
    void testInitializeDice() {
        boardGame.initializeDice(2);
        assertNotNull(boardGame.getDice());

        assertEquals(2, boardGame.getDice().getListOfDice().size());
    }

    @Test
    void testAddAndRemovePlayerFromList() {
        boardGame.addPlayer("Julie", "red");
        boardGame.addPlayer("Bob", "blue");

        List<String> players = boardGame.getListOfPlayers();
        assertEquals(2, players.size());
        assertFalse(players.contains("Sverre,red"));
        assertTrue(players.contains("Julie,red"));

        Player player = new Player("Julie", "red");
        boardGame.removePlayer(player);
        assertEquals(1, boardGame.getListOfPlayers().size());
    }

    @Test
    void testRemovePlayerFromList() {
        boardGame.addPlayer("Nicoline", "blue");

        Player player = new Player("Nicoline", "blue");
        boardGame.removePlayer(player);
        assertEquals(0, boardGame.getListOfPlayers().size());
    }


    @Test
    void testDeclareAndUndoWinner() {
        Player winner = new Player("Winner", "green");
        boardGame.declareWinner(winner);

        assertEquals(winner, boardGame.getWinner());

        boardGame.undoWinner(winner);
        assertNull(boardGame.getWinner());
    }

    @Test
    void testLoadCustomBoard() {
        assertFalse(boardGame.isCustomBoardLoaded());

        boardGame.loadCustomBoard("/resources/hardcodedBoards.json");
        assertTrue(boardGame.isCustomBoardLoaded());

        boardGame.undoCustomBoardLoad();
        assertFalse(boardGame.isCustomBoardLoaded());
    }

    @Test
    void testCreatePlayerHolder() throws IOException {
        List<String> playerStrings = Arrays.asList("Nicoline,green", "Petter,black");
        boardGame.createPlayerHolder("players.csv", playerStrings);

        PlayerHolder holder = boardGame.getPlayerHolder();
        assertNotNull(holder);
        assertEquals(2, holder.getPlayers().size());
    }

    @Test
    void testClearBoard() {
        boardGame.addPlayer("Pål", "yellow");
        boardGame.clearBoard();

        assertNull(boardGame.getBoard());
        assertEquals(0, boardGame.getListOfPlayers().size());
    }

    @Test
    void testSetAndGetCardManager() {
        CardManager cardManager = new CardManager("/resources/cards.csv");
        boardGame.setCardManager(cardManager);
        assertEquals(cardManager, boardGame.getCardManager());
    }
}
