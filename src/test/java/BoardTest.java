import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idatt2003.boardgame.componentHolders.Board;
import edu.ntnu.idatt2003.boardgame.componentHolders.BoardGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BoardTest {
    private BoardGame boardGame;
    private Board board;

    @BeforeEach
    public void setUp() {
        boardGame = new BoardGame();
        boardGame.createBoard(20,"src/test/resources/boardGameInfoTest.json");
        board = boardGame.getBoard();
    }

    //Positive tests
    @Test
    public void getTheSameBoardTest() {
        assertEquals(board.getTiles(),board.getTiles());
    }
    @Test
    public void getBoardSizeTest() {
        assertEquals(20, board.getTiles().size());
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
