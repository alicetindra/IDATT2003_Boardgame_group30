import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idatt2003.boardgame.Model.Board;
import edu.ntnu.idatt2003.boardgame.Model.BoardGame;
import edu.ntnu.idatt2003.boardgame.Model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class LadderActionTest {
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
    public void performLadderActionTest() {
        player.placeOnTile(board,4);
        player.move(1);
        assertEquals(25, player.getCurrentTile().getId());
    }


}
