import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idatt2003.boardgame.componentHolders.Board;
import edu.ntnu.idatt2003.boardgame.componentHolders.BoardGame;
import edu.ntnu.idatt2003.boardgame.componentHolders.PlayerHolder;
import edu.ntnu.idatt2003.boardgame.components.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BoardGameTest {
    private BoardGame game;
    @BeforeEach
    void setUp() {
        game = new BoardGame();
        game.createBoard(10, "src/test/resources/boardGameInfoTest.json");
    }


    //Positive tests
    @Test
    public void testCreateDice() {
        game.createDice(4);
        assertEquals(4, game.getDice().getListOfDice().size());
    }

    //NB . Not done
    /*


    @Test
    public void createPlayerHolder() throws IOException {
        List<String> stringList = new ArrayList<>();
        stringList.add("Amanda,Purple");
        stringList.add("Peter,Beige");
        game.createPlayerHolder("src/test/resources/playersTest.csv",stringList);
    }
    */
    @Test
    public void testGetWinner() {
        Player player = new Player("Julie","yellow");
        game.declareWinner(player);
        assertEquals(game.getWinner(), player);
    }
    @Test
    public void testCreateBoard() {
        BoardGame boardGame = new BoardGame();
        boardGame.createBoard(12, "src/test/resources/boardGameInfoTest.json");
        assertEquals(12, boardGame.getBoard().getTiles().size());
    }

    //------------------Negative tests-------------------------------


    @Test
    public void testCreateDiceLessThanOne() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,() -> game.createDice(0));
        assertEquals("Number of dice cannot be negative or zero",exception.getMessage());
    }
    //Negative tests
    @Test
    public void createBoardWithInvalidIntThrowsTest() {
        BoardGame boardGame = new BoardGame();
        assertThrows(IllegalArgumentException.class, () -> {
            boardGame.createBoard(-5, "src/test/resources/boardGameInfoTest.json");
        });
    }



}
