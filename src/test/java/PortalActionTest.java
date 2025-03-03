import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idatt2003.boardgame.Board;
import edu.ntnu.idatt2003.boardgame.BoardGame;
import edu.ntnu.idatt2003.boardgame.Player;
import edu.ntnu.idatt2003.boardgame.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
public class PortalActionTest {
    Player player;
    Board board;
    BoardGame boardGame;

    @BeforeEach
    public void setUp() {
        boardGame = new BoardGame();
        boardGame.createBoard();
        board = boardGame.getBoard();
        for (int i = 1; i<11; i++){
            board.addTile(new Tile(i));
        }
        board.fillActionMap(6,0);
        player = new Player("name", boardGame);
        boardGame.addPlayer(player);
        boardGame.setCurrentPlayer(player);
        boardGame.createDice(2);

    }
    @Test
    public void testPortalAction() {
        player.placeOnTile(board.getTile(1));
        player.move(5);
        assertNotEquals(player.getCurrentTile(), board.getTile(6));
    }
    
}
