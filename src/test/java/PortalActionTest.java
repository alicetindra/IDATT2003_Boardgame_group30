import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idatt2003.boardgame.Model.Board;
import edu.ntnu.idatt2003.boardgame.Model.BoardGame;
import edu.ntnu.idatt2003.boardgame.Model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class PortalActionTest {
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
    public void teleportPlayerTest(){
        player.placeOnTile(board,48);
        player.move(1);
        assertNotEquals(49, player.getCurrentTile().getId());
    }


}
