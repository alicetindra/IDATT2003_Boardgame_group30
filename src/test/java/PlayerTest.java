import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idatt2003.boardgame.componentHolders.BoardGame;
import edu.ntnu.idatt2003.boardgame.components.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PlayerTest {
    private Player player;
    private BoardGame boardGame;

    @BeforeEach
    public void setUp() {
        player = new Player("Fredrik","Magenta");
        boardGame = new BoardGame();
        player.setBoardGame(boardGame);
        boardGame.createBoard(10,"src/test/resources/boardGameInfoTest.json");
    }


    //Positive tests
    @Test
    public void testGetName() {
        String playerName = player.getName();
        assertEquals("Fredrik", playerName);
    }
    @Test
    public void testGetColor() {
        String playerColor = player.getColor();
        assertEquals("Magenta", playerColor);
    }
    @Test
    public void testSetAndGetBoardGame() {
        BoardGame game = player.getBoardGame();
        assertEquals(game, boardGame);
    }
    @Test
    public void testSetAndGetCurrentTile() {
        player.setCurrentTile(boardGame.getBoard(),8);
        assertEquals(8, player.getCurrentTile().getTileId());
    }


    //Negative tests
    @Test
    public void testCreatePlayerNameIsNull(){
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Player(null, "Green"));
        assertEquals("Name and color cannot be null or empty", exception.getMessage());
    }
    @Test
    public void testCreatePlayerNameIsEmpty(){
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Player("", "Yellow"));
        assertEquals("Name and color cannot be null or empty", exception.getMessage());
    }
    @Test
    public void testCreatePlayerColorIsNull(){
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Player("Harald", null));
        assertEquals("Name and color cannot be null or empty", exception.getMessage());
    }
    @Test
    public void testCreatePlayerColorIsEmpty(){
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Player("Harald", ""));
        assertEquals("Name and color cannot be null or empty", exception.getMessage());
    }
    @Test
    public void testSetCurrentTileIsPastFinish(){
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()-> player.setCurrentTile(boardGame.getBoard(), 13));
        assertEquals("Tile id out of bounds", exception.getMessage());
    }

}
