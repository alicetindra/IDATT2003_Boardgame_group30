import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idatt2003.boardgame.Model.Board;
import edu.ntnu.idatt2003.boardgame.Model.BoardGame;
import edu.ntnu.idatt2003.boardgame.Model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class SnakeActionTest {
    Player player;
    BoardGame boardGame;
    Board board;
    @BeforeEach
    public void setUp() {
        player = new Player("Nicoline","Red");
        boardGame = new BoardGame();
        player.setBoardGame(boardGame);
        boardGame.initializeBoard("SnakesAndLadders", 90,"hardcodedBoards.json");
        board = boardGame.getBoard();
    }

    //------------------------Positive tests--------------------------------

    @Test
    public void performSnakeActionTest() {
        player.placeOnTile(board,27);
        player.move(1);
        assertEquals(8, player.getCurrentTile().getId());
    }


}
