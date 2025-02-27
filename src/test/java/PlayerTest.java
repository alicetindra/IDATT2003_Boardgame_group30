import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idatt2003.boardgame.BoardGame;
import edu.ntnu.idatt2003.boardgame.Player;
import edu.ntnu.idatt2003.boardgame.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class PlayerTest {

  private Player player;
  private BoardGame boardGame;

  private Tile tile = new Tile(1);
  private Tile tile2 = new Tile(2);
  private Tile tile3 = new Tile(3);

  @BeforeEach
  public void setUp() {
    BoardGame game = new BoardGame();
    game.createBoard();
    game.getBoard().addTile(tile);
    game.getBoard().addTile(tile2);
    game.getBoard().addTile(tile3);

    player = new Player("Tindra", game);
  }

  //--------------------------Positive tests----------------------------------

  @Test
  public void testPlaceOnTileValidInput(){
    player.placeOnTile(tile2);
    assertEquals(tile2, player.getCurrentTile());
  }
  @Test
  public void testPlayerCorrectTileAfterMove(){

    player.placeOnTile(tile);
    player.move(2);

    assertEquals(tile3, player.getCurrentTile());
  }

//------------------------------Negative tests---------------------------


  @Test
  public void testCreatePlayerNameIsNull(){
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Player(null, boardGame));
    assertEquals("Player name cannot be null or empty", exception.getMessage());
  }

  @Test
  public void testCreatePlayerNameIsEmpty(){
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Player(" ", boardGame));
    assertEquals("Player name cannot be null or empty", exception.getMessage());
  }

  @Test
  public void testCreatePlayerBoardGameIsNull(){
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()-> new Player("Tindra", null));
    assertEquals("BoardGame cannot be null", exception.getMessage());
  }

  @Test
  public void testPlaceOnTileIsNull(){
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()-> player.placeOnTile(null));
    assertEquals("Tile cannot be null", exception.getMessage());
  }

  @Test
  public void testMoveOnStepsLessThanOne(){
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()-> player.move(0));
    assertEquals("Steps must be greater than 1", exception.getMessage());
  }

  @Test
  public void testSetCurrentTileIsNull(){
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()-> player.placeOnTile(null));
    assertEquals("Tile cannot be null", exception.getMessage());
  }


}
